package com.skr.redcare.githubapi.client;

import com.skr.redcare.githubapi.configuration.AppConfig;
import com.skr.redcare.githubapi.configuration.GithubAPIDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubAPIRetryableClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @Mock
    private AppConfig appConfig;

    @Autowired
    private GithubAPIRetryableClient githubAPIRetryableClient;

    private static Set<HttpStatus> allowedHttpStatusCodes() {
        return Set.of(BAD_GATEWAY, SERVICE_UNAVAILABLE, INTERNAL_SERVER_ERROR, GATEWAY_TIMEOUT);
    }

    @ParameterizedTest
    @MethodSource("allowedHttpStatusCodes")
    void retriableExceptions_testRetryWorks(HttpStatus httpStatus) {
        GithubAPIDetails apiDetails = new GithubAPIDetails();
        apiDetails.setHost("test");
        apiDetails.setPath("test");
        apiDetails.setScheme("test");
        when(this.appConfig.getGithubAPIDetails()).thenReturn(apiDetails);
        given(
                restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class), any(Object[].class))).willThrow(new HttpServerErrorException(httpStatus));

        Assertions.assertThrows(HttpStatusCodeException.class, () -> githubAPIRetryableClient.callGithubSearchRepo(any(String.class), any(String.class), any(String.class), any(Integer.class), 1));

        verify(restTemplate, times(2))
                .exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class), any(Object[].class));
    }
}