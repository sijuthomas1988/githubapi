package com.skr.redcare.githubapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OwnerDetailsResponse {

    @JsonProperty("id")
    private Integer OwnerId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("type")
    private String type;

    @JsonProperty("site_admin")
    private Boolean siteAdmin;
}
