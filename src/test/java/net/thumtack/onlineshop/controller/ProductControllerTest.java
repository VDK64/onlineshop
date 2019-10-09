package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestProductDto;
import net.thumtack.onlineshop.dto.Response.ResponseProductDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class ProductControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static final Cookie cookie = new Cookie("admin", "proverochniutoken64");

    @Test
    public void addProductTest() throws Exception {
        RequestProductDto reqProd = new RequestProductDto("Paper", 200, 500, Collections.singletonList(3));
        MvcResult result1 = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(reqProd)))
                .andReturn();
        ResponseProductDto respProd = mapper.readValue(result1.getResponse().getContentAsString(), ResponseProductDto.class);
        assertNotNull(respProd.getId());
        assertEquals(200, result1.getResponse().getStatus());
    }

    @Test
    public void addProductTest2() throws Exception {
        RequestProductDto reqProd = new RequestProductDto("water", -300, 0, new ArrayList<>(Arrays.asList(3,4,1)));
        MvcResult result1 = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(reqProd)))
                .andReturn();
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void updateProductTest() throws Exception {
        RequestProductDto reqProd = new RequestProductDto();
        reqProd.setName("White Paper");
        MvcResult result1 = mockMvc.perform(put("/api/products/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(reqProd)))
                .andReturn();
        ResponseProductDto respProd = mapper.readValue(result1.getResponse().getContentAsString(), ResponseProductDto.class);
        assertEquals("White Paper", respProd.getName());
        assertEquals(200, result1.getResponse().getStatus());
    }

    @Test
    public void deleteProductTest() throws Exception {
        MvcResult result1 = mockMvc.perform(delete("/api/products/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result1.getResponse().getStatus());
    }

    @Test
    public void deleteProductTest2() throws Exception {
        MvcResult result1 = mockMvc.perform(delete("/api/products/{id}", 30)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void infoProductTest() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/api/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result1.getResponse().getStatus());
    }

    @Test
    public void infoProductTest2() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/api/products/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void infoProductTest3() throws Exception {
        Cookie cook = new Cookie("A", "B");
        MvcResult result1 = mockMvc.perform(get("/api/products/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cook))
                .andReturn();
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void productsListTest() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .param("categories", "1")
                .param("categories", "2")
                .param("order", "product"))
                .andReturn();
        assertEquals(200, result1.getResponse().getStatus());
    }

}