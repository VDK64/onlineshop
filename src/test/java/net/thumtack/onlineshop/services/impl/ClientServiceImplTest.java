package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.config.FileConfig;
import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestChangePassClientDto;
import net.thumtack.onlineshop.dto.Request.RequestClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientChangePassDto;
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

public class ClientServiceImplTest {
    private ClientServiceImpl underTest;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private CookieDataRepository cookieDataRepository;
    @Mock
    private HttpServletRequest httpReq;
    private Validator validator;

    @Before
    public void setUp(){
        FileConfig.maxNameLength = 50;
        FileConfig.minPasswordLength = 8;
        MockitoAnnotations.initMocks(this);
        validator = new Validator(administratorRepository, cookieDataRepository, clientRepository, categoryRepository);
        underTest = new ClientServiceImpl(clientRepository, validator, cookieDataRepository);
    }

    @Test
    public void regUser() throws ServerExceptions {
        RequestClientDto request = new RequestClientDto("Иван", "Оскарович", "Головин",
                "golova@mail.ru","Saratov", "+7-(985)-385-64-31", "VanyaGolova",
                "Passwordwordword1333");
        Client savedClient = request.createClient();
        savedClient.setDeposit(0);
        savedClient.setId(6);
        Client client = request.createClient();
        client.setDeposit(0);
        validator.regCheckClient(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        ResponseClientDto response = underTest.regUser(request);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLogin(), client.getLogin());
    }

    @Test(expected = ServerExceptions.class)
    public void regUser2() throws ServerExceptions {
        RequestClientDto request = new RequestClientDto("Иgван", "Оскарович", "Головин",
                "golova@mail.ru","Saratov", "+7-(985)-385-64-31", "VanyaGolova",
                "Passwordwordword1333");
        Client savedClient = request.createClient();
        savedClient.setDeposit(0);
        savedClient.setId(6);
        Client client = request.createClient();
        client.setDeposit(0);
        validator.regCheckClient(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        ResponseClientDto response = underTest.regUser(request);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLogin(), client.getLogin());
    }

    @Test(expected = ServerExceptions.class)
    public void regUser3() throws ServerExceptions {
        RequestClientDto request = new RequestClientDto("Иван", "Оскghjарович", "Головин",
                "golova@mail.ru","Saratov", "+7-(985)-385-64-31", "VanyaGolova",
                "Passwordwordword1333");
        Client savedClient = request.createClient();
        savedClient.setDeposit(0);
        savedClient.setId(6);
        Client client = request.createClient();
        client.setDeposit(0);
        validator.regCheckClient(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        ResponseClientDto response = underTest.regUser(request);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLogin(), client.getLogin());
    }

    @Test(expected = ServerExceptions.class)
    public void regUser4() throws ServerExceptions {
        RequestClientDto request = new RequestClientDto("Иван", "Оскарович", "Голghjовин",
                "golova@mail.ru","Saratov", "+7-(985)-385-64-31", "VanyaGolova",
                "Passwordwordword1333");
        Client savedClient = request.createClient();
        savedClient.setDeposit(0);
        savedClient.setId(6);
        Client client = request.createClient();
        client.setDeposit(0);
        validator.regCheckClient(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        ResponseClientDto response = underTest.regUser(request);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLogin(), client.getLogin());
    }

    @Test(expected = ServerExceptions.class)
    public void regUser5() throws ServerExceptions {
        RequestClientDto request = new RequestClientDto("Иван", "Оскарович", "Головин",
                "golovaru","Saratov", "+7-(985)-385-64-31", "VanyaGolova",
                "Passwordwordword1333");
        Client savedClient = request.createClient();
        savedClient.setDeposit(0);
        savedClient.setId(6);
        Client client = request.createClient();
        client.setDeposit(0);
        validator.regCheckClient(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        ResponseClientDto response = underTest.regUser(request);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLogin(), client.getLogin());
    }

    @Test(expected = ServerExceptions.class)
    public void regUser6() throws ServerExceptions {
        RequestClientDto request = new RequestClientDto("Иван", "Оскарович", "Головин",
                "golova@mail.ru","Saratov", "+7-(%^&985)-385-64-31", "VanyaGolova",
                "Passwordwordword1333");
        Client savedClient = request.createClient();
        savedClient.setDeposit(0);
        savedClient.setId(6);
        Client client = request.createClient();
        client.setDeposit(0);
        validator.regCheckClient(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        ResponseClientDto response = underTest.regUser(request);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLogin(), client.getLogin());
    }

    @Test(expected = ServerExceptions.class)
    public void regUser7() throws ServerExceptions {
        RequestClientDto request = new RequestClientDto("Иван", "Оскарович", "Головин",
                "golova@mail.ru","Saratov", "+7-(985)-385-64-31", "VanyaGolova",
                "Pa");
        Client savedClient = request.createClient();
        savedClient.setDeposit(0);
        savedClient.setId(6);
        Client client = request.createClient();
        client.setDeposit(0);
        validator.regCheckClient(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        ResponseClientDto response = underTest.regUser(request);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLogin(), client.getLogin());
    }

