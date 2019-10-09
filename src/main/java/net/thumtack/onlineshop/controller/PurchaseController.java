package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ProductService;
import net.thumtack.onlineshop.services.interfaces.PurchaseService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/")
public class PurchaseController {
    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(Validator validator, PurchaseService purchaseService, ProductService productService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("purchases")
    public ResponseEntity buyProduct(HttpServletRequest httpReq,
                                     @RequestBody RequestProductBuyDto request) throws ServerExceptions {
        return ResponseEntity.ok(purchaseService.buyProduct(httpReq, request));
    }

    @PostMapping("purchases/baskets")
    public ResponseEntity buyCartProducts(HttpServletRequest httpReq,
                                          @RequestBody List<RequestProductBuyDto> buyReq) throws ServerExceptions {
        return ResponseEntity.ok(purchaseService.buyCartProducts(httpReq, buyReq));

    }

    @GetMapping("purchases")
    public ResponseEntity commonTableInfo(HttpServletRequest httpReq, @RequestParam String command,
                                          @RequestParam(required = false) List<Integer> value,
                                          @RequestParam(required = false) Integer offset,
                                          @RequestParam(required = false) Integer limit) throws ServerExceptions {
        return ResponseEntity.ok(purchaseService.commonTableInfo(httpReq, command, value, offset, limit));
    }
}
