package com.skr.redcare.githubapi.client;

import com.skr.redcare.githubapi.configuration.AppConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class GithubAPIRetryableClient extends BaseRetryableApiClient {

    // private static final String URL1 = "https://api.github.com/search/repositories?q=created:2019-01-10+language:java&sort=stars&order=desc&per_page=1&page=1";

    private RestTemplate restTemplate;

    private AppConfig appConfig;

    public GithubAPIRetryableClient(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    public ResponseEntity callGithubSearchRepo(String query, String sort, String order, Integer perPage, Integer page) {
        URL url;
        try {
            url = UriComponentsBuilder.newInstance()
                    .scheme(this.appConfig.getGithubAPIDetails().getScheme())
                    .host(this.appConfig.getGithubAPIDetails().getHost())
                    .path(this.appConfig.getGithubAPIDetails().getPath())
                    .queryParam("q", replaceCommaWithSpaceInQuery(query))
                    .queryParam("sort", sort)
                    .queryParam("order", order)
                    .queryParam("per_page", perPage)
                    .queryParam("page", page)
                    .build()
                    .toUri()
                    .toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity = getHttpClient(restTemplate, url.toString(), new HttpEntity<>(headers), String.class);

        return responseEntity;
    }

    private String replaceCommaWithSpaceInQuery(String query) {
        if (query == null || query == "") {
            return "";
        } else {
            return query.replace(",", "+");
        }
    }
}
