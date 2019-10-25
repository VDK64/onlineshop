package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Response.LoginResponse;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("accounts")
    public ResponseEntity info(HttpServletRequest httpReq) {
        LoginResponse logResp;
        logResp = accountService.info(httpReq);
        return ResponseEntity.ok(logResp.toString());
    }
}
