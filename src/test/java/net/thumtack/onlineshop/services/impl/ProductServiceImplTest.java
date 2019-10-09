package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import net.thumtack.onlineshop.dto.Request.RequestProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductDto;
import net.thumtack.onlineshop.entities.Category;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import net.thumtack.onlineshop.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
    private ProductServiceImpl underTest;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private CookieDataRepository cookieDataRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private HttpServletRequest httpReq;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Validator validator = new Validator(administratorRepository, cookieDataRepository, clientRepository, categoryRepository);
        underTest = new ProductServiceImpl(productRepository, validator, categoryRepository);
    }

    @Test
    public void addProduct() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("Bulavka", 100, 1000,
                Collections.singletonList(category.getId()));
        Product product = new Product(request.getPrice(), request.getName(), request.getCount());
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        product.setCategories(Collections.singletonList(category));
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(java.util.Optional.of(category));
        when(productRepository.save(product)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.addProduct(httpReq, request);
        assertNotNull(response.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addProduct2() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("Bulavka", 100, 1000,
                Collections.singletonList(category.getId()));
        Product product = new Product(request.getPrice(), request.getName(), request.getCount());
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        product.setCategories(Collections.singletonList(category));
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(java.util.Optional.of(category));
        when(productRepository.save(product)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.addProduct(httpReq, request);
        assertNotNull(response.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addProduct3() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("Bulavka", 100, 1000,
                Collections.singletonList(category.getId()));
        Product product = new Product(request.getPrice(), request.getName(), request.getCount());
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        product.setCategories(Collections.singletonList(category));
        savedProduct.setCategories(product.getCategories());
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(java.util.Optional.of(category));
        when(productRepository.save(product)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.addProduct(httpReq, request);
        assertNotNull(response.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addProduct4() throws ServerExceptions {
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("Bulavka", 100, 1000,
                Collections.singletonList(category.getId()));
        Product product = new Product(request.getPrice(), request.getName(), request.getCount());
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        product.setCategories(Collections.singletonList(category));
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(java.util.Optional.of(category));
        when(productRepository.save(product)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.addProduct(httpReq, request);
        assertNotNull(response.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addProduct5() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("Bulavka", 100, 1000,
                Collections.singletonList(category.getId()));
        Product product = new Product(request.getPrice(), request.getName(), request.getCount());
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        product.setCategories(Collections.singletonList(category));
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(productRepository.save(product)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.addProduct(httpReq, request);
        assertNotNull(response.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addProduct6() throws ServerExceptions {
        CookieData cookieData = new CookieData("g", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("g", "testToken");
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("Bulavka", 100, 1000,
                Collections.singletonList(category.getId()));
        Product product = new Product(request.getPrice(), request.getName(), request.getCount());
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        product.setCategories(Collections.singletonList(category));
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(java.util.Optional.of(category));
        when(productRepository.save(product)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.addProduct(httpReq, request);
        assertNotNull(response.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addProduct7() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("Bulavka", -100, 1000,
                Collections.singletonList(category.getId()));
        Product product = new Product(request.getPrice(), request.getName(), request.getCount());
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        product.setCategories(Collections.singletonList(category));
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(java.util.Optional.of(category));
        when(productRepository.save(product)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.addProduct(httpReq, request);
        assertNotNull(response.getId());
    }

    @Test
    public void updateProduct() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("apple", 200, 111,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(request.getPrice(), request.getName(), request.getCount());
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void updateProduct2() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("apple", 200, 111,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(request.getPrice(), request.getName(), request.getCount());
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void updateProduct3() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("apple", 200, 111,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(request.getPrice(), request.getName(), request.getCount());
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void updateProduct5() throws ServerExceptions {
        CookieData cookieData = new CookieData("k", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("k", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("apple", 200, 111,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(request.getPrice(), request.getName(), request.getCount());
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void updateProduct6() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("apple", 200, 111,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(request.getPrice(), request.getName(), request.getCount());
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test
    public void updateProduct7() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto(null, 200, 111,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(request.getPrice(), "Bulavka", request.getCount());
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test
    public void updateProduct8() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("apple", null, 111,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(100, request.getName(), request.getCount());
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test
    public void updateProduct9() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto("apple", 200, null,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(request.getPrice(), request.getName(), 1000);
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test
    public void updateProduct10() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        RequestProductDto request = new RequestProductDto(null, null, null,
                Collections.singletonList(category.getId()));
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        Product savedProduct = new Product(100, "Bulavka",1000);
        savedProduct.setId(1);
        savedProduct.setCategories(product.getCategories());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(categoryRepository.findById(request.getCategories().get(0))).thenReturn(Optional.of(category));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        ResponseProductDto response = underTest.updateProduct(httpReq, id, request);
        assertEquals(response.getId(), savedProduct.getId());
        assertEquals(response.getName(), savedProduct.getName());
        assertEquals(response.getPrice(), savedProduct.getPrice());
        assertEquals(response.getCount(), savedProduct.getCount());
    }

    @Test
    public void deleteProduct() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(id);
        underTest.deleteProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteProduct2() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(id);
        underTest.deleteProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteProduct3() throws ServerExceptions {
        CookieData cookieData = new CookieData("k", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("k", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(id);
        underTest.deleteProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteProduct4() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(id);
        underTest.deleteProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteProduct5() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        when(httpReq.getCookies()).thenReturn(cookies);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(id);
        underTest.deleteProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteProduct6() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        Integer id = 1;
        Category category = new Category("products", 0);
        category.setId(1);
        Product product = new Product(100, "Bulavka",1000);
        product.setId(1);
        product.setCategories(Collections.singletonList(category));
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        doNothing().when(productRepository).deleteById(id);
        underTest.deleteProduct(httpReq, id);
    }
}