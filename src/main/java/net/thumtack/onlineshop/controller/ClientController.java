package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Request.RequestChangePassClientDto;
import net.thumtack.onlineshop.dto.Request.RequestClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("clients")
    public ResponseEntity regClient(HttpServletResponse httpResp, @RequestBody RequestClientDto reqClient) {
        ResponseClientDto respClient;
        respClient = clientService.regUser(reqClient);
        httpResp.addCookie(clientService.createCookieClient(reqClient.getLogin()));
        return ResponseEntity.ok(respClient);
    }

    @PutMapping("clients")
    public ResponseEntity updateClient(HttpServletRequest httpReq, @RequestBody RequestChangePassClientDto reqChPassClient) {
        return ResponseEntity.ok(clientService.updateUser(httpReq, reqChPassClient));
    }

    @GetMapping("clients")
    public ResponseEntity userInfo(HttpServletRequest httpReq) {
        return ResponseEntity.ok(clientService.userInfo(httpReq));
    }
}
