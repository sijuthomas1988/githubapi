package com.skr.redcare.githubapi.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GithubDataControllerE2ETest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getGithubRepoSearch_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/search/repositories?query=created:2019-11-10,language:java&sort=stars&order=desc&perPage=1&page=1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.items[0].language", Matchers.equalTo("Java")));
    }

    @Test
    public void getGithubRepoSearch_QueryValidationFailureWithInvalidQueryInput() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/search/repositories?query=created1:2019-11-10,language:java&sort=stars&order=desc&perPage=1&page=1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.[0].details", Matchers.equalTo("The following query params are not accepted : [created1]")));
    }

    @Test
    public void getGithubRepoSearch_QueryValidationFailureWithMultipleInvalidQueryInput1() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/search/repositories?query=created1:2019-11-10,language1:java&sort=starssss&order=desc&perPage=1&page=1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.[1].details", Matchers.either(Matchers.is("The following query params are not accepted : [language1, created1]")).or(Matchers.is("The sort params is not accepted : starssss"))))
                .andExpect(jsonPath("$.[0].details", Matchers.either(Matchers.is("The following query params are not accepted : [language1, created1]")).or(Matchers.is("The sort params is not accepted : starssss"))));
    }

    @Test
    public void getGithubRepoSearch_QueryValidationFailureWithMultipleInvalidQueryInput2() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/search/repositories?query=created1:2019-11-10,language1:java&sort=starssss&order=descfff&perPage=1&page=1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.[1].details", Matchers.either(Matchers.is("The following query params are not accepted : [language1, created1]")).or(Matchers.is("The sort params is not accepted : starssss")).or(Matchers.is("The order param is not accepted :  descfff"))))
                .andExpect(jsonPath("$.[0].details", Matchers.either(Matchers.is("The following query params are not accepted : [language1, created1]")).or(Matchers.is("The sort params is not accepted : starssss")).or(Matchers.is("The order param is not accepted :  descfff"))))
                .andExpect(jsonPath("$.[2].details", Matchers.either(Matchers.is("The following query params are not accepted : [language1, created1]")).or(Matchers.is("The sort params is not accepted : starssss")).or(Matchers.is("The order param is not accepted :  descfff"))));
    }

    @Test
    public void getGithubRepoSearch_QueryValidationFailureWithMultipleInvalidQueryInputAndTypeValidation() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/search/repositories?query=created:2019-13-10,language1:java&sort=stars&order=desc&perPage=1&page=1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.[1].details", Matchers.either(Matchers.is("Accepted date format for created is YYYY-mm-DD. Provided date value 2019-13-10 is invalid")).or(Matchers.is("The following query params are not accepted : [language1]"))))
                .andExpect(jsonPath("$.[0].details", Matchers.either(Matchers.is("Accepted date format for created is YYYY-mm-DD. Provided date value 2019-13-10 is invalid")).or(Matchers.is("The following query params are not accepted : [language1]"))));
    }
}