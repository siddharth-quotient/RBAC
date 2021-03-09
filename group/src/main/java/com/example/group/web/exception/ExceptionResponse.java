package com.example.group.web.exception;

import java.util.Date;

/**
 *Provides consistent Error Message format across the entire application
 *
 * @author Siddharth Mehta
 */
public class ExceptionResponse {
    private Date timeStamp;
    private Integer errorCode;
    private String message;
    private String details;

    public ExceptionResponse(Date timeStamp, Integer errorCode, String message, String details) {
        this.timeStamp = timeStamp;
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Integer getErrorCode() { return errorCode; }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
