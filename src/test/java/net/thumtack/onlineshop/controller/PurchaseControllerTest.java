package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseCartBuyDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductBuyDto;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class PurchaseControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static Cookie cookie = new Cookie("client", "TokenTestClient123");

    @Test
    public void buyProductTest() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 300, 5);
        MvcResult result = mockMvc.perform(post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void buyProductTest2() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "со добрый", 300, 5);
        MvcResult result = mockMvc.perform(post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void buyProductTest3() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 300, 59000);
        MvcResult result = mockMvc.perform(post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void buyProductTest4() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(1, "сок добрый", 3000, 5);
        MvcResult result = mockMvc.perform(post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void buyProductTest5() throws Exception {
        RequestProductBuyDto request = new RequestProductBuyDto(3, "Adidas shoes", 7000, 50);
        MvcResult result = mockMvc.perform(post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    public void buyCartProductsTest() throws Exception {
        Cookie cookie = new Cookie("client", "testClient2365");
        List<RequestProductBuyDto> request = Arrays.asList(
          new ResponseProductBuyDto(1, "сок добрый", 300, 20),
          new ResponseProductBuyDto(2, "китайские ботинки", 2000, 15),
          new ResponseProductBuyDto(3, "Adidas shoes", 7000, 60),
          new ResponseProductBuyDto(4, "Tapki", 300, null)
        );
        MvcResult result = mockMvc.perform(post("/api/purchases/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        ResponseCartBuyDto response = mapper.readValue(result.getResponse().getContentAsString(), ResponseCartBuyDto.class);
        assertEquals(3, response.getBought().size());
        assertEquals(1, response.getRemaining().size());
        assertEquals(200, result.getResponse().getStatus());
    }
}