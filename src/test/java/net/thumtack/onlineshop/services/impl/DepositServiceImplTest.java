package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestDepositDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DepositServiceImplTest {
    private DepositServiceImpl underTest;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CookieDataRepository cookieDataRepository;
    @Mock
    private HttpServletRequest httpReq;
    @Mock
    private ClientServiceImpl clientService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Validator validator = new Validator(administratorRepository, cookieDataRepository, clientRepository, categoryRepository);
        underTest = new DepositServiceImpl(validator, clientRepository, clientService);
    }

    @Test
    public void addDeposit() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        RequestDepositDto request = new RequestDepositDto(10000);
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.addDeposit(httpReq, request);
        assertEquals(response.getDeposit(), request.getDeposit());
        assertEquals(response.getId(), client.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addDeposit2() throws ServerExceptions {
        CookieData cookieData = new CookieData("a", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("a", "testToken");
        RequestDepositDto request = new RequestDepositDto(10000);
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.addDeposit(httpReq, request);
        assertEquals(response.getDeposit(), request.getDeposit());
        assertEquals(response.getId(), client.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addDeposit3() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        RequestDepositDto request = new RequestDepositDto(-1000);
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.addDeposit(httpReq, request);
        assertEquals(response.getDeposit(), request.getDeposit());
        assertEquals(response.getId(), client.getId());
    }

    @Test(expected = ServerExceptions.class)
    public void addDeposit4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        RequestDepositDto request = new RequestDepositDto(10000);
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.addDeposit(httpReq, request);
        assertEquals(response.getDeposit(), request.getDeposit());
        assertEquals(response.getId(), client.getId());
    }


    @Test(expected = ServerExceptions.class)
    public void addDeposit5() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        RequestDepositDto request = new RequestDepositDto(0);
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.addDeposit(httpReq, request);
        assertEquals(response.getDeposit(), request.getDeposit());
        assertEquals(response.getId(), client.getId());
    }

    @Test
    public void withdraw() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        client.setDeposit(10000);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.withdraw(httpReq);
        int depo = response.getDeposit();
        assertEquals(0, depo);
    }

    @Test(expected = ServerExceptions.class)
    public void withdraw2() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        client.setDeposit(0);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.withdraw(httpReq);
        int depo = response.getDeposit();
        assertEquals(0, depo);
    }

    @Test(expected = ServerExceptions.class)
    public void withdraw3() throws ServerExceptions {
        CookieData cookieData = new CookieData("j", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("j", "testToken");
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        client.setDeposit(10000);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.withdraw(httpReq);
        int depo = response.getDeposit();
        assertEquals(0, depo);
    }

    @Test(expected = ServerExceptions.class)
    public void withdraw4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        client.setDeposit(10000);
        when(httpReq.getCookies()).thenReturn(cookies);
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.withdraw(httpReq);
        int depo = response.getDeposit();
        assertEquals(0, depo);
    }

    @Test(expected = ServerExceptions.class)
    public void withdraw5() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "client45");
        Cookie[] cookies = new Cookie[1];
        String login = "client45";
        cookies[0] = new Cookie("client", "testToken");
        Client client = new Client("Иван", "Иванович", "Иванов", "ivan@mail.ru",
                "Saratov", "+79173255145","client45", "Password164asdasd");
        client.setId(6);
        client.setDeposit(10000);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientService.giveClientLoginByCookie(httpReq.getCookies())).thenReturn(login);
        when(clientRepository.findByLogin(login)).thenReturn(java.util.Optional.of(client));
        when((clientRepository.save(client))).thenReturn(client);
        ResponseClientDto response = underTest.withdraw(httpReq);
        int depo = response.getDeposit();
        assertEquals(0, depo);
    }
}