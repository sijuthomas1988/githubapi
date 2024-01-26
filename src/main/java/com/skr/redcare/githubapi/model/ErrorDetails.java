package com.skr.redcare.githubapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Model Class for Error Object to be returned during response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetails {

    /**
     * Time stamp at which error occurred
     */
    private Timestamp timeStamp;
    /**
     * Details of the error message like message
     */
    private String details;

    private List<GithubAPIErrors> githubAPIErrors;
}