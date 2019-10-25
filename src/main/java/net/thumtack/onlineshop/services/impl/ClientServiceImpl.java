package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestChangePassClientDto;
import net.thumtack.onlineshop.dto.Request.RequestClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientChangePassDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientsInfoDto;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private Validator validator;
    private CookieDataRepository cookieRepo;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, Validator validator, CookieDataRepository cookieRepo) {
        this.clientRepository = clientRepository;
        this.validator = validator;
        this.cookieRepo = cookieRepo;
    }

    @Override
    public ResponseClientDto regUser(RequestClientDto reqClient) {
        List<RespError> errorList;
        Client client = reqClient.createClient();
        client.setDeposit(0);
        errorList = validator.regCheckClient(client);
        errorList = validator.checkClientLoginRepeat(client.getLogin(), errorList);
        if (!errorList.isEmpty())
            throw new ServerExceptions(errorList);
        client = clientRepository.save(client);
        return new ResponseClientDto(client);
    }

    @Override
    public ResponseClientChangePassDto updateUser(HttpServletRequest httpReq,
                                                  RequestChangePassClientDto reqChPassClient) {
        validator.isCookieNullCLient(httpReq);
        List<RespError> errorList = validator.checkClientToChangePass(reqChPassClient);
        reqChPassClient.setLogin(giveClientLoginByCookie(httpReq.getCookies()));
        Optional<Client> clientFromDB = clientRepository.findByLogin(reqChPassClient.getLogin());
        if (!clientFromDB.isPresent()) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE,
                    "cookie", ServerErrors.INVALID_COOKIE.getErrorMessage())));
        }
        Client client = clientFromDB.get();
        if (!client.getPassword().equals(reqChPassClient.getOldPassword())) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PASSWORD,
                    "password", ServerErrors.WRONG_PASSWORD.getErrorMessage())));
        }
        if (!errorList.isEmpty()){
            throw new ServerExceptions(errorList);
        }
        client.setPassword(reqChPassClient.getNewPassword());
        clientRepository.save(client);
        return new ResponseClientChangePassDto(client);
    }

    @Override
    public List<ResponseClientsInfoDto> userInfo(HttpServletRequest httpReq) {
        validator.isCookieNullAdmin(httpReq);
        Iterable<Client> clients = clientRepository.findAll();
        List<ResponseClientsInfoDto> list = new ArrayList<>();
        for (Client value : clients) {
            list.add(new ResponseClientsInfoDto(value));
        }
        return list;
    }

    public String giveClientLoginByCookie(Cookie[] cookies) {
        int i = 0;
        Optional<CookieData> testCookie = Optional.empty();
        for (Cookie cookie: cookies){
            if (cookie.getName().equalsIgnoreCase("client")) {
                testCookie = cookieRepo.findById(cookie.getValue());
                if (testCookie.isPresent() && testCookie.get().getStatus().equalsIgnoreCase("client"))
                    ++i;
            }
        }
        if (i > 1) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.MULTICOOCKING, "cookie",
                    ServerErrors.MULTICOOCKING.getErrorMessage())));
        }
        if (!testCookie.isPresent()) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        }
        return testCookie.get().getLogin();
    }

    public Cookie createCookieClient(String login) {
        Optional<CookieData> cook = cookieRepo.findByLogin(login);
        String token = String.valueOf(UUID.randomUUID());
        Cookie cookie = new Cookie("client", token);
        cookie.setMaxAge(7200);
        cook.ifPresent(cookieData -> cookieRepo.delete(cookieData));
        cookieRepo.save(new CookieData(cookie.getName(), cookie.getValue(), login));
        return cookie;
    }
}
