package com.example.base.common.middleware.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiError {
    public String message;
    public List<String> details;

    public ApiError() {

    }

    public ApiError(String message, String... details) {
        this.message = message;
        this.details = Arrays.asList(details);
    }

    public ApiError(Exception ex, String... details) {
        String classNameOfException = ex.getClass().getName();
        int lastDotIndex = classNameOfException.lastIndexOf(".");
        String shortExceptionName;
        if (lastDotIndex > 0) {
            shortExceptionName = classNameOfException.substring(lastDotIndex + 1);
        } else {
            shortExceptionName = classNameOfException;
        }

        this.message = shortExceptionName + " : " + ex.getMessage();
        if (ex.getCause() != null) {
            this.details = new ArrayList<>();
            this.details.add(ex.getCause().getMessage());
        }
        if (details != null && details.length > 0) {
            this.details.addAll(Arrays.asList(details));
        }
    }
}