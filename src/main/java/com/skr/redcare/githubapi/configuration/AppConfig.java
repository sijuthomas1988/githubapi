package com.skr.redcare.githubapi.configuration;

import com.skr.redcare.githubapi.model.GithubAPIDetails;
import com.skr.redcare.githubapi.model.QueryValidation;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Config class for loading app related configs;
 */
@Component
@ConfigurationProperties("app")
@Data
public class AppConfig {
    private QueryValidation queryValidation;

    private TypeValidation typeValidation;

    private GithubAPIDetails githubAPIDetails;
}

