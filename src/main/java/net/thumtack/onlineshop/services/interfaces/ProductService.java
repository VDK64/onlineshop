package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductInfoDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface ProductService {

    ResponseProductDto addProduct(HttpServletRequest httpReq, RequestProductDto requestProductDto) throws ServerExceptions;
    ResponseProductDto updateProduct(HttpServletRequest httpReq, Integer id, RequestProductDto requestProductDto) throws ServerExceptions;
    void deleteProduct(HttpServletRequest httpReq, Integer id) throws ServerExceptions;
    ResponseProductInfoDto infoProduct(HttpServletRequest httpReq, Integer id) throws ServerExceptions;
    Set productsSet(List<Integer> categories) throws ServerExceptions;
    List<ResponseProductInfoDto> productsList(List<Integer> categories) throws ServerExceptions;
}
