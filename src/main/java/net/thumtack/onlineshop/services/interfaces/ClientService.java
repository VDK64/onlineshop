package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestChangePassClientDto;
import net.thumtack.onlineshop.dto.Request.RequestClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientChangePassDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientsInfoDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ClientService {

    ResponseClientDto regUser(RequestClientDto reqClient);
    ResponseClientChangePassDto updateUser(HttpServletRequest httpReq,
                                           RequestChangePassClientDto requestChangePassClientDto);
    List<ResponseClientsInfoDto> userInfo(HttpServletRequest httpReq);
    String giveClientLoginByCookie(Cookie[] cookies);
    Cookie createCookieClient(String login);

}
