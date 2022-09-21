package com.example.base.common.middleware.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseErrorValidation {
    int status;

    String message;

    long timestamp;

    String path;

    Map<String, String> validationErrors;

    public ResponseErrorValidation(int status, String message) {
        super();
        this.timestamp = new Date().getTime();
        this.status = status;
        this.message = message;
    }
    public ResponseErrorValidation(int status, String message, String path) {
        super();
        this.timestamp = new Date().getTime();
        this.status = status;
        this.message = message;
        this.path = path;
    }

}
