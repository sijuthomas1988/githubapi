package com.skr.redcare.githubapi.configuration;

import lombok.Data;

import java.util.List;

@Data
public class TypeValidation {
    private List<String> stringWithoutNumbers;

    private List<String> date;
}
