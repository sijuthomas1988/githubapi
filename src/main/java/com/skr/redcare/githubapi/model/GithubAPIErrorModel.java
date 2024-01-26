package com.skr.redcare.githubapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GithubAPIErrorModel {
    private String message;

    private List<GithubAPIErrors> errors;
}


