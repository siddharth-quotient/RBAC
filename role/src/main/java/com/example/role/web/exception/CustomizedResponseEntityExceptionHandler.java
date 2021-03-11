package com.example.role.web.exception;

import com.example.role.constant.Constants;
import com.example.role.web.dto.ResponseDto;
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
 * [Role Service] controller advice allows exception handling techniques across the whole application
 *
 * @author Siddharth Mehta
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public final ResponseEntity<ResponseDto> roleNotFoundException(Exception roleNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_NOT_FOUND, roleNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_SERVICE_INVALID_METHOD_ARGUMENT, "Failed Validation of Request Body", errorMessage);

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(RoleNameNotUniqueException.class)
    public ResponseEntity<Object> roleNameNotUniqueException(RoleNameNotUniqueException roleNameNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_NAME_DATA_INTEGRITY_VIOLATION, roleNameNotUniqueException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.CONFLICT);
    }

}
