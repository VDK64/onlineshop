package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Request.RequestLoginDto;
import net.thumtack.onlineshop.dto.Response.LoginResponse;
import net.thumtack.onlineshop.dto.Response.ResponseEmptyDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.AdminService;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import net.thumtack.onlineshop.services.interfaces.SessionService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/")
public class SessionController {

    private SessionService sessionService;
    private AdminService adminService;
    private ClientService clientService;

    @Autowired
    public SessionController(SessionService sessionService, Validator validator, AdminService adminService,
                             ClientService clientService) {
        this.sessionService = sessionService;
        this.adminService = adminService;
        this.clientService = clientService;
    }

    @PostMapping("sessions")
    public ResponseEntity logIn(HttpServletRequest httpReq, HttpServletResponse response,
                                @RequestBody RequestLoginDto requestLoginDto) {
        LoginResponse logResp;
        logResp = sessionService.logIn(requestLoginDto, httpReq);
        if (logResp.whoIs().equalsIgnoreCase("admin"))
            response.addCookie(adminService.createCookieAdmin(requestLoginDto.getLogin()));
        else response.addCookie(clientService.createCookieClient(requestLoginDto.getLogin()));
        return ResponseEntity.ok(logResp.toString());
    }

    @DeleteMapping("sessions")
    public ResponseEntity logOut(HttpServletRequest httpReq) {
        sessionService.logOut(httpReq);
        return ResponseEntity.ok(ResponseEmptyDto.voidResponse);
    }

}
