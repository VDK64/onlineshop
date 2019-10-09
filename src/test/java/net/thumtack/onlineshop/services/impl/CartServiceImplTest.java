package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CartRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartDto;
import net.thumtack.onlineshop.entities.Cart;
import net.thumtack.onlineshop.entities.Client;
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
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class CartServiceImplTest {
    private CartServiceImpl underTest;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private CookieDataRepository cookieDataRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private HttpServletRequest httpReq;
    @Mock
    private HttpSession httpSession;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Validator validator = new Validator(administratorRepository, cookieDataRepository, clientRepository, categoryRepository);
        underTest = new CartServiceImpl(cartRepository, validator, productRepository, clientRepository, clientService);
    }

    @Test
    public void addCartProduct() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        ResponseCartDto response = underTest.addCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCartProduct2() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.findByClientCartAndProductCart(client, product)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        ResponseCartDto response = underTest.addCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCartProduct3() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        ResponseCartDto response = underTest.addCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCartProduct4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        ResponseCartDto response = underTest.addCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addCartProduct5() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        ResponseCartDto response = underTest.addCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test
    public void deleteCartProduct() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        Client client = new Client("Алексей", "Петрович", "Сидоров",
                "sidor@mail.ru", "Saratov", "+79815350114","admin41", "Passwordl123as6d54");
        client.setId(1);
        Integer id = 1;
        Product product = new Product(300, "Sok", 25);
        product.setId(1);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(httpReq.getCookies()).thenReturn(cookies);
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(httpReq.getSession()).thenReturn(httpSession);
        underTest.deleteCartProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteCartProduct2() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        Client client = new Client("Алексей", "Петрович", "Сидоров",
                "sidor@mail.ru", "Saratov", "+79815350114","admin41", "Passwordl123as6d54");
        client.setId(1);
        Integer id = 1;
        Product product = new Product(300, "Sok", 25);
        product.setId(1);
        when(httpReq.getCookies()).thenReturn(cookies);
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(Optional.of(cookieData));
        when(httpReq.getSession()).thenReturn(httpSession);
        underTest.deleteCartProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteCartProduct3() throws ServerExceptions {
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        Client client = new Client("Алексей", "Петрович", "Сидоров",
                "sidor@mail.ru", "Saratov", "+79815350114","admin41", "Passwordl123as6d54");
        client.setId(1);
        Integer id = 1;
        Product product = new Product(300, "Sok", 25);
        product.setId(1);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(httpReq.getCookies()).thenReturn(cookies);
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(httpReq.getSession()).thenReturn(httpSession);
        underTest.deleteCartProduct(httpReq, id);
    }

    @Test(expected = ServerExceptions.class)
    public void deleteCartProduct4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = null;
        String login = "admin41";
        Client client = new Client("Алексей", "Петрович", "Сидоров",
                "sidor@mail.ru", "Saratov", "+79815350114","admin41", "Passwordl123as6d54");
        client.setId(1);
        Integer id = 1;
        Product product = new Product(300, "Sok", 25);
        product.setId(1);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(httpReq.getCookies()).thenReturn(cookies);
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(httpReq.getSession()).thenReturn(httpSession);
        underTest.deleteCartProduct(httpReq, id);
    }

    @Test
    public void updateCartProduct() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(httpReq.getSession()).thenReturn(httpSession);
        when(cartRepository.findByClientCartAndProductCart(client, product)).thenReturn(Optional.of(cart));
        ResponseCartDto response = underTest.updateCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCartProduct2() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(httpReq.getSession()).thenReturn(httpSession);
        when(cartRepository.findByClientCartAndProductCart(client, product)).thenReturn(Optional.of(cart));
        ResponseCartDto response = underTest.updateCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCartProduct3() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(httpReq.getSession()).thenReturn(httpSession);
        when(cartRepository.findByClientCartAndProductCart(client, product)).thenReturn(Optional.of(cart));
        ResponseCartDto response = underTest.updateCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCartProduct4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Cart cart = new Cart(client, null, 10);
        cart.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(httpReq.getSession()).thenReturn(httpSession);
        ResponseCartDto response = underTest.updateCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void updateCartProduct5() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestProductBuyDto request = new RequestProductBuyDto(1, "Sok", 300, 10);
        Client client = new Client("Алексей", "Петрович", "Сидоров", "sidor@mail.ru", "Saratov", "+79815350114",
                "admin41", "Passwordl123as6d54");
        client.setId(1);
        Product product = new Product(300, "Sok", 25);
        Cart cart = new Cart(client, product, 10);
        cart.setId(1);
        product.setId(1);
        Set<Cart> carts = new HashSet<>();
        carts.add(cart);
        client.setCarts(carts);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(productRepository.findById(request.getId())).thenReturn(Optional.of(product));
        doReturn(login).when(clientService).giveClientLoginByCookie(cookies);
        when(clientRepository.findByLogin(login)).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(httpReq.getSession()).thenReturn(httpSession);
        ResponseCartDto response = underTest.updateCartProduct(httpReq, request);
        assertEquals(response.getCart().get(0).getCount(), request.getCount());
        assertEquals(response.getCart().get(0).getPrice(), request.getPrice());
        assertEquals(response.getCart().get(0).getId(), request.getId());
    }

}