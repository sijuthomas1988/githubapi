package com.skr.redcare.githubapi.validation;

import com.skr.redcare.githubapi.configuration.AppConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderParamValidator implements ConstraintValidator<OrderParamAssert, String> {

    @Autowired
    private AppConfig appConfig;
    private String orderParam;
    private String message;

    @Override
    public void initialize(OrderParamAssert constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
        this.orderParam = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Boolean isValid = true;
        // validate if the sort param has accepted values
        List<String> acceptedQueryParamValues = this.appConfig.getQueryValidation().getOrder();
        boolean validOrderParam = acceptedQueryParamValues.stream().anyMatch(s::equalsIgnoreCase);
        if (!validOrderParam) {
            isValid = false;

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message.concat(s))
                    .addConstraintViolation();

        }

        return isValid;
    }
}
