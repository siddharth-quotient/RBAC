package com.example.role.web.dto;

import com.example.role.web.exception.ExceptionResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    private Object success;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    private ExceptionResponse error;
}
