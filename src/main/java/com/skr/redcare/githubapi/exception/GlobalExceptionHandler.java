package com.skr.redcare.githubapi.exception;

import com.skr.redcare.githubapi.model.ErrorDetails;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
@Slf4j
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorDetails> handleValidationErrors(ConstraintViolationException ex) {
        log.debug("Constraint violation exception encountered: {}", ex.getConstraintViolations(), ex);
        return buildValidationErrors(ex.getConstraintViolations());
    }

    private List<ErrorDetails>  buildValidationErrors(Set<ConstraintViolation<?>> violations) {
        return violations.stream().map(violation -> ErrorDetails.builder().timeStamp(new Timestamp(System.currentTimeMillis())).details(violation.getMessage()).build()).collect(toList());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails handleServiceException(ServiceException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        errorDetails.setDetails(e.getMessage());
        return errorDetails;
    }

    @ExceptionHandler(GithubAPIException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDetails handleAPIException(GithubAPIException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        errorDetails.setGithubAPIErrors(e.getGithubAPIErrorModel().getErrors());
        errorDetails.setDetails(e.getMessage());
        return errorDetails;
    }
}
