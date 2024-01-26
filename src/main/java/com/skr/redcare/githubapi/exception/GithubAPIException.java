package com.skr.redcare.githubapi.exception;

import com.skr.redcare.githubapi.model.GithubAPIErrorModel;
import lombok.Data;
import lombok.Getter;

public class GithubAPIException extends Exception {

    @Getter
    private GithubAPIErrorModel githubAPIErrorModel;

    @Getter
    private String message;

    public GithubAPIException(String message, GithubAPIErrorModel githubAPIErrorModel) {
        super(message);
        this.githubAPIErrorModel = githubAPIErrorModel;
        this.message = message;
    }

    public GithubAPIException(String message) {
        super(message);
        this.message = message;
    }

    public GithubAPIException(String message, GithubAPIErrorModel githubAPIErrorModel, Exception e) {
        super(message,e);
        this.message = message;
        this.githubAPIErrorModel = githubAPIErrorModel;
    }
}
