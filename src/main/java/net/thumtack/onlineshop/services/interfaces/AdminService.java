package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestAdminDto;
import net.thumtack.onlineshop.dto.Request.RequestChangePassAdminDto;
import net.thumtack.onlineshop.dto.Response.ResponseAdminChangePassDto;
import net.thumtack.onlineshop.dto.Response.ResponseAdminDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    ResponseAdminDto regAdmin(RequestAdminDto requestAdminDto);
    ResponseAdminChangePassDto updateAdmin(RequestChangePassAdminDto requestChangePassAdminDto,
                                           HttpServletRequest httpReq);
    String giveAdminLoginByCookie(Cookie[] cookies);
    Cookie createCookieAdmin(String login);
}
