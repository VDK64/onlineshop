package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestLoginDto;
import net.thumtack.onlineshop.dto.Response.LoginResponse;
import net.thumtack.onlineshop.dto.Response.ResponseAdminDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.entities.Administrator;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.entities.abstractClass.User;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.SessionService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {
    private CookieDataRepository cookieRepo;
    private Validator validator;

    @Autowired
    public SessionServiceImpl(CookieDataRepository cookieRepo, Validator validator) {
        this.cookieRepo = cookieRepo;
        this.validator = validator;
    }

    @Override
    public LoginResponse logIn(RequestLoginDto requestLoginDto, HttpServletRequest request) {
        ResponseAdminDto respAdmin;
        ResponseClientDto respClient;
       Optional<? extends User> optional =  validator.validLogin(requestLoginDto.getLogin());
       if (optional.isPresent()) {
           if (optional.get() instanceof Administrator) {
               if (optional.get().getPassword().equals(requestLoginDto.getPassword())) {
                   validator.nullCookieLoginAdmin(request);
                   respAdmin = new ResponseAdminDto((Administrator) optional.get());
                   LoginResponse logResp = new LoginResponse();
                   logResp.setRespAdmin(respAdmin);
                   return logResp;
               }
           }
           if (optional.get() instanceof Client) {
               if (optional.get().getPassword().equals(requestLoginDto.getPassword())) {
                   validator.nullCookieLoginClient(request);
                   respClient = new ResponseClientDto((Client) optional.get());
                   LoginResponse logResp = new LoginResponse();
                   logResp.setRespClient(respClient);
                   return logResp;
               }
           }
       }
       throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PASSWORD_LOG_IN,
               "login/password", ServerErrors.WRONG_PASSWORD_LOG_IN.getErrorMessage())));
    }

    @Override
    public void logOut(HttpServletRequest httpReq) {
        if (httpReq.getCookies() == null) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.ALREADY_LOG_OUT,
                    "cookie", ServerErrors.ALREADY_LOG_IN.getErrorMessage())));
        }
        for (Cookie cookie : httpReq.getCookies()){
            Optional<CookieData> optCook = cookieRepo.findById(cookie.getValue());
            if (optCook.isPresent()) {
                validator.validLogin(optCook.get().getLogin());
                cookieRepo.delete(optCook.get());
            }
        }
    }
}
