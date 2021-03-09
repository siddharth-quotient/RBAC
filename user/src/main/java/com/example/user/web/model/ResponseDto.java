package com.example.user.web.model;

import com.example.user.web.exception.ExceptionResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDto {
    private Object success;
    private ExceptionResponse error;
}
