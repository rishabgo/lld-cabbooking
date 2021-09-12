package com.rishabh.lldcabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Data
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String message;
}
