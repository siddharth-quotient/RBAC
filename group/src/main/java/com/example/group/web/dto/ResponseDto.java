package com.example.group.web.dto;

import com.example.group.web.exception.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Global wrapper for all Data Transfer Objects.
 *
 * @author Siddharth Mehta
 */
@Data
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private Object success;
    private ExceptionResponse error;
}
