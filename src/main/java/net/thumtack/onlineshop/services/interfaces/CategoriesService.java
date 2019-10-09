package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseCategoryDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CategoriesService {

    ResponseCategoryDto addCat(HttpServletRequest httpReq, RequestCategoryDto requestCategoryDto) throws ServerExceptions;
    ResponseCategoryDto getCat(HttpServletRequest httpReq, Integer id) throws ServerExceptions;
    ResponseCategoryDto updateCat(HttpServletRequest httpReq, RequestCategoryDto requestCategoryDto, Integer id) throws ServerExceptions;
    void deleteCat(HttpServletRequest httpReq, Integer id) throws ServerExceptions;
    List<ResponseCategoryDto> getAllCat(HttpServletRequest httpReq) throws ServerExceptions;
}
