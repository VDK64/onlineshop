package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseEmptyDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("baskets")
    public ResponseEntity addCartProduct(HttpServletRequest httpReq, @RequestBody RequestProductBuyDto request) {
        return ResponseEntity.ok(cartService.addCartProduct(httpReq, request));

    }

    @DeleteMapping("baskets/{id}")
    public ResponseEntity deleteCartProduct(HttpServletRequest httpReq, @PathVariable Integer id) {
        cartService.deleteCartProduct(httpReq, id);
        return ResponseEntity.ok(ResponseEmptyDto.voidResponse);

    }

    @PutMapping("baskets")
    public ResponseEntity updateCartProduct(HttpServletRequest httpReq,
                                            @RequestBody RequestProductBuyDto request) {
        return ResponseEntity.ok(cartService.updateCartProduct(httpReq, request));

    }

    @GetMapping("baskets")
    public ResponseEntity allCartProducts(HttpServletRequest httpReq) {
        return ResponseEntity.ok(cartService.allCartProducts(httpReq));

    }
}