    @Test
    public void updateUser() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestChangePassClientDto request = new RequestChangePassClientDto("Иван", "Головин",
                "Оскарович","golova@mail.ru","Saratov", "+7-(985)-385-64-31",
                "Passwordwordword1333","NewPassworld1321asd456");
        request.setLogin(login);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(6);
        Client changeClient = new Client(client.getFirstName(), client.getPatronymic(), client.getLastName(), client.getEmail(),
                client.getAddress(), client.getPhone(), client.getLogin(), client.getPassword());
        changeClient.setPassword(request.getNewPassword());
        changeClient.setPassword(request.getNewPassword());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientRepository.findByLogin(request.getLogin())).thenReturn(java.util.Optional.of(client));
        when(clientRepository.save(client)).thenReturn(changeClient);
        ResponseClientChangePassDto response = underTest.updateUser(httpReq, request);
        assertEquals(request.getNewPassword(), changeClient.getPassword());
    }

    @Test(expected = ServerExceptions.class)
    public void updateUser2() throws ServerExceptions {
        CookieData cookieData = new CookieData("admin", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("admin", "testToken");
        RequestChangePassClientDto request = new RequestChangePassClientDto("Иван", "Головин",
                "Оскарович","golova@mail.ru","Saratov", "+7-(985)-385-64-31",
                "Passwordwordword1333","NewPassworld1321asd456");
        request.setLogin(login);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(6);
        Client changeClient = new Client(client.getFirstName(), client.getPatronymic(), client.getLastName(), client.getEmail(),
                client.getAddress(), client.getPhone(), client.getLogin(), client.getPassword());
        changeClient.setPassword(request.getNewPassword());
        changeClient.setPassword(request.getNewPassword());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientRepository.findByLogin(request.getLogin())).thenReturn(java.util.Optional.of(client));
        when(clientRepository.save(client)).thenReturn(changeClient);
        ResponseClientChangePassDto response = underTest.updateUser(httpReq, request);
        assertEquals(request.getNewPassword(), changeClient.getPassword());
    }

    @Test(expected = ServerExceptions.class)
    public void updateUser3() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("client", "testToken");
        String login = "admin41";
        RequestChangePassClientDto request = new RequestChangePassClientDto("Иван", "Головин",
                "Оскарович","golova@mail.ru","Saratov", "+7-(985)-385-64-31",
                "Passwordwordword1333","NewPassworld1321asd456");
        request.setLogin(login);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(6);
        Client changeClient = new Client(client.getFirstName(), client.getPatronymic(), client.getLastName(), client.getEmail(),
                client.getAddress(), client.getPhone(), client.getLogin(), client.getPassword());
        changeClient.setPassword(request.getNewPassword());
        changeClient.setPassword(request.getNewPassword());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(clientRepository.findByLogin(request.getLogin())).thenReturn(java.util.Optional.of(client));
        when(clientRepository.save(client)).thenReturn(changeClient);
        ResponseClientChangePassDto response = underTest.updateUser(httpReq, request);
        assertEquals(request.getNewPassword(), changeClient.getPassword());
    }

    @Test(expected = ServerExceptions.class)
    public void updateUser4() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestChangePassClientDto request = new RequestChangePassClientDto("Иван", "Головин",
                "Оскарович","golova@mail.ru","Saratov", "+7-(985)-385-64-31",
                "Passwordwordword1333","NewPassworld1321asd456");
        request.setLogin(login);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","Passwordwordword1333");
        client.setId(6);
        Client changeClient = new Client(client.getFirstName(), client.getPatronymic(), client.getLastName(), client.getEmail(),
                client.getAddress(), client.getPhone(), client.getLogin(), client.getPassword());
        changeClient.setPassword(request.getNewPassword());
        changeClient.setPassword(request.getNewPassword());
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientRepository.findByLogin(request.getLogin())).thenReturn(java.util.Optional.of(client));
        when(clientRepository.save(client)).thenReturn(changeClient);
        ResponseClientChangePassDto response = underTest.updateUser(httpReq, request);
        assertEquals(request.getNewPassword(), changeClient.getPassword());
    }

    @Test(expected = ServerExceptions.class)
    public void updateUser5() throws ServerExceptions {
        CookieData cookieData = new CookieData("client", "testToken", "admin41");
        Cookie[] cookies = new Cookie[1];
        String login = "admin41";
        cookies[0] = new Cookie("client", "testToken");
        RequestChangePassClientDto request = new RequestChangePassClientDto("Иван", "Головин",
                "Оскарович","golova@mail.ru","Saratov", "+7-(985)-385-64-31",
                "Passwordwordword1333","NewPassworld1321asd456");
        request.setLogin(login);
        Client client = new Client("Иван", "Оскарович","Головин","golova@mail.ru",
                "Saratov", "+79853856431","admin41","rdword1333");
        client.setId(6);
        Client changeClient = new Client(client.getFirstName(), client.getPatronymic(), client.getLastName(), client.getEmail(),
                client.getAddress(), client.getPhone(), client.getLogin(), client.getPassword());
        changeClient.setPassword(request.getNewPassword());
        changeClient.setPassword(request.getNewPassword());
        when(httpReq.getCookies()).thenReturn(cookies);
        when(cookieDataRepository.findById(cookies[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(clientRepository.findByLogin(request.getLogin())).thenReturn(java.util.Optional.of(client));
        when(clientRepository.save(client)).thenReturn(changeClient);
        ResponseClientChangePassDto response = underTest.updateUser(httpReq, request);
        assertEquals(request.getNewPassword(), changeClient.getPassword());
    }

}