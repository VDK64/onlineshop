package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import net.thumtack.onlineshop.dto.Request.RequestProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductInfoDto;
import net.thumtack.onlineshop.entities.Category;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ProductService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private Validator validator;

    @Autowired
    ProductServiceImpl(ProductRepository productRepository, Validator validator, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.validator = validator;
    }

    @Override
    public ResponseProductDto addProduct(HttpServletRequest httpReq, RequestProductDto requestProductDto) throws ServerExceptions {
        List<RespError> errorList = new ArrayList<>();
        validator.isCookieNullAdmin(httpReq);
        errorList = validator.checkPrice(requestProductDto.getPrice(), errorList);
        Product product = requestProductDto.createProductWithoutCat();
        for (Integer id : requestProductDto.getCategories()) {
            Optional<Category> optCat = categoryRepository.findById(id);
            if (!categoryRepository.findById(id).isPresent()) {
                errorList.add(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT, "category",
                        ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage()));
            }
            optCat.ifPresent(category -> product.getCategories().add(category));
        }
        if (!errorList.isEmpty()) { throw new ServerExceptions(errorList); }
        Product savedProduct = productRepository.save(product);
        return new ResponseProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(),
                savedProduct.getCount(), requestProductDto.getCategories());
    }

    @Override
    public ResponseProductDto updateProduct(HttpServletRequest httpReq, Integer id,
                                            RequestProductDto requestProductDto) throws ServerExceptions {
        List<RespError> errorList = new ArrayList<>();
        validator.isCookieNullAdmin(httpReq);
        requestProductDto.setId(id);
        errorList = validator.checkPriceUpdate(requestProductDto.getPrice(), errorList);
        Product oldProd = productRepository.findById(requestProductDto.getId()).orElseThrow(() -> new ServerExceptions(
                Collections.singletonList(new RespError(ServerErrors.WRONG_PRODUCT,"id", ServerErrors.WRONG_PRODUCT.getErrorMessage()))));
        Product product = requestProductDto.createProductWithoutCat();
        product.setId(requestProductDto.getId());
        if (requestProductDto.getCategories() != null) {
            for (Integer id1 : requestProductDto.getCategories()) {
                Optional<Category> optCat = categoryRepository.findById(id1);
                if (!optCat.isPresent()) {
                    errorList.add(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT, "category", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage()));
                }
                optCat.ifPresent(category -> product.getCategories().add(category));
            }
        }
        createNewProduct(product, oldProd);
        if (requestProductDto.getCategories() == null) {
            product.setCategories(oldProd.getCategories());
        }
        if (!errorList.isEmpty()) { throw new ServerExceptions(errorList); }
        Product savedProduct = productRepository.save(product);
        ResponseProductDto respProd = new ResponseProductDto();
        respProd.setCategories(new ArrayList<>());
        for (Category cat : savedProduct.getCategories()) {
            respProd.getCategories().add(cat.getId());
        }
        respProd.setId(savedProduct.getId());
        respProd.setName(savedProduct.getName());
        respProd.setPrice(savedProduct.getPrice());
        respProd.setCount(savedProduct.getCount());
        return respProd;
    }

    @Override
    public void deleteProduct(HttpServletRequest httpReq, Integer id) throws ServerExceptions {
        validator.isCookieNullAdmin(httpReq);
        Optional<Product> prodOpt = productRepository.findById(id);
        if (!prodOpt.isPresent())
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PRODUCT, "id",
                    ServerErrors.WRONG_PRODUCT.getErrorMessage())));
        productRepository.deleteById(id);
    }

    @Override
    public ResponseProductInfoDto infoProduct(HttpServletRequest httpReq, Integer id) throws ServerExceptions {
        validator.checkCookie(httpReq.getCookies());
        Optional<Product> prodOpt = productRepository.findById(id);
        if (!prodOpt.isPresent())
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PRODUCT, "id",
                    ServerErrors.WRONG_PRODUCT.getErrorMessage())));
        ResponseProductInfoDto respProd = new ResponseProductInfoDto(prodOpt.get().getId(), prodOpt.get().getName(),
                prodOpt.get().getCount(), prodOpt.get().getPrice());
        for (Category cat : prodOpt.get().getCategories()) {
            respProd.getCategoryName().add(cat.getName());
        }
        return respProd;
    }

    @Override
    public Set productsSet(List<Integer> categories) throws ServerExceptions {
        List<ResponseProductInfoDto> resultList = getProducts(categories);
        return new HashSet<>(resultList);
    }

    @Override
    public List<ResponseProductInfoDto> productsList(List<Integer> categories) throws ServerExceptions {
        List<ResponseProductInfoDto> resultList = getProducts(categories);
        resultList = getSortedProductsByList(resultList);
        return resultList;
    }

    private List<ResponseProductInfoDto> getProducts(List<Integer> categories) throws ServerExceptions {
        List<ResponseProductInfoDto> result = new ArrayList<>();
        if (categories == null) {
            Iterable <Product> prod;
            prod = productRepository.findAll();
            for (Product product : prod){
                ResponseProductInfoDto resp = new ResponseProductInfoDto(product.getId(), product.getName(), product.getCount(),
                        product.getPrice());
                for (Category cat : product.getCategories())
                    resp.getCategoryName().add(cat.getName());
                result.add(resp);
            }
        }
        if (categories != null && categories.size() != 0 ){
            Iterable<Product> products = productRepository.findAll();;
            Optional<Category> category;
            for (Integer cat : categories){
                category = categoryRepository.findById(cat);
                if (!category.isPresent()) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT,
                            "categories", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage())));
                }
                for (Product product : products){
                    if (product.getCategories().contains(category.get())){
                        ResponseProductInfoDto resp = new ResponseProductInfoDto(product.getId(),
                                product.getName(), product.getCount(), product.getPrice());
                        for (Category categ : product.getCategories())
                            resp.getCategoryName().add(categ.getName());
                        result.add(resp);
                    }
                }
            }
        }
        if (categories != null && categories.size() == 0){
            Iterable<Product> prod =  productRepository.findAll();
            for (Product product : prod){
                if (product.getCategories().size() == 0){
                    ResponseProductInfoDto resp = new ResponseProductInfoDto(product.getId(), product.getName(), product.getCount(),
                            product.getPrice());
                    result.add(resp);
                }
            }
        }
        Set<ResponseProductInfoDto> sortedSet = new TreeSet<>(Comparator.comparing(ResponseProductInfoDto::getName));
        sortedSet.addAll(result);
        result.clear();
        result.addAll(sortedSet);
        return result;
    }

    private List<ResponseProductInfoDto> getSortedProductsByList(List<ResponseProductInfoDto> products){
        List<ResponseProductInfoDto> resList = new ArrayList<>();
        ResponseProductInfoDto resp;
        for (ResponseProductInfoDto prod : products){
            if (prod.getCategoryName().size() == 0 || prod.getCategoryName() == null) {
                resp = new ResponseProductInfoDto(prod.getId(), prod.getName(), prod.getCount(), prod.getPrice());
                resp.getCategoryName().add("");
                resList.add(resp);
            }
            for (String cat : prod.getCategoryName()){
                resp = new ResponseProductInfoDto(prod.getId(), prod.getName(), prod.getCount(), prod.getPrice());
                resp.getCategoryName().add(cat);
                resList.add(resp);
            }
        }
        resList.sort(Comparator.comparing(o -> o.getCategoryName().get(0)));
        return resList;
    }

    private void createNewProduct(Product product, Product oldProduct) {
        if (product.getName() == null)
            product.setName(oldProduct.getName());
        if (product.getCount() == null)
            product.setCount(oldProduct.getCount());
        if (product.getPrice() == null)
            product.setPrice(oldProduct.getPrice());
    }

}
