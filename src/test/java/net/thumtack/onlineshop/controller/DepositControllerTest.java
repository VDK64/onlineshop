package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestDepositDto;
import net.thumtack.onlineshop.dto.Response.ResponseClientDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class DepositControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static Cookie cookie = new Cookie("client", "TokenTestClient123");

    @Test
    public void addDepositTest() throws Exception {
        RequestDepositDto request = new RequestDepositDto(500);
        MvcResult result = mockMvc.perform(put("/api/deposits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        ResponseClientDto response = mapper.readValue(result.getResponse().getContentAsString(), ResponseClientDto.class);
        assertEquals(200, result.getResponse().getStatus());
        assertEquals((Integer)3500, response.getDeposit());
    }

    @Test
    public void addDepositTest2() throws Exception {
        RequestDepositDto request = new RequestDepositDto(0);
        MvcResult result = mockMvc.perform(put("/api/deposits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void addDepositTest3() throws Exception {
        RequestDepositDto request = new RequestDepositDto(-100);
        MvcResult result = mockMvc.perform(put("/api/deposits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void withdrawTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/deposits")
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void addDepositTest4() throws Exception {
        Cookie wrongCookie = new Cookie("client", "asdasdasdq123");
        RequestDepositDto request = new RequestDepositDto(5000);
        MvcResult result = mockMvc.perform(put("/api/deposits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(wrongCookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void withdrawTest2() throws Exception {
        Cookie wrongCookie = new Cookie("client", "asdasdasdq123");
        MvcResult result = mockMvc.perform(get("/api/deposits")
                .cookie(wrongCookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }
}