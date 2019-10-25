package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.CartRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import net.thumtack.onlineshop.dao.PurchaseRepository;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientsDetailingDto;
import net.thumtack.onlineshop.dto.Response.ResponseLightPurchaseDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductInfoDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductsTopDto;
import net.thumtack.onlineshop.dto.Response.ResponsePurchProdDto;
import net.thumtack.onlineshop.dto.Response.ResponsePurchaseDto;
import net.thumtack.onlineshop.dto.Response.ResponseRevenueDto;
import net.thumtack.onlineshop.entities.Cart;
import net.thumtack.onlineshop.entities.Category;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.entities.Purchase;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import net.thumtack.onlineshop.services.interfaces.ProductService;
import net.thumtack.onlineshop.services.interfaces.PurchaseService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private ClientRepository clientRepository;
    private Validator validator;
    private ClientService clientService;
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private PurchaseRepository purchaseRepository;
    private ProductService productService;
    private CategoryRepository categoryRepository;


    @Autowired
    PurchaseServiceImpl(ClientRepository clientRepository, Validator validator,
                        ClientService clientService, ProductRepository productRepository,
                        CartRepository cartRepository, PurchaseRepository purchaseRepository,
                        ProductService productService, CategoryRepository categoryRepository) {
        this.clientRepository = clientRepository;
        this.validator = validator;
        this.clientService = clientService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseProductBuyDto buyProduct(HttpServletRequest httpReq, RequestProductBuyDto request) {
        validator.isCookieNullCLient(httpReq);
        List<RespError> errorList = new ArrayList<>();
        Client client = new Client();
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        Optional<Client> optClient = clientRepository.findByLogin(login);
        Product product = productRepository.findById(request.getId()).orElseThrow(() -> new ServerExceptions(Collections
                .singletonList(new RespError(ServerErrors.WRONG_PRODUCT, "id",
                        ServerErrors.WRONG_PRODUCT.getErrorMessage()))));
        if (optClient.isPresent()) {
            client = optClient.get();
        }
        errorList = validator.validPurchase(client, product, request, errorList);
        client.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        product.setCount(product.getCount() - request.getCount());
        if (!errorList.isEmpty()) {
            throw new ServerExceptions(errorList);
        }
        productRepository.save(product);
        clientRepository.save(client);
        addPurchase(client, product, request);
        return new ResponseProductBuyDto(request.getId(), request.getName(), request.getPrice(),
                request.getCount());
    }

    @Override
    @Transactional(rollbackFor = ServerExceptions.class)
    public ResponseCartBuyDto buyCartProducts(HttpServletRequest httpReq,
                                              List<RequestProductBuyDto> buyReq) {
        validator.isCookieNullCLient(httpReq);
        ResponseCartBuyDto response = new ResponseCartBuyDto();
        Client client = new Client();
        List<Cart> deletCartList = new ArrayList<>();
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        Optional<Client> optClient = clientRepository.findByLogin(login);
        if (optClient.isPresent())
            client = optClient.get();
        for (RequestProductBuyDto reqProd : buyReq) {
            for (Cart cart : client.getCarts()) {
                if (cart.getProductCart().getId().equals(reqProd.getId())) {
                    if (reqProd.getCount() == null)
                        reqProd.setCount(cart.getCount());
                    if (reqProd.getName().equalsIgnoreCase(cart.getProductCart().getName())
                            || reqProd.getPrice().equals(cart.getProductCart().getPrice())) {
                        if (cart.getCount() <= reqProd.getCount()) {
                            if (cart.getCount() <= cart.getProductCart().getCount()) {
                                reqProd.setCount(cart.getCount());
                                client.setDeposit(client.getDeposit() - (reqProd.getPrice()*reqProd.getCount()));
                                cart.setClientCart(client);
                                response.getBought().add(new ResponseProductBuyDto(reqProd.getId(),
                                        reqProd.getName(), reqProd.getPrice(), reqProd.getCount()));
                                if (client.getDeposit() <= 0) {
                                    throw new ServerExceptions(Collections.singletonList(
                                            new RespError(ServerErrors.WRONG_PURCHASE_DEPOSIT, "deposit",
                                            ServerErrors.WRONG_PURCHASE_DEPOSIT.getErrorMessage())));
                                }
                                addPurchase(client, cart.getProductCart(), reqProd);
                                cart.getProductCart().setCount(cart.getProductCart().getCount() - reqProd.getCount());
                                productRepository.save(cart.getProductCart());
                                deletCartList.add(cart);
                            }
                        } else {
                            if (cart.getCount() <= cart.getProductCart().getCount()) {
                                client.setDeposit(client.getDeposit() - (reqProd.getPrice()*reqProd.getCount()));
                                response.getBought().add(new ResponseProductBuyDto(reqProd.getId(),
                                        reqProd.getName(), reqProd.getPrice(), reqProd.getCount()));
                                cart.setCount(cart.getCount() - reqProd.getCount());
                                addPurchase(client, cart.getProductCart(), reqProd);
                                cart.getProductCart().setCount(cart.getProductCart().getCount() - reqProd.getCount());
                                productRepository.save(cart.getProductCart());
                                cartRepository.save(cart);
                                if (client.getDeposit() >= 0){
                                    cart.setClientCart(client);
                                } else {
                                    throw new ServerExceptions(Collections.singletonList(
                                            new RespError(ServerErrors.WRONG_PURCHASE_DEPOSIT, "deposit",
                                                    ServerErrors.WRONG_PURCHASE_DEPOSIT.getErrorMessage())));
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Cart cart : client.getCarts()){
            if (!deletCartList.contains(cart)) {
                response.getRemaining().add(new ResponseProductBuyDto(cart.getProductCart().getId(),
                        cart.getProductCart().getName(), cart.getProductCart().getPrice(), cart.getCount()));
            } else cartRepository.delete(cart);
        }
        return response;
    }

    private void addPurchase(Client client, Product product, RequestProductBuyDto request){
        Optional<Purchase> optPurchase = purchaseRepository.findByClientAndProduct(client, product);
        if (optPurchase.isPresent()) {
            Purchase purchase = optPurchase.get();
            purchase.setCount(purchase.getCount() + request.getCount());
            purchase.setTotalPrice(purchase.getTotalPrice() + request.getCount() * request.getPrice());
            purchaseRepository.save(purchase);
        } else {
            Purchase purchase = new Purchase(request.getCount(),request.getPrice()*request.getCount(),
                    client, product);
            purchaseRepository.save(purchase);
        }
    }

    public List commonTableInfo(HttpServletRequest httpReq, String command, List<Integer> value, Integer offset,
                                Integer limit) {
        Pageable page;
        List list;
        validator.isCookieNullAdmin(httpReq);
        switch (command) {
            case "info":
                return Collections.singletonList("Commands: prodCat, categories, clientsDetails, purchases, " +
                        "lightPurchases, topProducts," +
                        " totalRevenue");
            case "prodCat":
                if (offset == null || limit == null) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_OFFSET_LIMIT,
                            "offset or limit", ServerErrors.WRONG_OFFSET_LIMIT.getErrorMessage())));
                }
                List<ResponseProductInfoDto> list1 = new ArrayList<>(productService.productsSet(value));
                List<? extends ResponseProductInfoDto> respList = paginationCollection(list1, offset, limit);
                respList.sort((Comparator<ResponseProductInfoDto>) (o1, o2) -> o2.getId().compareTo(o1.getId()));
                return respList;

            case "categories":
                if (offset == null || limit == null) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_OFFSET_LIMIT,
                            "offset or limit", ServerErrors.WRONG_OFFSET_LIMIT.getErrorMessage())));
                }
                list = getAllCategoriesDescIdPageable(offset, limit);
                return list;
            case "clientsDetails":
                if (offset == null || limit == null) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_OFFSET_LIMIT,
                            "offset or limit", ServerErrors.WRONG_OFFSET_LIMIT.getErrorMessage())));
                }
                List<Client> clientsList;
                page = PageRequest.of(offset-1, limit, Sort.Direction.DESC, "id");
                if (value != null){
                    clientsList = new ArrayList<>();
                    for (Integer i: value){
                        Optional<Client> client = clientRepository.findById(i);
                        if (client.isPresent()) {
                            clientsList.add(client.get());
                        } else {
                            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_ID,
                                "id", ServerErrors.WRONG_ID.getErrorMessage())));
                        }
                    }
                } else {
                    clientsList = clientRepository.findAll(page).getContent();
                }
                return getAllClientsWithDetailing(clientsList);
            case "purchases":
                if (offset == null || limit == null) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_OFFSET_LIMIT,
                            "offset or limit", ServerErrors.WRONG_OFFSET_LIMIT.getErrorMessage())));
                }
                page = PageRequest.of(offset-1, limit, Sort.Direction.DESC, "id");
                List<Purchase> purchases = null;
                if (value != null) {
                    for (Integer id : value) {
                        purchases = purchaseRepository.findById(id, page);
                    }
                }else {
                    purchases = purchaseRepository.findAll(page).getContent();
                }
                if (purchases != null && purchases.size()!=0) {
                    return formPurchaseList(purchases);
                } else throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PURCHASES,
                        "maybe id", ServerErrors.WRONG_PURCHASES.getErrorMessage())));
            case "lightPurchases":
                if (offset == null || limit == null) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_OFFSET_LIMIT,
                            "offset or limit", ServerErrors.WRONG_OFFSET_LIMIT.getErrorMessage())));
                }
                page = PageRequest.of(offset-1, limit, Sort.Direction.DESC, "id");
                purchases = null;
                if (value != null) {
                    for (Integer id : value) {
                        purchases = purchaseRepository.findById(id, page);
                    }
                }else {
                    purchases = purchaseRepository.findAll(page).getContent();
                }
                if (purchases != null && purchases.size()!=0) {
                    return formLightPurchasesList(purchases);
                } else throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PURCHASES,
                        "maybe id", ServerErrors.WRONG_PURCHASES.getErrorMessage())));
            case "topProducts":
                if (offset == null || limit == null) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_OFFSET_LIMIT,
                            "offset or limit", ServerErrors.WRONG_OFFSET_LIMIT.getErrorMessage())));
                }
                page = PageRequest.of(offset-1, limit, Sort.Direction.DESC, "product_id");
                purchases = purchaseRepository.findAll(page).getContent();
                return listTopProducts(purchases);
            case "totalRevenue":
                return giveRevenue();
            default:
                throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_COMMAND,
                        "command", ServerErrors.WRONG_COMMAND.getErrorMessage())));
        }
    }

    private List giveRevenue(){
        int total = 0;
        Iterable<Purchase> purchases = purchaseRepository.findAll();
        for (Purchase purch : purchases){
            total = total + purch.getTotalPrice();
        }
        List<ResponseRevenueDto> response = new ArrayList<>();
        response.add(new ResponseRevenueDto(total));
        return response;

    }

    private List listTopProducts(List<Purchase> purchases) {
        List<ResponseProductsTopDto> response = new ArrayList<>();
        int i = 0;
        ResponseProductsTopDto respProduct = new ResponseProductsTopDto();
        for (Purchase purchase : purchases) {
            if (Objects.equals(purchase.getProduct().getId(), respProduct.getProductId())) {
                respProduct.setRevenue(respProduct.getRevenue() + purchase.getTotalPrice());
                respProduct.setUnitSales(respProduct.getUnitSales() + purchase.getCount());
                if (purchases.size()-1 == i) {
                    response.add(new ResponseProductsTopDto(respProduct.getProductId(), respProduct.getProductName(),
                            respProduct.getRevenue(), respProduct.getUnitSales(), respProduct.getCategoryList()));
                }
            } else {
                if (respProduct.getProductId() != null || purchases.size()-1 == i) {
                    response.add(new ResponseProductsTopDto(respProduct.getProductId(), respProduct.getProductName(),
                            respProduct.getRevenue(), respProduct.getUnitSales(), respProduct.getCategoryList()));
                }
                respProduct = new ResponseProductsTopDto(purchase.getProduct().getId(), purchase.getProduct().getName(),
                        purchase.getTotalPrice(), purchase.getCount(), purchase.getProduct().getCategories());
            }
            ++i;
        }
        response.sort((o1, o2) -> o2.getRevenue().compareTo(o1.getRevenue()));
        return response;
    }

    private List<ResponseLightPurchaseDto> formLightPurchasesList(List<Purchase> purchases) {
        List<ResponseLightPurchaseDto> response = new ArrayList<>();
        for (Purchase purchase : purchases){
            response.add(new ResponseLightPurchaseDto(purchase.getId(), purchase.getCount(), purchase.getTotalPrice(),
                    purchase.getClient().getLogin(), purchase.getProduct().getName(), purchase.getProduct().getId()));
        }
        return response;
    }

    private List<ResponsePurchaseDto> formPurchaseList(List<Purchase> purchases) {
        List<ResponsePurchaseDto> purchaseDtos = new ArrayList<>();
        for (Purchase purch : purchases){
            purchaseDtos.add(new ResponsePurchaseDto(purch.getId(), purch.getCount(), purch.getTotalPrice(),
                    new ResponseClientDto(purch.getClient()), new ResponsePurchProdDto(purch.getProduct())));
        }
        return purchaseDtos;
    }

    private <T>List<T> paginationCollection(List<T> list, Integer offset, Integer limit){
        List<T> response = new ArrayList<>();
        if ((offset + limit) <= list.size()-1) {
            for (int i = offset - 1; i < offset + limit; i++) {
                response.add(list.get(i));
            }
        } else return list;
        return response;
    }

    private List<ResponseCategoryDto> getAllCategoriesDescIdPageable(Integer offset, Integer limit){
        List<ResponseCategoryDto> response = new ArrayList<>();
        Pageable page = PageRequest.of(offset-1, limit, Sort.Direction.DESC, "id");
        List<Category> categories = categoryRepository.findByParentId(0, page);
        for (Category category : categories){
            response.add(new ResponseCategoryDto(category.getId(), category.getName(), null, null));
            Iterable<Category> subCategories = categoryRepository.findByParentId(category.getId(), page);
            for (Category subCat : subCategories){
                response.add(new ResponseCategoryDto(subCat.getId(), subCat.getName(),
                        subCat.getParentId(), category.getName()));
            }
        }
        return response;
    }

    private List<ResponseClientsDetailingDto> getAllClientsWithDetailing(List<Client> clientsList){
        List<ResponseClientsDetailingDto> response = new ArrayList<>();
        for (Client client:clientsList){
            response.add(new ResponseClientsDetailingDto(client));
        }
        return response;
    }
}
