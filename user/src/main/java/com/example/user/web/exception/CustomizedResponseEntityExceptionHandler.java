package com.example.user.web.exception;

import com.example.user.constant.Constants;
import com.example.user.web.dto.ResponseDto;
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

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ResponseDto> userNotFoundException(Exception userNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_NOT_FOUND, userNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

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

    @ExceptionHandler(UserGroupNotFoundException.class)
    public final ResponseEntity<ResponseDto> userGroupNotFoundException(Exception userGroupNotFoundException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_GROUP_NOT_FOUND, userGroupNotFoundException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_SERVICE_INVALID_METHOD_ARGUMENT, "Failed Validation of Request Body", errorMessage);

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameNotUniqueException.class)
    public ResponseEntity<ResponseDto> userNameNotUniqueException(UserNameNotUniqueException userNameNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_NAME_DATA_INTEGRITY_VIOLATION, userNameNotUniqueException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserGroupNotUniqueException.class)
    public ResponseEntity<ResponseDto> userGroupNotUniqueException(UserGroupNotUniqueException userGroupNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_GROUP_DATA_INTEGRITY_VIOLATION, userGroupNotUniqueException.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GroupServiceDownException.class)
    public final ResponseEntity<ResponseDto> groupServiceDownException(Exception groupServiceDownException, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_SERVICE_DOWN, "Please try again later", groupServiceDownException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
