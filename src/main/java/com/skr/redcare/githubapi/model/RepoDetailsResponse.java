package com.skr.redcare.githubapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RepoDetailsResponse {

    @JsonProperty("id")
    @JsonAlias("repo_id")
    private Integer id;

    @JsonProperty("full_name")
    @JsonAlias("repo_full_name")
    private String fullName;

    @JsonProperty("private")
    @JsonAlias("is_private")
    private Boolean isPrivate;

    @JsonProperty("owner")
    @JsonAlias("repo_owner")
    private OwnerDetailsResponse owner;

    @JsonProperty("description")
    @JsonAlias("repo_description")
    private String description;

    @JsonProperty("url")
    @JsonAlias("repo_url")
    private String repoUrl;

    @JsonProperty("language")
    @JsonAlias("repo_language")
    private String language;

    @JsonProperty("visibility")
    @JsonAlias("repo_visibility")
    private String visibility;
}
