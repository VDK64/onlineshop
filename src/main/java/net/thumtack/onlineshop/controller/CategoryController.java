package net.thumtack.onlineshop.controller;

import net.thumtack.onlineshop.dto.Request.RequestCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseEmptyDto;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/")
public class CategoryController {

    private CategoriesService categoriesService;

    @Autowired
    public CategoryController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @PostMapping("categories")
    public ResponseEntity addCat(HttpServletRequest httpReq, @RequestBody RequestCategoryDto requestCategoryDto) throws ServerExceptions {
        ResponseCategoryDto responseCat;
        responseCat = categoriesService.addCat(httpReq, requestCategoryDto);
        return ResponseEntity.ok(responseCat);
    }

    @GetMapping("categories/{id}")
    public ResponseEntity getCat(HttpServletRequest httpReq, @PathVariable Integer id) throws ServerExceptions {
        ResponseCategoryDto responseCat;
        responseCat = categoriesService.getCat(httpReq, id);
        return ResponseEntity.ok(responseCat);
    }

    @PutMapping("categories/{id}")
    public ResponseEntity updateCat(HttpServletRequest httpReq, @PathVariable Integer id,
                                                         @RequestBody RequestCategoryDto requestCategoryDto) throws ServerExceptions {
        return ResponseEntity.ok(categoriesService.updateCat(httpReq, requestCategoryDto, id));

    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity deleteCat(HttpServletRequest httpReq, @PathVariable Integer id) throws ServerExceptions {
        categoriesService.deleteCat(httpReq, id);
        return ResponseEntity.ok(ResponseEmptyDto.voidResponse);
    }

    @GetMapping("categories")
    public ResponseEntity getAllCat(HttpServletRequest httpReq) throws ServerExceptions {
        return ResponseEntity.ok(categoriesService.getAllCat(httpReq));

    }
}
