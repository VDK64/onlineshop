package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    ResponseCartDto addCartProduct(HttpServletRequest httpReq, RequestProductBuyDto request) throws ServerExceptions;
    void deleteCartProduct(HttpServletRequest httpReq, Integer id) throws ServerExceptions;
    ResponseCartDto updateCartProduct(HttpServletRequest httpReq, RequestProductBuyDto request) throws ServerExceptions;
    public ResponseCartDto allCartProducts(HttpServletRequest httpReq) throws ServerExceptions;
}
