package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    ResponseCartDto addCartProduct(HttpServletRequest httpReq, RequestProductBuyDto request);
    void deleteCartProduct(HttpServletRequest httpReq, Integer id);
    ResponseCartDto updateCartProduct(HttpServletRequest httpReq, RequestProductBuyDto request);
    public ResponseCartDto allCartProducts(HttpServletRequest httpReq);
}
