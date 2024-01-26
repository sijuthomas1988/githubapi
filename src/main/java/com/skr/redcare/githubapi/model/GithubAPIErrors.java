package com.skr.redcare.githubapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GithubAPIErrors {
    private String message;

    private String resource;

    private String field;

    private String code;
}
