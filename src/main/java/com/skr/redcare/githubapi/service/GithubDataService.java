package com.skr.redcare.githubapi.service;

import com.skr.redcare.githubapi.exception.GithubAPIException;
import com.skr.redcare.githubapi.exception.ServiceException;
import com.skr.redcare.githubapi.model.GithubDataResponse;

import java.util.List;

public interface GithubDataService {
    GithubDataResponse getRepositoryDetails(String query, String sort, String order, Integer page, Integer perPage) throws ServiceException, GithubAPIException;
}
