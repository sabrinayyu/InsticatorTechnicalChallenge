package com.challenge.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.Site;
import com.challenge.demo.repository.SiteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

public class SiteControllerTest extends SpringBootTests{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Autowired
    SiteRepository siteRepository;

    private Site testSite;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //db set up
        testSite = new Site();
        testSite.setUrl("www.bob.com");
        testSite.setSiteUUID(UUID.randomUUID());

        testSite = siteRepository.save(testSite);
    }

    @Test
    public void TestGetSiteById() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/sites/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.siteId").exists());
    }

    @Test
    public void TestUpdateSite() throws Exception {
        testSite.setUrl("www.test.com");
        mvc.perform( MockMvcRequestBuilders
                .put("/sites/{id}", 1)
                .content(asJsonString(testSite))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.siteId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("www.test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.siteUUID").exists());

    }

    @Test
    public void TestDeleteSite() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .delete("/sites/{id}", 1))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
