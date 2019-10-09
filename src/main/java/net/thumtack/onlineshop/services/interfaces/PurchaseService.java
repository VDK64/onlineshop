package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductBuyDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PurchaseService {
    ResponseProductBuyDto buyProduct(HttpServletRequest httpReq, RequestProductBuyDto request) throws ServerExceptions;
    ResponseCartBuyDto buyCartProducts(HttpServletRequest httpReq, List<RequestProductBuyDto> buyReq) throws ServerExceptions;
    List commonTableInfo(HttpServletRequest httpReq, String command, List<Integer> value, Integer offset,
                         Integer limit) throws ServerExceptions;
}
