package com.skr.redcare.githubapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = QueryParamValidator.class)
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParamAssert {
    String message() default "The following query params are not accepted : ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value();
}
