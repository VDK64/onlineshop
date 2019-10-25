package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dto.Request.RequestDepositDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import net.thumtack.onlineshop.services.interfaces.DepositService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
public class DepositServiceImpl implements DepositService {
    private Validator validator;
    private ClientRepository clientRepository;
    private ClientService clientService;

    @Autowired
    DepositServiceImpl(Validator validator, ClientRepository clientRepository, ClientService clientService) {
        this.clientService = clientService;
        this.validator = validator;
        this.clientRepository = clientRepository;
    }

    @Override
    public ResponseClientDto addDeposit(HttpServletRequest httpReq,
                                        RequestDepositDto requestDepositDto) {
        validator.checkCookie(httpReq.getCookies());
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        validator.negativeDepo(requestDepositDto.getDeposit());
        Client client = clientRepository.findByLogin(login).orElse(new Client());
        client.setDeposit(client.getDeposit() + requestDepositDto.getDeposit());
        return new ResponseClientDto(client);
    }

    @Override
    public ResponseClientDto withdraw(HttpServletRequest httpReq) {
        validator.checkCookieClient(httpReq.getCookies());
        String login = clientService.giveClientLoginByCookie(httpReq.getCookies());
        Client client = clientRepository.findByLogin(login).orElse(new Client());
        if (client.getDeposit() == 0)
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_WITHDRAW,
                    "deposit", ServerErrors.WRONG_WITHDRAW.getErrorMessage())));
        client.setDeposit(0);
        return new ResponseClientDto(clientRepository.save(client));
    }
}
