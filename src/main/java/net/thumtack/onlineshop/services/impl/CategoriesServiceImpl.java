package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dto.Request.RequestCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseCategoryDto;
import net.thumtack.onlineshop.entities.Category;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.CategoriesService;
import net.thumtack.onlineshop.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    private CategoryRepository categoryRepository;
    private Validator validator;

    @Autowired
    CategoriesServiceImpl(CategoryRepository categoryRepository, Validator validator) {
        this.categoryRepository = categoryRepository;
        this.validator = validator;
    }

    @Override
    public ResponseCategoryDto addCat(HttpServletRequest httpReq,
                                      RequestCategoryDto requestCategoryDto) {
        validator.isCookieNullAdmin(httpReq);
        Optional<Category> optCat;
        ResponseCategoryDto respCat = new ResponseCategoryDto();
        if (requestCategoryDto.getParentId() == null) {
            requestCategoryDto.setParentId(0);
        }
        if (requestCategoryDto.getParentId() != 0) {
            optCat = categoryRepository.findById(requestCategoryDto.getParentId());
            if (!optCat.isPresent()) {
               throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT,
                       "id", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage())));
            }
            respCat.setParentId(requestCategoryDto.getParentId());
            respCat.setParentName(optCat.get().getName());
        }
        if (requestCategoryDto.getName() == null || requestCategoryDto.getName().equalsIgnoreCase("")) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_NAME_NULL,
                    "name", ServerErrors.WRONG_CATEGORY_NAME_NULL.getErrorMessage())));
        }
        if (categoryRepository.existsByName(requestCategoryDto.getName())) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WORNG_CATEGORY_EXIST, "name",
                    ServerErrors.WORNG_CATEGORY_EXIST.getErrorMessage())));
        }
        Category category = new Category(requestCategoryDto.getName(), requestCategoryDto.getParentId());
        Category savedCat = categoryRepository.save(category);
        respCat.setId(savedCat.getId());
        respCat.setName(category.getName());
        return respCat;
    }

    @Override
    public ResponseCategoryDto getCat(HttpServletRequest httpReq, Integer id) {
        validator.isCookieNullAdmin(httpReq);
        ResponseCategoryDto respCat = new ResponseCategoryDto();
        Optional<Category> category = categoryRepository.findById(id);
        Optional<Category> parentCat = Optional.empty();
        if (!category.isPresent())
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT,
                    "parent_id", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage())));
        if (category.get().getParentId() != 0)
            if (category.get().getParentId() != null) {
                parentCat = categoryRepository.findById(category.get().getParentId());
                if (!parentCat.isPresent())
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT,
                            "parent_id", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage())));
            }
        respCat.setId(category.get().getId());
        respCat.setName(category.get().getName());
        if (parentCat.isPresent()) {
            respCat.setParentName(parentCat.get().getName());
            respCat.setParentId(parentCat.get().getId());
        } else {
            respCat.setParentName(null);
            respCat.setParentId(0);
        }
        return respCat;
    }

    @Override
    public ResponseCategoryDto updateCat(HttpServletRequest httpReq, RequestCategoryDto requestCategoryDto, Integer id) {
        validator.isCookieNullAdmin(httpReq);
        Optional<Category> optCat = categoryRepository.findById(id);
        Category savedCat;
        Optional<Category> parentCat = Optional.empty();
        if (!optCat.isPresent()) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT,
                    "parentId", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage())));
        }
        Category category = optCat.get();
        validator.validateCategories(category, requestCategoryDto);
        parentCat = validator.getParentCat(requestCategoryDto, category);
        if (requestCategoryDto.getName() != null) {
            if (!requestCategoryDto.getName().equalsIgnoreCase(""))
                category.setName(requestCategoryDto.getName());
        }
        savedCat = categoryRepository.save(category);
        ResponseCategoryDto respCatDto = new ResponseCategoryDto();
        respCatDto.setId(savedCat.getId());
        respCatDto.setParentId(savedCat.getParentId());
        respCatDto.setName(savedCat.getName());
        if (parentCat.isPresent())
            respCatDto.setParentName(parentCat.get().getName());
        else respCatDto.setParentName(null);
        return respCatDto;
    }

    @Override
    @Transactional
    public void deleteCat(HttpServletRequest httpReq, Integer id) {
        validator.isCookieNullAdmin(httpReq);
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT,
                    "id", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage())));
        }
        List<Product> products = category.get().getProducts();
        for (Product prod : products){
            prod.getCategories().remove(category.get());
        }
        Iterable<Category> subCat = categoryRepository.findByParentId(id);
        for (Category cat : subCat) {
            for (Product product : cat.getProducts()) {
                product.getCategories().remove(cat);
            }
        }
        categoryRepository.deleteByParentId(category.get().getId());
        categoryRepository.deleteById(id);
    }

    @Override
    public List<ResponseCategoryDto> getAllCat(HttpServletRequest httpReq) {
        validator.isCookieNullAdmin(httpReq);
        List<ResponseCategoryDto> response = new ArrayList<>();
        Sort castSort = new Sort(Sort.Direction.ASC, "name");
        List<Category> categories = (List<Category>) categoryRepository.findByParentId(0, castSort);
        for (Category category : categories){
            response.add(new ResponseCategoryDto(category.getId(), category.getName(), null, null));
            Iterable<Category> subCategories = categoryRepository.findByParentId(category.getId(), castSort);
            for (Category subCat : subCategories){
                response.add(new ResponseCategoryDto(subCat.getId(), subCat.getName(),
                        subCat.getParentId(), category.getName()));
            }
        }
        return response;
    }
}
