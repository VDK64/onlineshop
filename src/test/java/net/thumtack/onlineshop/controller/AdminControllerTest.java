package net.thumtack.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumtack.onlineshop.dto.Request.RequestAdminDto;
import net.thumtack.onlineshop.dto.Request.RequestChangePassAdminDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class AdminControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private static Cookie cookie;

    @Test
    public void regAdminTest() throws Exception {
        RequestAdminDto req = new RequestAdminDto("Андрей", "Васильевич", "Петров",
                "topmanager", "vasiLiy164", "asd66546cxvASD4");
        MvcResult result = mockMvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andReturn();
        cookie = result.getResponse().getCookie("admin");
        assertEquals(200, result.getResponse().getStatus());
    }
        @Test
        public void regAdmin2Test() throws Exception {
        RequestAdminDto req = new RequestAdminDto("Gtnh", "Васильевич", "Петров",
                "topmanager", "петрВасильев123", "asd66546cxvASD4");
        MvcResult result1 = mockMvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andReturn();
        Cookie cookie1 = result1.getResponse().getCookie("admin");
        assertEquals(400, result1.getResponse().getStatus());
    }

    @Test
    public void updateAdminTest() throws Exception {
        RequestChangePassAdminDto reqCh = new RequestChangePassAdminDto("Андрей", "Петров", "Васильевич",
                "topmanager", "asd66546cxvASD4", "Passworld19923165");
        MvcResult result1 = mockMvc.perform(put("/api/admins")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqCh)))
                .andReturn();
        RequestChangePassAdminDto reqNew;
        reqNew = mapper.readValue(result1.getResponse().getContentAsString(), RequestChangePassAdminDto.class);
        assertEquals("Passworld19923165", reqNew.getNewPassword());
    }

}
