package com.skr.redcare.githubapi.model;

import lombok.Data;

import java.util.List;

@Data
public class QueryValidation {
    private List<String> sort;
    private List<String> query;

    private List<String> order;

}
