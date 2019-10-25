package net.thumtack.onlineshop.services.interfaces;

import net.thumtack.onlineshop.dto.Request.RequestCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseCategoryDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CategoriesService {

    ResponseCategoryDto addCat(HttpServletRequest httpReq, RequestCategoryDto requestCategoryDto);
    ResponseCategoryDto getCat(HttpServletRequest httpReq, Integer id);
    ResponseCategoryDto updateCat(HttpServletRequest httpReq, RequestCategoryDto requestCategoryDto, Integer id);
    void deleteCat(HttpServletRequest httpReq, Integer id);
    List<ResponseCategoryDto> getAllCat(HttpServletRequest httpReq);
}
