package com.example.user.web.model;

import com.example.user.web.exception.ExceptionResponse;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private Object success;
    private ExceptionResponse error;
}
