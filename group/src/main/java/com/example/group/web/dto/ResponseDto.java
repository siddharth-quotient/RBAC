package com.example.group.web.dto;

import com.example.group.web.exception.ExceptionResponse;
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
