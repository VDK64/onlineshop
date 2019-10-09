package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.config.FileConfig;
import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestAdminDto;
import net.thumtack.onlineshop.dto.Request.RequestChangePassAdminDto;
import net.thumtack.onlineshop.dto.Response.ResponseAdminChangePassDto;
import net.thumtack.onlineshop.dto.Response.ResponseAdminDto;
import net.thumtack.onlineshop.entities.Administrator;
import net.thumtack.onlineshop.entities.CookieData;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import net.thumtack.onlineshop.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AdminServiceImplTest {
    private AdminServiceImpl underTest;
    @Mock
    private CookieDataRepository cookieDataRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    HttpServletRequest httpReq;
    @Mock
    HttpServletResponse httpResp;

    @Before
    public void setUp(){
        FileConfig.maxNameLength = 50;
        FileConfig.minPasswordLength = 8;
        MockitoAnnotations.initMocks(this);
        Validator validator = new Validator(administratorRepository, cookieDataRepository, clientRepository, categoryRepository);
        underTest = new AdminServiceImpl(administratorRepository, validator, cookieDataRepository);
    }

    @Test
    public void regAdmin() throws ServerExceptions {
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestAdminDto reqAdmin = new RequestAdminDto("Иван", "Оскарович", "Головин",
                "topManager","oskar132", "Passworld123321");
        when(administratorRepository.save(admin)).thenReturn(admin);
        ResponseAdminDto savedAdmin = underTest.regAdmin(reqAdmin);
        assertEquals(savedAdmin.getFirstName(), admin.getFirstName());
        assertEquals(savedAdmin.getPosition(), admin.getPosition());
    }

    @Test(expected = ServerExceptions.class)
    public void regAdmin2() throws ServerExceptions {
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestAdminDto reqAdmin = new RequestAdminDto("Ивkан", "Оскарович", "Головин",
                "topManager","oskar132", "Passworld123321");
        when(administratorRepository.save(admin)).thenReturn(admin);
        ResponseAdminDto savedAdmin = underTest.regAdmin(reqAdmin);
    }

    @Test(expected = ServerExceptions.class)
    public void regAdmin3() throws ServerExceptions {
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestAdminDto reqAdmin = new RequestAdminDto("Иван", "Оскарkович", "Головин",
                "topManager","oskar132", "Passworld123321");
        when(administratorRepository.save(admin)).thenReturn(admin);
        ResponseAdminDto savedAdmin = underTest.regAdmin(reqAdmin);
    }

    @Test(expected = ServerExceptions.class)
    public void regAdmin4() throws ServerExceptions {
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestAdminDto reqAdmin = new RequestAdminDto("Иван", "Оскарович", "Голоfghвин",
                "topManager","oskar132", "Passworld123321");
        when(administratorRepository.save(admin)).thenReturn(admin);
        ResponseAdminDto savedAdmin = underTest.regAdmin(reqAdmin);
        assertEquals(savedAdmin.getFirstName(), admin.getFirstName());
        assertEquals(savedAdmin.getPosition(), admin.getPosition());
    }

    @Test(expected = ServerExceptions.class)
    public void regAdmin5() throws ServerExceptions {

        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestAdminDto reqAdmin = new RequestAdminDto("Иван", "Оскарович", "Головин",
                "topManager","os^%$kar132", "Passworld123321");
        when(administratorRepository.save(admin)).thenReturn(admin);
        ResponseAdminDto savedAdmin = underTest.regAdmin(reqAdmin);
        assertEquals(savedAdmin.getFirstName(), admin.getFirstName());
        assertEquals(savedAdmin.getPosition(), admin.getPosition());
    }

    @Test(expected = ServerExceptions.class)
    public void regAdmin6() throws ServerExceptions {
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestAdminDto reqAdmin = new RequestAdminDto("Иван", "Оскарович", "Головин",
                "topManager","oskar132", "Pass");
        when(administratorRepository.save(admin)).thenReturn(admin);
        ResponseAdminDto savedAdmin = underTest.regAdmin(reqAdmin);
        assertEquals(savedAdmin.getFirstName(), admin.getFirstName());
        assertEquals(savedAdmin.getPosition(), admin.getPosition());
    }

    @Test
    public void updateAdmin() throws ServerExceptions {
        Cookie[] cookie = new Cookie[1];
        cookie[0] = new Cookie("admin", "testCookie45");
        CookieData cookieData = new CookieData("admin", "testCookie45", "oskar132");
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestChangePassAdminDto request = new RequestChangePassAdminDto("Иван", "Головин",
                "Оскарович", "topManager", "Passworld123321", "newPassword123654");
        ResponseAdminChangePassDto response= new ResponseAdminChangePassDto(admin);
        when(administratorRepository.save(admin)).thenReturn(admin);
        when(administratorRepository.findByLogin("oskar132")).thenReturn(java.util.Optional.of(admin));
        when(cookieDataRepository.findById(cookie[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(httpReq.getCookies()).thenReturn(cookie);
        ResponseAdminChangePassDto savedAdmin = underTest.updateAdmin(request, httpReq);
        assertEquals(savedAdmin.getNewPassword(), request.getNewPassword());
    }

    @Test(expected = ServerExceptions.class)
    public void updateAdmin2() throws ServerExceptions {
        Cookie[] cookie = new Cookie[1];
        cookie[0] = new Cookie("admin", "testCookie45");
        CookieData cookieData = new CookieData("admin", "testCookie45", "oskar132");
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestChangePassAdminDto request = new RequestChangePassAdminDto("Иван", "Головин",
                "Оскарович", "topManager", "Passworld123321", "new");
        ResponseAdminChangePassDto response= new ResponseAdminChangePassDto(admin);
        when(administratorRepository.save(admin)).thenReturn(admin);
        when(administratorRepository.findByLogin("oskar132")).thenReturn(java.util.Optional.of(admin));
        when(cookieDataRepository.findById(cookie[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(httpReq.getCookies()).thenReturn(cookie);
        ResponseAdminChangePassDto savedAdmin = underTest.updateAdmin(request, httpReq);
    }

    @Test(expected = ServerExceptions.class)
    public void updateAdmin3() throws ServerExceptions {
        Cookie[] cookie = new Cookie[1];
        cookie[0] = new Cookie("client", "testCookie45");
        CookieData cookieData = new CookieData("admin", "testCookie45", "oskar132");
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestChangePassAdminDto request = new RequestChangePassAdminDto("Иван", "Головин",
                "Оскарович", "topManager", "Passworld123321", "newPassword123123");
        ResponseAdminChangePassDto response= new ResponseAdminChangePassDto(admin);
        when(administratorRepository.save(admin)).thenReturn(admin);
        when(administratorRepository.findByLogin("oskar132")).thenReturn(java.util.Optional.of(admin));
        when(cookieDataRepository.findById(cookie[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(httpReq.getCookies()).thenReturn(cookie);
        ResponseAdminChangePassDto savedAdmin = underTest.updateAdmin(request, httpReq);
    }

    @Test(expected = ServerExceptions.class)
    public void updateAdmin4() throws ServerExceptions {
        Cookie[] cookie = new Cookie[1];
        cookie[0] = new Cookie("client", "testCookie45");
        CookieData cookieData = new CookieData("admin", "testCookie45", "oskar132");
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestChangePassAdminDto request = new RequestChangePassAdminDto("Ивfghfghан", "Головин",
                "Оскарович", "topManager", "Passworld123321", "newPassword123123");
        ResponseAdminChangePassDto response= new ResponseAdminChangePassDto(admin);
        when(administratorRepository.save(admin)).thenReturn(admin);
        when(administratorRepository.findByLogin("oskar132")).thenReturn(java.util.Optional.of(admin));
        when(cookieDataRepository.findById(cookie[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(httpReq.getCookies()).thenReturn(cookie);
        ResponseAdminChangePassDto savedAdmin = underTest.updateAdmin(request, httpReq);
    }

    @Test(expected = ServerExceptions.class)
    public void updateAdmin5() throws ServerExceptions {
        Cookie[] cookie = new Cookie[1];
        cookie[0] = new Cookie("admin", "testCookie45");
        CookieData cookieData = new CookieData("admin", "testCookie45", "oskar132");
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestChangePassAdminDto request = new RequestChangePassAdminDto("Иван", "Гоvbnvbnловин",
                "Оскарович", "topManager", "Passworld123321", "newPassword123654");
        ResponseAdminChangePassDto response = new ResponseAdminChangePassDto(admin);
        when(administratorRepository.save(admin)).thenReturn(admin);
        when(administratorRepository.findByLogin("oskar132")).thenReturn(java.util.Optional.of(admin));
        when(cookieDataRepository.findById(cookie[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(httpReq.getCookies()).thenReturn(cookie);
        ResponseAdminChangePassDto savedAdmin = underTest.updateAdmin(request, httpReq);
    }

    @Test(expected = ServerExceptions.class)
    public void updateAdmin6() throws ServerExceptions {
        Cookie[] cookie = new Cookie[1];
        cookie[0] = new Cookie("admin", "testCookie45");
        CookieData cookieData = new CookieData("admin", "testCookie45", "oskar132");
        Administrator admin = new Administrator("Иван", "Оскарович", "Головин", "topManager",
                "oskar132", "Passworld123321");
        RequestChangePassAdminDto request = new RequestChangePassAdminDto("Иван", "Головин",
                "Оскfghfghарович", "topManager", "Passworld123321", "newPassword123654");
        ResponseAdminChangePassDto response = new ResponseAdminChangePassDto(admin);
        when(administratorRepository.save(admin)).thenReturn(admin);
        when(administratorRepository.findByLogin("oskar132")).thenReturn(java.util.Optional.of(admin));
        when(cookieDataRepository.findById(cookie[0].getValue())).thenReturn(java.util.Optional.of(cookieData));
        when(httpReq.getCookies()).thenReturn(cookie);
        ResponseAdminChangePassDto savedAdmin = underTest.updateAdmin(request, httpReq);

    }
}