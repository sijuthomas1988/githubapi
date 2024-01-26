package com.skr.redcare.githubapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties("app")
@Data
public class AppConfig {
    private QueryValidation queryValidation;

    private TypeValidation typeValidation;

    private GithubAPIDetails githubAPIDetails;
}

