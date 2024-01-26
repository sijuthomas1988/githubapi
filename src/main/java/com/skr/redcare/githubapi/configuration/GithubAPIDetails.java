package com.skr.redcare.githubapi.configuration;

import lombok.Data;

@Data
public class GithubAPIDetails {

    private String scheme;

    private String host;

    private String path;
}
