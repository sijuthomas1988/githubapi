package com.skr.redcare.githubapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skr.redcare.githubapi.client.GithubAPIRetryableClient;
import com.skr.redcare.githubapi.exception.GithubAPIException;
import com.skr.redcare.githubapi.exception.ServiceException;
import com.skr.redcare.githubapi.model.GithubAPIErrorModel;
import com.skr.redcare.githubapi.model.GithubDataResponse;
import com.skr.redcare.githubapi.service.GithubDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@Service
@Slf4j
public class GithubDataServiceImpl implements GithubDataService {

    private GithubAPIRetryableClient githubAPIRetryableClient;

    public GithubDataServiceImpl(GithubAPIRetryableClient githubAPIRetryableClient) {
        this.githubAPIRetryableClient = githubAPIRetryableClient;
    }

    @Override
    public GithubDataResponse getRepositoryDetails(String query, String sort, String order, Integer page, Integer perPage) throws ServiceException, GithubAPIException {
        /**
         * The rationale behind not using a mapper class or rather having two model classes one for unmarshalling
         * and another for Response is that Github api returns a json string and solely for this task alone, have opted to unmarshall
         * JSON string directly to the response class and have taken the field names returned from github api.
         */
        Long startTime = System.currentTimeMillis();
        ResponseEntity apiResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            apiResponse = this.githubAPIRetryableClient.callGithubSearchRepo(query, sort, order, perPage, page);
        } catch (Exception e) {
            log.error("Error thrown when calling github external API with exception : " + e);
            throw new GithubAPIException("Error thrown when calling Github API", null, e);
        }

        if (apiResponse.getStatusCode() == HttpStatus.OK) {
            Long endTime = System.currentTimeMillis();
            NumberFormat formatter = new DecimalFormat("#0.00000");
            log.info("Github API data return time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");

            try {
                GithubDataResponse response = objectMapper.readValue(apiResponse.getBody().toString(), GithubDataResponse.class);
                return response;
            } catch (JsonProcessingException e) {
                log.error("Error thrown when parsing github api 200 status response : " + e);
                throw new ServiceException(e, "Error thrown when parsing github api 200 status response");
            }
        } else {
            try {
                GithubAPIErrorModel errorModel = objectMapper.readValue(apiResponse.getBody().toString(), GithubAPIErrorModel.class);
                throw new GithubAPIException("Error recieved from API call", errorModel);

            } catch (JsonProcessingException e) {
                log.error("Error thrown when parsing github api non 200 status response : " + e);
                throw new ServiceException(e, "Error thrown when parsing github api non 200 status response");
            }

        }
    }
}
