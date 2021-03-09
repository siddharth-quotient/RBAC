package com.example.group.web.exception;

import com.example.group.constant.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * [Group Service] controller advice allows exception handling techniques across the whole application
 *
 * @author Siddharth Mehta
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GroupNotFoundException.class)
    public final ResponseEntity<Object> groupNotFoundException(Exception groupNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_NOT_FOUND, groupNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public final ResponseEntity<Object> roleNotFoundException(Exception roleNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_NOT_FOUND, roleNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupRoleNotFoundException.class)
    public final ResponseEntity<Object> groupRoleNotFoundException(Exception groupRoleNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_ROLE_NOT_FOUND, groupRoleNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_SERVICE_INVALID_METHOD_ARGUMENT, "Failed Validation of Request Body", errorMessage);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GroupNameNotUniqueException.class)
    public ResponseEntity<Object> groupNameNotUniqueException(GroupNameNotUniqueException groupNameNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_NAME_DATA_INTEGRITY_VIOLATION, groupNameNotUniqueException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GroupRoleNotUniqueException.class)
    public ResponseEntity<Object> groupRoleNotUniqueException(GroupRoleNotUniqueException groupRoleNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_ROLE_DATA_INTEGRITY_VIOLATION, groupRoleNotUniqueException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoleServiceDownException.class)
    public final ResponseEntity<Object> roleServiceDownException(Exception roleServiceDownException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_SERVICE_DOWN, roleServiceDownException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
