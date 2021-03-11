package com.example.group.web.exception;

import com.example.group.constant.Constants;
import com.example.group.web.dto.ResponseDto;
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
    public final ResponseEntity<ResponseDto> groupNotFoundException(Exception groupNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_NOT_FOUND, groupNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public final ResponseEntity<ResponseDto> roleNotFoundException(Exception roleNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_NOT_FOUND, roleNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(GroupRoleNotFoundException.class)
    public final ResponseEntity<ResponseDto> groupRoleNotFoundException(Exception groupRoleNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_ROLE_NOT_FOUND, groupRoleNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_SERVICE_INVALID_METHOD_ARGUMENT, "Failed Validation of Request Body", errorMessage);

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GroupNameNotUniqueException.class)
    public ResponseEntity<ResponseDto> groupNameNotUniqueException(GroupNameNotUniqueException groupNameNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_NAME_DATA_INTEGRITY_VIOLATION, groupNameNotUniqueException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);

    }

    @ExceptionHandler(GroupRoleNotUniqueException.class)
    public ResponseEntity<ResponseDto> groupRoleNotUniqueException(GroupRoleNotUniqueException groupRoleNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_ROLE_DATA_INTEGRITY_VIOLATION, groupRoleNotUniqueException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(RoleServiceDownException.class)
    public final ResponseEntity<ResponseDto> roleServiceDownException(Exception roleServiceDownException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_SERVICE_DOWN, "Cannot connect to role service!", roleServiceDownException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
