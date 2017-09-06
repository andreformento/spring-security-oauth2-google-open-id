package com.formento.core.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// https://docs.spring.io/spring-security/site/docs/current/reference/html/test-mockmvc.html
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class HomeControllerIT {

    private static final String PATH = "/";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private MockMvcRequestSpecification given;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();

        this.given = RestAssuredMockMvc.
            given().
            mockMvc(mvc).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(ContentType.JSON);
    }

    @Test
    @WithMockUser(username = "andre")
    public void shouldDoOkRequest() {
        given.
            get(PATH).
            then().
            statusCode(is(HttpStatus.OK.value())).
            body("username", equalTo("andre"));
    }

}
