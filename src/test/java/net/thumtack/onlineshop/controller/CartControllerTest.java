package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class CartControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static Cookie cookie = new Cookie("client", "TokenTestClient123");

    @Test
    public void addCartProductTest() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 300, 500);
        MvcResult result = mockMvc.perform(post("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void addCartProductTest2() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(2, "сок добрый", 300, 500);
        MvcResult result = mockMvc.perform(post("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void addCartProductTest3() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сfок добрый", 300, 500);
        MvcResult result = mockMvc.perform(post("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void addCartProductTest4() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 3000, 500);
        MvcResult result = mockMvc.perform(post("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void addCartProductTest5() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 300, 0);
        MvcResult result = mockMvc.perform(post("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void addCartProductTest6() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 0, 0);
        MvcResult result = mockMvc.perform(post("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void deleteCartProductTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/baskets/" + 10)
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void deleteCartProductTest2() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/baskets/" + 3)
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void updateCartProductTest() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 300, 100500);
        MvcResult result = mockMvc.perform(put("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void updateCartProductTest2() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(2, "китайские ботинки", 2000, 155);
        MvcResult result = mockMvc.perform(put("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void updateCartProductTest3() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(2, "китайские ботинки", 2000, 155);
        MvcResult result = mockMvc.perform(put("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void allCartProductsTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/baskets")
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}