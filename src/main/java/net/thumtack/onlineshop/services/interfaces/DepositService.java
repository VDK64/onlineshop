package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestDepositDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;

public interface DepositService {

    ResponseClientDto addDeposit(HttpServletRequest httpReq, RequestDepositDto requestDepositDto);
    ResponseClientDto withdraw(HttpServletRequest httpReq);

}
