package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.CartRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductBuyDto;
import net.thumtack.onlineshop.entities.Cart;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.CartService;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private Validator validator;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;
    private ClientService clientService;

    @Autowired
    CartServiceImpl(CartRepository cartRepository, Validator validator, ProductRepository productRepository,
                    ClientRepository clientRepository, ClientService clientService) {
        this.cartRepository = cartRepository;
        this.validator = validator;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    @Override
    public ResponseCartDto addCartProduct(HttpServletRequest httpReq, RequestProductBuyDto request) throws ServerExceptions {
        List<RespError> errorList = new ArrayList<>();
        validator.isCookieNullCLient(httpReq);
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        Optional<Product> optProduct = productRepository.findById(request.getId());
        Product product = null;
        if (optProduct.isPresent()) {
            product = optProduct.get();
        } else {
            errorList.add(new RespError(ServerErrors.WRONG_PRODUCT, "id",
                    ServerErrors.WRONG_PRODUCT.getErrorMessage()));
        }
        Client client = clientRepository.findByLogin(login).orElseThrow(()-> new ServerExceptions(Collections.singletonList(
              new RespError(  ServerErrors.INVALID_COOKIE, "cookie", ServerErrors.INVALID_COOKIE.getErrorMessage()))));
        errorList = validator.validAddCart(product, request, errorList);
        Optional<Cart> optCart = cartRepository.findByClientCartAndProductCart(client, product);
        if (optCart.isPresent()) {
            errorList.add(new RespError(ServerErrors.WRONG_CARTPRODUCT_EXIST, "id",
                    ServerErrors.WRONG_CARTPRODUCT_EXIST.getErrorMessage()));
        }
        if (!errorList.isEmpty()) { throw new ServerExceptions(errorList); }
        Cart cart;
        cart = new Cart(client, product, request.getCount());
        cartRepository.save(cart);
        ResponseCartDto response = new ResponseCartDto();
        response.getCart().add(new ResponseProductBuyDto(cart.getProductCart().getId(), cart.getProductCart().getName(),
                cart.getProductCart().getPrice(), cart.getCount()));
        response = formCart(client, response);
        if (!errorList.isEmpty()) {
            throw new ServerExceptions(errorList);
        }
        return response;
    }

    @Override
    public void deleteCartProduct(HttpServletRequest httpReq, Integer id) throws ServerExceptions {
        validator.isCookieNullCLient(httpReq);
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        Client client = clientRepository.findByLogin(login)
                .orElseThrow(() -> new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE,
                        "cookie", ServerErrors.INVALID_COOKIE.getErrorMessage()))));
        Cart cart = client.getCarts().stream()
                .filter(card -> card.getProductCart().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CARTPRODUCT_DELETE,
                        "id", ServerErrors.WRONG_CARTPRODUCT_DELETE.getErrorMessage()))));
        httpReq.getSession().setAttribute("deleteCart", cart);
        cartRepository.delete(cart);
    }

    @Override
    public ResponseCartDto updateCartProduct(HttpServletRequest httpReq, RequestProductBuyDto request) throws ServerExceptions {
        List<RespError> errorList = new ArrayList<>();
        validator.isCookieNullCLient(httpReq);
        Client client = null;
        Product product = null;
        Cart cart;
        Cart deletedCart = (Cart) httpReq.getSession().getAttribute("deleteCart");
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        Optional<Client> optClient = clientRepository.findByLogin(login);
        Optional<Product> optProduct = productRepository.findById(request.getId());
        if (optProduct.isPresent()) {
            product = optProduct.get();
        }
        else {
            errorList.add(new RespError(ServerErrors.WRONG_PRODUCT, "id", ServerErrors.WRONG_PRODUCT.getErrorMessage()));
        }
        if (optClient.isPresent()) {
            client = optClient.get();
        }
        errorList = validator.validAddCart(product, request, errorList);
        Optional<Cart> optCart = cartRepository.findByClientCartAndProductCart(client, product);
        ResponseCartDto response = new ResponseCartDto();
        if (!errorList.isEmpty()) { throw new ServerExceptions(errorList); }
        if (optCart.isPresent()) {
            cart = optCart.get();
            cart.setCount(request.getCount());
            cartRepository.save(cart);
        }
        else if (deletedCart != null) {
         response = formDeletedCart(request, deletedCart);
        }
        else {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CARTPRODUCT_DELETE,
                    "id", ServerErrors.WRONG_CARTPRODUCT_DELETE.getErrorMessage())));
        }
        if (client != null) {
            response = formCart(client, response);
        }
        httpReq.getSession().removeAttribute("deleteCart");
        return response;
    }

    @Override
    public ResponseCartDto allCartProducts(HttpServletRequest httpReq) throws ServerExceptions {
        validator.isCookieNullCLient(httpReq);
        ResponseCartDto response = new ResponseCartDto();
        Client client = null;
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        Optional<Client> optClient = clientRepository.findByLogin(login);
        if (optClient.isPresent())
            client = optClient.get();
        for (Cart clientCart : Objects.requireNonNull(client).getCarts()){
            response.getCart().add(new ResponseProductBuyDto(clientCart.getProductCart().getId(),
                    clientCart.getProductCart().getName(), clientCart.getProductCart().getPrice(),
                    clientCart.getCount()));
        }
        return response;
    }

    private ResponseCartDto formCart(Client client, ResponseCartDto response) {
        for (Cart clientCart : Objects.requireNonNull(client).getCarts()){
            response.getCart().add(new ResponseProductBuyDto(clientCart.getProductCart().getId(),
                    clientCart.getProductCart().getName(), clientCart.getProductCart().getPrice(),
                    clientCart.getCount()));
        }
        return response;
    }

    private ResponseCartDto formDeletedCart(RequestProductBuyDto request, Cart deletedCart) {
        ResponseCartDto response = new ResponseCartDto();
        if (request.getId().equals(deletedCart.getProductCart().getId())) {
            deletedCart.setCount(request.getCount());
            cartRepository.save(deletedCart);
            response.getCart().add(new ResponseProductBuyDto(deletedCart.getProductCart().getId(),
                    deletedCart.getProductCart().getName(), deletedCart.getProductCart().getPrice(),
                    deletedCart.getCount()));
        }
        return response;
    }
}
