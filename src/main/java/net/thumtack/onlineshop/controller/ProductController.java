package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Request.RequestProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseEmptyDto;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.AccountService;
import net.thumtack.onlineshop.services.interfaces.ProductService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("api/")
public class ProductController {

    private ProductService productService;
    private Validator validator;

    @Autowired
    public ProductController(ProductService productService, Validator validator, AccountService accountService) {
        this.productService = productService;
        this.validator = validator;
    }

    @PostMapping("products")
    public ResponseEntity addProduct(HttpServletRequest httpReq,
                                     @RequestBody RequestProductDto requestProductDto) {
        return ResponseEntity.ok(productService.addProduct(httpReq, requestProductDto));
    }

    @PutMapping("products/{id}")
    public ResponseEntity updateProduct(HttpServletRequest httpReq, @PathVariable Integer id,
                                        @RequestBody RequestProductDto requestProductDto) {
        return ResponseEntity.ok(productService.updateProduct(httpReq, id, requestProductDto));
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity deleteProduct(HttpServletRequest httpReq, @PathVariable Integer id) {
        productService.deleteProduct(httpReq, id);
        return ResponseEntity.ok(ResponseEmptyDto.voidResponse);
    }

    @GetMapping("products/{id}")
    public ResponseEntity infoProduct(HttpServletRequest httpReq, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.infoProduct(httpReq, id));

    }

    @GetMapping("products")
    public ResponseEntity productsList(HttpServletRequest httpReq,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) String order) {
        List respList = new ArrayList();
        Set respSet = new TreeSet();
        validator.checkCookie(httpReq.getCookies());
        if (order == null || order.equalsIgnoreCase("product")) {
            respSet = productService.productsSet(categories);
        }
        else if (("categories").equalsIgnoreCase(order)) {
            respList = productService.productsList(categories);
        }
        else {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_ORDER, "order",
                    ServerErrors.WRONG_ORDER.getErrorMessage())));
        }
        if (respSet.size() != 0) {
            return ResponseEntity.ok(respSet);
        }
       else return  ResponseEntity.ok(respList);
    }
}
