package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestCategoryDto;
import net.thumtack.onlineshop.dto.Response.ResponseCategoryDto;
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
import java.util.List;

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
public class CategoryControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static final Cookie cookie = new Cookie("admin","proverochniutoken64");

    @Test
    public void addCatTest() throws Exception {
        RequestCategoryDto reqCat = new RequestCategoryDto("Clothes", 0);
        MvcResult result1 = mockMvc.perform(post("/api/categories")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqCat)))
                .andReturn();
        ResponseCategoryDto respCat = mapper.readValue(result1.getResponse().getContentAsString(), ResponseCategoryDto.class);
        assertNotNull(respCat.getId());
        assertEquals(200, result1.getResponse().getStatus());
    }
    @Test
    public void addCat2Test() throws Exception {
        RequestCategoryDto reqCat1 = new RequestCategoryDto("Apples", 20);
        MvcResult result2 = mockMvc.perform(post("/api/categories")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqCat1)))
                .andReturn();
        assertEquals(400, result2.getResponse().getStatus());
    }

    @Test
    public void addCat3Test() throws Exception {
        RequestCategoryDto reqCat1 = new RequestCategoryDto("", 0);
        MvcResult result2 = mockMvc.perform(post("/api/categories")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqCat1)))
                .andReturn();
        assertEquals(400, result2.getResponse().getStatus());
    }

    @Test
    public void addCat4Test() throws Exception {
        RequestCategoryDto reqCat1 = new RequestCategoryDto(null, 0);
        MvcResult result2 = mockMvc.perform(post("/api/categories")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqCat1)))
                .andReturn();
        assertEquals(400, result2.getResponse().getStatus());
    }

    @Test
    public void getCatTest() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/api/categories/{id}", 3)
                .cookie(cookie))
                .andReturn();
        assertEquals(200, result1.getResponse().getStatus());
    }

    @Test
    public void getCat2Test() throws Exception {
        MvcResult result2 = mockMvc.perform(get("/api/categories/{id}", 23)
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result2.getResponse().getStatus());
    }

    @Test
    public void updateCatTest() throws Exception {
        RequestCategoryDto reqCat = new RequestCategoryDto("Super Bad pr", null);
        MvcResult result1 = mockMvc.perform(put("/api/categories/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(reqCat)))
                .andReturn();
        ResponseCategoryDto respCat = mapper.readValue(result1.getResponse().getContentAsString(), ResponseCategoryDto.class);
        assertEquals(200, result1.getResponse().getStatus());
        assertNotNull(respCat.getId());
    }

    @Test
    public void updateCat2Test() throws Exception {
        RequestCategoryDto reqCat = new RequestCategoryDto("Super Bad pr", 0);
        MvcResult result1 = mockMvc.perform(put("/api/categories/{id}", 4)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(reqCat)))
                .andReturn();
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void deleteCatTest() throws Exception {
        RequestCategoryDto reqCat = new RequestCategoryDto("Super Bad pr", null);
        MvcResult result1 = mockMvc.perform(delete("/api/categories/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(reqCat)))
                .andReturn();
        assertEquals(200, result1.getResponse().getStatus());
    }

    @Test
    public void deleteCat2Test() throws Exception {
        MvcResult result1 = mockMvc.perform(delete("/api/categories/{id}", 10)
                .cookie(cookie))
                .andReturn();
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void getAllCatTest() throws Exception {
        MvcResult result1 = mockMvc.perform(get("/api/categories")
                .cookie(cookie))
                .andReturn();
        List<ResponseCategoryDto> respCat = mapper.readValue(result1.getResponse().getContentAsString(), new TypeReference<List<ResponseCategoryDto>>(){});
        assertEquals(6, respCat.size());
        assertEquals(200, result1.getResponse().getStatus());
    }

}