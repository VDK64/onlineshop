package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Request.RequestAdminDto;
import net.thumtack.onlineshop.dto.Request.RequestChangePassAdminDto;
import net.thumtack.onlineshop.dto.Response.ResponseAdminDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("admins")
    public ResponseEntity regAdmin(HttpServletResponse response,
                                   @RequestBody RequestAdminDto reqAdmin) throws ServerExceptions {
        ResponseAdminDto respAdmin;
        respAdmin = adminService.regAdmin(reqAdmin);
        response.addCookie(adminService.createCookieAdmin(reqAdmin.getLogin()));
        return ResponseEntity.ok(respAdmin);
    }

    @PutMapping("admins")
    public ResponseEntity updateAdmin(HttpServletRequest httpReq,
                                      @RequestBody RequestChangePassAdminDto requestChangePass) throws ServerExceptions {
            return ResponseEntity.ok(adminService.updateAdmin(requestChangePass, httpReq));
    }
}
