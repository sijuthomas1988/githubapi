package com.skr.redcare.githubapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GithubDataResponse {

    @JsonProperty("items")
    private List<RepoDetailsResponse> data;
}

