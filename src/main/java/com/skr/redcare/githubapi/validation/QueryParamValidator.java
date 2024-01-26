package com.skr.redcare.githubapi.validation;

import com.skr.redcare.githubapi.configuration.AppConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParamValidator implements ConstraintValidator<QueryParamAssert, String> {

    @Autowired
    private AppConfig appConfig;
    private String queryParam;
    private String message;

    @Override
    public void initialize(QueryParamAssert constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
        this.queryParam = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String query, ConstraintValidatorContext constraintValidatorContext) {
        Boolean isValid = true;
        Map<String, String> queryParamsMap = splitQueryParams(query);

        if (queryParamsMap.size() > 0) {
            // validate if the query param has accepted values
            List<String> acceptedQueryParamValues = this.appConfig.getQueryValidation().getQuery();
            List<String> nonAcceptedQueryValues = queryParamsMap.keySet().stream().filter(u -> !acceptedQueryParamValues.contains(u)).collect(Collectors.toList());
            if (nonAcceptedQueryValues.size() > 0) {

                isValid = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(message.concat(nonAcceptedQueryValues.toString()))
                        .addConstraintViolation();

            }

            // type validation in query param for date
            List<String> dateValidation = this.appConfig.getTypeValidation().getDate();
            List<String> queryValuesForDateValidationIfPresent = queryParamsMap.keySet().stream().filter(u -> dateValidation.contains(u)).collect(Collectors.toList());

            if (queryValuesForDateValidationIfPresent.size() > 0) {
                for (String s : queryValuesForDateValidationIfPresent) {
                    boolean isValidDate = checkDatePattern("yyyy-MM-dd", queryParamsMap.get(s));

                    if (!isValidDate) {
                        isValid = false;
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate("Accepted date format for " + s + " is YYYY-mm-DD. Provided date value " + queryParamsMap.get(s) + " is invalid")
                                .addConstraintViolation();

                    }
                }
            }

            // type validation for strings without numbers
            List<String> validationForStringWithoutNumbers = this.appConfig.getTypeValidation().getStringWithoutNumbers();
            List<String> queryValuesForStringWithoutNumbersIfPresent = queryParamsMap.keySet().stream().filter(u -> validationForStringWithoutNumbers.contains(u)).collect(Collectors.toList());

            if (queryValuesForStringWithoutNumbersIfPresent.size() > 0) {
                for (String s: queryValuesForStringWithoutNumbersIfPresent) {
                    boolean isValidString = checkWhetherStringContainsNumbers(queryParamsMap.get(s));
                    if (isValidString) {
                        isValid = false;
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate("Value for " + s + " is not correct. Provided string value " + queryParamsMap.get(s) + " is invalid")
                                .addConstraintViolation();
                    }
                }
            }
        }

        return isValid;
    }

    public boolean checkWhetherStringContainsNumbers(String input) {
        return input.chars().anyMatch(Character::isDigit);
    }
    public boolean checkDatePattern(String dateFormat, String data) {
        try {
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            Date date= formatter.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    private Map<String, String> splitQueryParams(String query) {
        Map<String, String> queryParamSplit = new HashMap<>();
        String[] splitByCommaDelimiter = query.split(",");
        for (String s : splitByCommaDelimiter) {
            String[] s1 = s.split(":");
            queryParamSplit.put(s1[0], s1[1]);
        }

        return queryParamSplit;
    }
}
