package net.thumtack.onlineshop.controller;


import net.thumtack.onlineshop.dto.Request.RequestDepositDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/")
public class DepositController {
    private DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PutMapping("deposits")
    public ResponseEntity addDeposit(HttpServletRequest httpReq, @RequestBody RequestDepositDto requestDepositDto) throws ServerExceptions {
        return ResponseEntity.ok(depositService.addDeposit(httpReq, requestDepositDto));

    }

    @GetMapping("deposits")
    public ResponseEntity withdraw(HttpServletRequest httpReq) throws ServerExceptions {
        return ResponseEntity.ok(depositService.withdraw(httpReq));

    }
}
