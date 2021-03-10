package com.example.role.web.dto;

import com.example.role.web.exception.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private Object success;
    private ExceptionResponse error;
}
