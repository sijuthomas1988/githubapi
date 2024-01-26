package com.skr.redcare.githubapi.exception;

import lombok.Getter;

/**
 * Service Exception class
 */
public class ServiceException  extends Exception {

    /** Status code */
    @Getter
    private String code;

    /** error message */
    @Getter
    private String message;

    @Getter
    private String status;

    /**
     * Constructor
     *
     * @param code
     *     status code
     * @param message
     *     error messages
     */
    public ServiceException(String code, String status, String message) {
        super(message);
        this.code = code;
        this.status = status;
        this.message = message;
    }

    /**
     * Constructor
     *
     * @param message
     *     error message
     */
    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Constructor
     *
     * @param message
     *     error message
     * @param e
     *     exception
     */
    public ServiceException(Exception e, String message) {
        super(message, e);
        this.message = message;
    }
}
