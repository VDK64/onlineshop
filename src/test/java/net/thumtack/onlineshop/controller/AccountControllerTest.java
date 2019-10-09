package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class AccountControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static Cookie cookie;

    @Test
    public void info() throws Exception {
        cookie = new Cookie("admin", "proverochniutoken64");
        MvcResult result = mockMvc.perform(get("/api/accounts")
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void info2() throws Exception {
        cookie = new Cookie("client", "testClient2365");
        MvcResult result = mockMvc.perform(get("/api/accounts")
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}