package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseCategoryDto;
import net.thumtack.onlineshop.entities.Category;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CategoriesServiceImplTest {
    private CategoriesServiceImpl underTest;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CookieDataRepository cookieDataRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private HttpSession httpSession;
    @Mock
    private HttpServletRequest httpReq;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Validator validator = new Validator(administratorRepository, cookieDataRepository, clientRepository, categoryRepository);
        underTest = new CategoriesServiceImpl(categoryRepository, validator);
    }

    @Test
    public void addCat() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.addCat(httpReq, request);
        assertEquals(response.getId(), savedCategory.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCat2() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.addCat(httpReq, request);
        assertEquals(response.getId(), savedCategory.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCat3() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.addCat(httpReq, request);
        assertEquals(response.getId(), savedCategory.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCat4() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryRepository.existsByName(request.getName())).thenReturn(true);
        ResponseCategoryDto response = underTest.addCat(httpReq, request);
        assertEquals(response.getId(), savedCategory.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCat5() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("", 0);
        Category category = new Category(request.getName(), request.getParentId());
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.addCat(httpReq, request);
        assertEquals(response.getId(), savedCategory.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCat6() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto(null, 0);
        Category category = new Category(request.getName(), request.getParentId());
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.addCat(httpReq, request);
        assertEquals(response.getId(), savedCategory.getId());
    }

    @Test
    public void updateCat() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCat2() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCat3() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCat4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("client", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCat5() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 0);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCat6() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("", 0);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCat7() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto(null, null);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCat8() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        Integer id = 7;
        cookies[0] = new Cookie("admin", "testToken");
        RequestCategoryDto request = new RequestCategoryDto("products", 2);
        Category category = new Category(request.getName(), request.getParentId());
        category.setId(7);
        Category savedCategory = new Category(request.getName(), request.getParentId());
        savedCategory.setId(7);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        ResponseCategoryDto response = underTest.updateCat(httpReq, request, id);
        assertEquals(response.getId(), savedCategory.getId());
        assertEquals(response.getName(), savedCategory.getName());
    }

    @Test
    public void deleteCat() {
    }
}