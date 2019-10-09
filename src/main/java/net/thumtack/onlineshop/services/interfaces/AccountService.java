package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Response.LoginResponse;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {
     LoginResponse info(HttpServletRequest httpReq) throws ServerExceptions;
}
