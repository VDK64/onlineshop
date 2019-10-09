package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestAdminDto;
import net.thumtack.onlineshop.dto.Request.RequestChangePassAdminDto;
import net.thumtack.onlineshop.dto.Response.ResponseAdminChangePassDto;
import net.thumtack.onlineshop.dto.Response.ResponseAdminDto;
import net.thumtack.onlineshop.entities.Administrator;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.AdminService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {
    private AdministratorRepository administratorRepository;
    private Validator validator;
    private CookieDataRepository cookieRepo;

    @Autowired
    AdminServiceImpl(AdministratorRepository administratorRepository, Validator validator, CookieDataRepository cookieRepo) {
        this.administratorRepository = administratorRepository;
        this.validator = validator;
        this.cookieRepo = cookieRepo;
    }

    @Override
    public ResponseAdminDto regAdmin(RequestAdminDto reqAdmin) throws ServerExceptions {
        List<RespError> errorList;
        Administrator admin = reqAdmin.buildAdmin();
        errorList = validator.regCheckAdmin(admin);
        errorList = validator.checkAdminLoginRepeat(admin.getLogin(), errorList);
        if (!errorList.isEmpty())
            throw new ServerExceptions(errorList);
        admin = administratorRepository.save(admin);
        return new ResponseAdminDto(admin);
    }

    @Override
    public ResponseAdminChangePassDto updateAdmin(RequestChangePassAdminDto requestChangePassAdminDto,
                                                  HttpServletRequest httpReq) throws ServerExceptions {
        validator.isCookieNullAdmin(httpReq);
        requestChangePassAdminDto.setLogin(giveAdminLoginByCookie(httpReq.getCookies()));
        Optional<Administrator> adminFromDB = administratorRepository.findByLogin(requestChangePassAdminDto.getLogin());
        if (!adminFromDB.isPresent()) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        }
        List<RespError> errorList = validator.checkToUpdatePasswordByAdmin(requestChangePassAdminDto);
        if (!errorList.isEmpty()) { throw new ServerExceptions(errorList); }
        Administrator admin = adminFromDB.get();
        if (!admin.getPassword().equals(requestChangePassAdminDto.getOldPassword())) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PASSWORD_LOG_IN, "passwrod",
                    ServerErrors.WRONG_PASSWORD_LOG_IN.getErrorMessage())));
        }
        validator.checkPassword(requestChangePassAdminDto.getNewPassword());
        admin.setPassword(requestChangePassAdminDto.getNewPassword());
        administratorRepository.save(admin);
        ResponseAdminChangePassDto respAdmin = new ResponseAdminChangePassDto(admin);
        respAdmin.setOldPassword(requestChangePassAdminDto.getOldPassword());
        return respAdmin;
    }

    public String giveAdminLoginByCookie(Cookie[] cookies) throws ServerExceptions {
        int cookieCount = 0;
        Optional<CookieData> testCookie = Optional.empty();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("admin")) {
                testCookie = cookieRepo.findById(cookie.getValue());
                if (testCookie.isPresent() && testCookie.get().getStatus().equalsIgnoreCase("admin"))
                    ++cookieCount;
            }
        }
            if (cookieCount > 1) {
                throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.MULTICOOCKING, "cookie",
                        ServerErrors.MULTICOOCKING.getErrorMessage())));
            }
            if (!testCookie.isPresent()) {
                throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                        ServerErrors.INVALID_COOKIE.getErrorMessage())));
            }
            return testCookie.get().getLogin();
        }

    public Cookie createCookieAdmin(String login) {
        Optional<CookieData> cook = cookieRepo.findByLogin(login);
        String token = String.valueOf(UUID.randomUUID());
        Cookie cookie = new Cookie("admin", token);
        cookie.setMaxAge(7200);
        cook.ifPresent(cookieData -> cookieRepo.delete(cookieData));
        cookieRepo.save(new CookieData(cookie.getName(), cookie.getValue(), login));
        return cookie;
    }
}
