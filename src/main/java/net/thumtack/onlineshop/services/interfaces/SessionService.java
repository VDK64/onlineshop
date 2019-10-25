package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestLoginDto;
import net.thumtack.onlineshop.dto.Response.LoginResponse;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {
    LoginResponse logIn (RequestLoginDto requestLoginDto, HttpServletRequest request);
    void logOut (HttpServletRequest httpReq);
}
