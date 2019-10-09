package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestLoginDto;
import net.thumtack.onlineshop.exceptions.RespError;
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
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SessionControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static Cookie cookie;

    @Test
    public void test3() throws Exception {
        RequestLoginDto request = new RequestLoginDto("client45", "Sarcity64");
        MvcResult result = mockMvc.perform(post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getCookie("client"));
    }

    @Test
    public void test1() throws Exception {
        Cookie cookie = new Cookie("client", "testClient2365");
        RequestLoginDto request = new RequestLoginDto("client45", "Sarcity64");
        MvcResult result = mockMvc.perform(post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        List<RespError> errorList =
                mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<RespError>>(){});
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("ALREADY_LOG_IN", errorList.get(0).getErrorCode());
    }

    @Test
    public void test4() throws Exception {
        Cookie cookie = new Cookie("client", "testClient2365");
        RequestLoginDto request = new RequestLoginDto("client45", "Sarckity64");
        MvcResult result = mockMvc.perform(post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        List<RespError> errorList =
                mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<RespError>>(){});
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("WRONG_PASSWORD_LOG_IN", errorList.get(0).getErrorCode());
    }

    @Test
    public void test5() throws Exception {
        Cookie cookie = new Cookie("client", "testClient2365");
        RequestLoginDto request = new RequestLoginDto("cliefnt45", "Sarcity64");
        MvcResult result = mockMvc.perform(post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        List<RespError> errorList =
                mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<RespError>>(){});
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("WRONG_lOGIN_ABSENT", errorList.get(0).getErrorCode());
    }

    @Test
    public void test2() throws Exception {
        Cookie cookie = new Cookie("client", "testClient2365");
        MvcResult result = mockMvc.perform(delete("/api/sessions")
                .cookie(cookie))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
    }
}