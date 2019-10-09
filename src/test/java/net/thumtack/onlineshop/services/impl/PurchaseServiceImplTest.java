package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CartRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import net.thumtack.onlineshop.dao.PurchaseRepository;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductBuyDto;
import net.thumtack.onlineshop.entities.Cart;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.services.interfaces.ClientService;
import net.thumtack.onlineshop.services.interfaces.ProductService;
import net.thumtack.onlineshop.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PurchaseServiceImplTest {
    private PurchaseServiceImpl underTest;
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
    @Mock
    private CartRepository cartRepository;
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private ProductService productService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Validator validator = new Validator(administratorRepository, cookieDataRepository, clientRepository, categoryRepository);
        underTest = new PurchaseServiceImpl(clientRepository, validator, clientService, productRepository, cartRepository,
                purchaseRepository, productService, categoryRepository);
    }

    @Test
    public void buyProduct() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Bulavka", 50, 10);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(1);
        client.setDeposit(500);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        Product product = new Product(50, "Bulavka", 50);
        product.setId(1);
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        savedProduct.setCount(product.getCount() - request.getCount());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        when(clientRepository.save(savedClient)).thenReturn(savedClient);
        ResponseProductBuyDto response = underTest.buyProduct(httpReq, request);
        assertEquals((Integer) 0, savedClient.getDeposit());
        assertEquals((Integer) 40, savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyProduct2() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Bulavka", 50, 10);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(1);
        client.setDeposit(500);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        Product product = new Product(50, "Bulavka", 50);
        product.setId(1);
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        savedProduct.setCount(product.getCount() - request.getCount());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        when(clientRepository.save(savedClient)).thenReturn(savedClient);
        ResponseProductBuyDto response = underTest.buyProduct(httpReq, request);
        assertEquals((Integer) 0, savedClient.getDeposit());
        assertEquals((Integer) 40, savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyProduct3() throws ServerExceptions {
        CookieData cookieData = new CookieData("j", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("j", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Bulavka", 50, 10);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(1);
        client.setDeposit(500);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        Product product = new Product(50, "Bulavka", 50);
        product.setId(1);
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        savedProduct.setCount(product.getCount() - request.getCount());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        when(clientRepository.save(savedClient)).thenReturn(savedClient);
        ResponseProductBuyDto response = underTest.buyProduct(httpReq, request);
        assertEquals((Integer) 0, savedClient.getDeposit());
        assertEquals((Integer) 40, savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyProduct4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Bulavka", 50, 10);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(1);
        client.setDeposit(500);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        Product product = new Product(50, "Bulavka", 50);
        product.setId(1);
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        savedProduct.setCount(product.getCount() - request.getCount());
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        when(clientRepository.save(savedClient)).thenReturn(savedClient);
        ResponseProductBuyDto response = underTest.buyProduct(httpReq, request);
        assertEquals((Integer) 0, savedClient.getDeposit());
        assertEquals((Integer) 40, savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyProduct5() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Bulavka", 50, 10);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(1);
        client.setDeposit(500);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        Product product = new Product(50, "Bulavka", 50);
        product.setId(1);
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        savedProduct.setCount(product.getCount() - request.getCount());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        when(clientRepository.save(savedClient)).thenReturn(savedClient);
        ResponseProductBuyDto response = underTest.buyProduct(httpReq, request);
        assertEquals((Integer) 0, savedClient.getDeposit());
        assertEquals((Integer) 40, savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyProduct6() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Bulavka", -50, 10);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(1);
        client.setDeposit(500);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        Product product = new Product(50, "Bulavka", 50);
        product.setId(1);
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        savedProduct.setCount(product.getCount() - request.getCount());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        when(clientRepository.save(savedClient)).thenReturn(savedClient);
        ResponseProductBuyDto response = underTest.buyProduct(httpReq, request);
        assertEquals((Integer) 0, savedClient.getDeposit());
        assertEquals((Integer) 40, savedProduct.getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyProduct7() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Bulavka", 50, 10);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(1);
        client.setDeposit(400);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(client.getDeposit() - request.getCount() * request.getPrice());
        Product product = new Product(50, "Bulavka", 50);
        product.setId(1);
        Product savedProduct = new Product(product.getPrice(), product.getName(), product.getCount());
        savedProduct.setId(1);
        savedProduct.setCount(product.getCount() - request.getCount());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.save(savedProduct)).thenReturn(savedProduct);
        when(clientRepository.save(savedClient)).thenReturn(savedClient);
        ResponseProductBuyDto response = underTest.buyProduct(httpReq, request);
        assertEquals((Integer) 0, savedClient.getDeposit());
        assertEquals((Integer) 40, savedProduct.getCount());
    }



    @Test
    public void buyCartProducts() throws ServerExceptions {
        int i = 1;
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        List<RequestProductBuyDto> request = new ArrayList<>(Arrays.asList(
                new RequestProductBuyDto(1, "Bulavka", 50, 10),
                new RequestProductBuyDto(2, "nitka", 150, 5),
                new RequestProductBuyDto(3, "igolka", 200, 10)
        ));
        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product(50, "Bulavka", 30),
                new Product(150, "nitka", 20),
                new Product(200, "igolka", 20)
        ));
        for (Product product : products){
            product.setId(i);
            ++i;
        }
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        Cart cart1 = new Cart(client, products.get(0), 10);
        cart1.setId(1);
        Cart cart2 = new Cart(client, products.get(1), 3);
        cart2.setId(2);
        Cart cart3 = new Cart(client, products.get(2), 15);
        cart3.setId(3);
        Set<Cart> cartSet = new TreeSet<>(Comparator.comparing(Cart::getId));
        cartSet.add(cart1);
        cartSet.add(cart2);
        cartSet.add(cart3);
        i = 1;
        for (Cart cart : cartSet){
            cart.setId(i);
            ++i;
        }
        client.setId(1);
        client.setDeposit(50000);
        client.setCarts(cartSet);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(savedClient.getDeposit() - 400);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.save(products.get(2))).thenReturn(products.get(2));
        ResponseCartBuyDto response = underTest.buyCartProducts(httpReq, request);
        assertTrue(response.getRemaining().size()!=0);
        assertEquals(5, (int) response.getRemaining().get(0).getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyCartProducts2() throws ServerExceptions {
        int i = 1;
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("admin", "testToken");
        List<RequestProductBuyDto> request = new ArrayList<>(Arrays.asList(
                new RequestProductBuyDto(1, "Bulavka", 50, 10),
                new RequestProductBuyDto(2, "nitka", 150, 5),
                new RequestProductBuyDto(3, "igolka", 200, 10)
        ));
        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product(50, "Bulavka", 30),
                new Product(150, "nitka", 20),
                new Product(200, "igolka", 20)
        ));
        for (Product product : products){
            product.setId(i);
            ++i;
        }
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        Cart cart1 = new Cart(client, products.get(0), 10);
        cart1.setId(1);
        Cart cart2 = new Cart(client, products.get(1), 3);
        cart2.setId(2);
        Cart cart3 = new Cart(client, products.get(2), 15);
        cart3.setId(3);
        Set<Cart> cartSet = new TreeSet<>(Comparator.comparing(Cart::getId));
        cartSet.add(cart1);
        cartSet.add(cart2);
        cartSet.add(cart3);
        i = 1;
        for (Cart cart : cartSet){
            cart.setId(i);
            ++i;
        }
        client.setId(1);
        client.setDeposit(50000);
        client.setCarts(cartSet);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(savedClient.getDeposit() - 400);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.save(products.get(2))).thenReturn(products.get(2));
        ResponseCartBuyDto response = underTest.buyCartProducts(httpReq, request);
        assertTrue(response.getRemaining().size()!=0);
        assertEquals(5, (int) response.getRemaining().get(0).getCount());
    }

    @Test(expected = ServerExceptions.class)
    public void buyCartProducts3() throws ServerExceptions {
        int i = 1;
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        List<RequestProductBuyDto> request = new ArrayList<>(Arrays.asList(
                new RequestProductBuyDto(1, "Bulavka", 50, 10),
                new RequestProductBuyDto(2, "nitka", 150, 5),
                new RequestProductBuyDto(3, "igolka", 200, 10)
        ));
        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product(50, "Bulavka", 30),
                new Product(150, "nitka", 20),
                new Product(200, "igolka", 20)
        ));
        for (Product product : products){
            product.setId(i);
            ++i;
        }
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        Cart cart1 = new Cart(client, products.get(0), 10);
        cart1.setId(1);
        Cart cart2 = new Cart(client, products.get(1), 3);
        cart2.setId(2);
        Cart cart3 = new Cart(client, products.get(2), 15);
        cart3.setId(3);
        Set<Cart> cartSet = new TreeSet<>(Comparator.comparing(Cart::getId));
        cartSet.add(cart1);
        cartSet.add(cart2);
        cartSet.add(cart3);
        i = 1;
        for (Cart cart : cartSet){
            cart.setId(i);
            ++i;
        }
        client.setId(1);
        client.setDeposit(500);
        client.setCarts(cartSet);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(savedClient.getDeposit() - 400);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.save(products.get(2))).thenReturn(products.get(2));
        ResponseCartBuyDto response = underTest.buyCartProducts(httpReq, request);
        assertTrue(response.getRemaining().size()!=0);
        assertEquals(5, (int) response.getRemaining().get(0).getCount());
    }

    @Test
    public void buyCartProducts4() throws ServerExceptions {
        int i = 1;
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        String login = "admin41";
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        List<RequestProductBuyDto> request = new ArrayList<>(Arrays.asList(
                new RequestProductBuyDto(1, "Bulavka", 50, 10),
                new RequestProductBuyDto(2, "nitka", 150, 5),
                new RequestProductBuyDto(3, "igolka", 200, 10)
        ));
        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product(50, "Bulavka", 1),
                new Product(150, "nitka", 20),
                new Product(200, "igolka", 20)
        ));
        for (Product product : products){
            product.setId(i);
            ++i;
        }
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        Cart cart1 = new Cart(client, products.get(0), 10);
        cart1.setId(1);
        Cart cart2 = new Cart(client, products.get(1), 3);
        cart2.setId(2);
        Cart cart3 = new Cart(client, products.get(2), 15);
        cart3.setId(3);
        Set<Cart> cartSet = new TreeSet<>(Comparator.comparing(Cart::getId));
        cartSet.add(cart1);
        cartSet.add(cart2);
        cartSet.add(cart3);
        i = 1;
        for (Cart cart : cartSet){
            cart.setId(i);
            ++i;
        }
        client.setId(1);
        client.setDeposit(50000);
        client.setCarts(cartSet);
        Client savedClient = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        savedClient.setId(1);
        savedClient.setDeposit(savedClient.getDeposit() - 400);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when(productRepository.save(products.get(2))).thenReturn(products.get(2));
        ResponseCartBuyDto response = underTest.buyCartProducts(httpReq, request);
        assertTrue(response.getRemaining().size() == 2);
        assertEquals(5, (int) response.getRemaining().get(1).getCount());
        assertEquals(products.get(0).getName(), response.getRemaining().get(0).getName());
    }
}