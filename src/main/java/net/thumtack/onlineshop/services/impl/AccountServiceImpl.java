package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.CookieDataRepository;
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
import net.thumtack.onlineshop.services.interfaces.AccountService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private Validator validator;
    private CookieDataRepository cookRepo;

    @Autowired
    public AccountServiceImpl(Validator validator, CookieDataRepository cookRepo) {
        this.validator = validator;
        this.cookRepo = cookRepo;
    }

    @Override
    public LoginResponse info(HttpServletRequest httpReq) throws ServerExceptions {
        Optional<? extends User> optional;
        Cookie cookie = validator.checkCookie(httpReq.getCookies());
        Optional<CookieData> cookieData = cookRepo.findById(cookie.getValue());
        if (cookieData.isPresent()) {
            optional = validator.validLogin(cookieData.get().getLogin());
        }
        else {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        }
        if (optional.isPresent()) {
            if (optional.get() instanceof Administrator) {
                ResponseAdminDto respAdmin = new ResponseAdminDto((Administrator) optional.get());
                LoginResponse logResp = new LoginResponse();
                logResp.setRespAdmin(respAdmin);
                return logResp;
            } else {
                ResponseClientDto respClient = new ResponseClientDto((Client) optional.get());
                LoginResponse logResp = new LoginResponse();
                logResp.setRespClient(respClient);
                return logResp;
            }
        }
        throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                ServerErrors.INVALID_COOKIE.getErrorMessage())));
    }
}
