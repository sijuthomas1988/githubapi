package com.skr.redcare.githubapi.controller;

import com.skr.redcare.githubapi.exception.GithubAPIException;
import com.skr.redcare.githubapi.exception.ServiceException;
import com.skr.redcare.githubapi.model.GithubAPIErrorModel;
import com.skr.redcare.githubapi.model.GithubAPIErrors;
import com.skr.redcare.githubapi.service.impl.GithubDataServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GithubDataControllerTest {

    @MockBean
    private GithubDataServiceImpl githubDataService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getGithubRepoSearch_throwsApiException() throws Exception {
        Mockito.when(this.githubDataService.getRepositoryDetails(any(),any(), any(), any(), any()))
                .thenThrow(new GithubAPIException("Error thrown from API",getAPIErrorModel() ));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/search/repositories?query=created:2019-12-10,language:java&sort=stars&order=desc&perPage=1&page=1");
        ResultActions actions = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.details", Matchers.equalTo("Error thrown from API")))
                .andExpect(jsonPath("$.githubAPIErrors.[0].message", Matchers.equalTo("error from api")));
    }

    @Test
    public void getGithubRepoSearch_throwsServiceException() throws Exception {
        Mockito.when(this.githubDataService.getRepositoryDetails(any(),any(), any(), any(), any()))
                .thenThrow(new ServiceException("Error thrown from Service Exception"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/search/repositories?query=created:2019-12-10,language:java&sort=stars&order=desc&perPage=1&page=1");
        ResultActions actions = mockMvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.details", Matchers.equalTo("Error thrown from Service Exception")));
    }

    private GithubAPIErrorModel getAPIErrorModel() {
        GithubAPIErrors githubAPIErrors = GithubAPIErrors.builder()
                .message("error from api")
                .resource("test resource")
                .build();
        GithubAPIErrorModel githubAPIErrorModel = GithubAPIErrorModel.builder()
                .errors(Arrays.asList(githubAPIErrors))
                .message("error from API")
                .build();
        return githubAPIErrorModel;
    }
}
