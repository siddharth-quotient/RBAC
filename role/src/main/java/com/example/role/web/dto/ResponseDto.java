package com.example.role.web.dto;

import com.example.role.web.exception.ExceptionResponse;
import lombok.*;

/**
 * Global wrapper for all Data Transfer Objects.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private Object success;
    private ExceptionResponse error;
}
