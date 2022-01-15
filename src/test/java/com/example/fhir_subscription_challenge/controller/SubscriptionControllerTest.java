package com.example.fhir_subscription_challenge.controller;

import com.example.fhir_subscription_challenge.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SubscriptionControllerTest {
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void SubscriptionController_Valid_String_Success() throws Exception {

        String uri = "/api/receive/Patient/1";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType("application/fhir+json").content("hallo")).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void SubscriptionController_Valid_String_IncorrectMediaType_Failure() throws Exception {
        String uri = "/api/receive/Patient/1";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType("application/json").content("")).andReturn();

        assertEquals(415, result.getResponse().getStatus());
    }

    @Test
    public void SubscriptionController_Valid_String_InvalidMethod_Failure() throws Exception {
        String uri = "/api/receive/Patient/1";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType("application/fhir+json").content("")).andReturn();

        assertEquals(405, result.getResponse().getStatus());
    }
}
