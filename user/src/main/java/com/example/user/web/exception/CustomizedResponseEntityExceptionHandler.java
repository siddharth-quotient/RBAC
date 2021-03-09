package com.example.user.web.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * [User Service] controller advice allows exception handling techniques across the whole application
 *
 * @author Siddharth Mehta
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    static final int USER_NOT_FOUND = 40401;
    static final int GROUP_NOT_FOUND = 40402;
    static final int ROLE_NOT_FOUND = 40403;
    static final int USER_GROUP_NOT_FOUND = 40404;
    static final int GROUP_ROLE_NOT_FOUND = 40405;

    static final int USER_SERVICE_INVALID_METHOD_ARGUMENT = 40001;
    static final int GROUP_SERVICE_INVALID_METHOD_ARGUMENT = 40002;
    static final int ROLE_SERVICE_INVALID_METHOD_ARGUMENT = 40003;

    static final int USER_NAME_DATA_INTEGRITY_VIOLATION = 40901;
    static final int GROUP_NAME_DATA_INTEGRITY_VIOLATION = 40902;
    static final int ROLE_NAME_DATA_INTEGRITY_VIOLATION = 40903;
    static final int USER_GROUP_DATA_INTEGRITY_VIOLATION = 40904;
    static final int GROUP_ROLE_DATA_INTEGRITY_VIOLATION = 40905;

    static final int GROUP_SERVICE_DOWN = 50001;
    static final int ROLE_SERVICE_DOWN = 50002;


    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> userNotFoundException(Exception userNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), USER_NOT_FOUND, userNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public final ResponseEntity<Object> groupNotFoundException(Exception groupNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), GROUP_NOT_FOUND, groupNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public final ResponseEntity<Object> roleNotFoundException(Exception roleNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ROLE_NOT_FOUND, roleNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserGroupNotFoundException.class)
    public final ResponseEntity<Object> userGroupNotFoundException(Exception userGroupNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), USER_GROUP_NOT_FOUND, userGroupNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), USER_SERVICE_INVALID_METHOD_ARGUMENT, "Failed Validation of Request Body", errorMessage);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameNotUniqueException.class)
    public ResponseEntity<Object> userNameNotUniqueException(UserNameNotUniqueException userNameNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), USER_NAME_DATA_INTEGRITY_VIOLATION, userNameNotUniqueException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserGroupNotUniqueException.class)
    public ResponseEntity<Object> userGroupNotUniqueException(UserGroupNotUniqueException userGroupNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), USER_GROUP_DATA_INTEGRITY_VIOLATION, userGroupNotUniqueException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GroupServiceDownException.class)
    public final ResponseEntity<Object> groupServiceDownException(Exception groupServiceDownException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), GROUP_SERVICE_DOWN, "Please try again later", groupServiceDownException.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
