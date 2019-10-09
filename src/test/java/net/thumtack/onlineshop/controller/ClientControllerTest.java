package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestChangePassClientDto;
import net.thumtack.onlineshop.dto.Request.RequestClientDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientsInfoDto;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class ClientControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static final Cookie cookie = new Cookie("admin", "proverochniutoken64");

        @Test
        public void a1() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/api/clients")
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result1.getResponse().getStatus());
        List<ResponseClientsInfoDto> respList = mapper.readValue(result1.getResponse().
                getContentAsString(), new TypeReference<List<ResponseClientsInfoDto>>(){});
        assertEquals(5, respList.size());
    }

    @Test
    public void a2() throws Exception {
        Cookie cook = new Cookie("admin", "asd5321Asdasd");
        MvcResult result1 = mockMvc.perform(get("/api/clients")
                .cookie(cook))
                .andReturn();
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void regClientTest() throws Exception {
        RequestClientDto request = new RequestClientDto("Анатолий", "Сидоров", "Петрович",
                "sidor@mail.ru", "Saratov", "+7-965-121-56-95", "Sidor64", "Password1321asd456");
        MvcResult result = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void regClientTest2() throws Exception {
        RequestClientDto request = new RequestClientDto("Анатgолий", "Сидоров", "Петрович",
                "sidor@mail.ru", "Saratov", "+7-965-121-56-95", "Sidor64", "Password1321asd456");
        MvcResult result = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void regClientTest3() throws Exception {
        RequestClientDto request = new RequestClientDto("Анатолий", "Сидоhров", "Петрович",
                "sidor@mail.ru", "Saratov", "+7-965-121-56-95", "Sidor64", "Password1321asd456");
        MvcResult result = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void regClientTest4() throws Exception {
        RequestClientDto request = new RequestClientDto("Анатолий", "Сидоров", "Петkрович",
                "sidor@mail.ru", "Saratov", "+7-965-121-56-95", "Sidor64", "Password1321asd456");
        MvcResult result = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void regClientTest5() throws Exception {
        RequestClientDto request = new RequestClientDto("Анатолий", "Сидоров", "Петрович",
                "sidoril.ru", "Saratov", "+7-965-121-56-95", "Sidor64", "Password1321asd456");
        MvcResult result = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void regClientTest6() throws Exception {
        RequestClientDto request = new RequestClientDto("Анатолий", "Сидоров", "Петрович",
                "sidor@mail.ru", "Saratov", "+1-965-121-56-95", "Sidor64", "Password1321asd456");
        MvcResult result = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void regClientTest7() throws Exception {
        RequestClientDto request = new RequestClientDto("Анатhjkолий", "Сиhjkдоров", "Петhjkрович",
                "sid.ru", "Saratov", "21-56-95", "", "P56");
        MvcResult result = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        List<RespError> errorList = mapper.readValue(result.getResponse().
                getContentAsString(), new TypeReference<List<RespError>>(){});
        assertEquals(7, errorList.size());
    }

    @Test
    public void updateClientTest() throws Exception {
            RequestClientDto request = new RequestClientDto("Илья", "Петров", "Александрович",
                    "email@mail.ru", "Saratov", "+7-961-456-78-97",
                    "Ilaz132", "Passwordlasd123");
        MvcResult result1 = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        Cookie regCookie = result1.getResponse().getCookie("client");
        assertEquals(200, result1.getResponse().getStatus());

        RequestChangePassClientDto requestChP = new RequestChangePassClientDto("Илья", "Петров",
                "Александрович","email@mail.ru", "Saratov", "+7-961-456-78-97",
                "Passwordlasd123", "NewPassword_64125");
        MvcResult result2 = mockMvc.perform(put("/api/clients")
                .cookie(regCookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestChP)))
                .andReturn();
        assertEquals(200, result2.getResponse().getStatus());
    }

    @Test
    public void updateClientTest2() throws Exception {
            RequestClientDto request = new RequestClientDto("Илья", "Петров", "Александрович",
                "email@mail.ru", "Saratov", "+7-961-456-78-97",
                "Ilaaz132", "Passwordlasd123");
        MvcResult result1 = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        Cookie regCookie = result1.getResponse().getCookie("client");
        assertEquals(200, result1.getResponse().getStatus());

        RequestChangePassClientDto request1 = new RequestChangePassClientDto("Анатолий", "Сидоров",
                "Петрович","sidor@mail.ru", "Saratov", "+7-965-121-56-95",
                "Password1321asd456", "New");
        MvcResult result = mockMvc.perform(put("/api/clients")
                .cookie(regCookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request1)))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
        List<RespError> errorList = mapper.readValue(result.getResponse().
                getContentAsString(), new TypeReference<List<RespError>>(){});
        assertTrue(errorList.get(0).getErrorCode().equalsIgnoreCase(String.valueOf(ServerErrors.WRONG_PASSWORD)));
    }

}