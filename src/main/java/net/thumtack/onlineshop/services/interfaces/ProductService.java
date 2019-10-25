package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductInfoDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface ProductService {

    ResponseProductDto addProduct(HttpServletRequest httpReq, RequestProductDto requestProductDto);
    ResponseProductDto updateProduct(HttpServletRequest httpReq, Integer id, RequestProductDto requestProductDto);
    void deleteProduct(HttpServletRequest httpReq, Integer id);
    ResponseProductInfoDto infoProduct(HttpServletRequest httpReq, Integer id);
    Set productsSet(List<Integer> categories);
    List<ResponseProductInfoDto> productsList(List<Integer> categories);
}
