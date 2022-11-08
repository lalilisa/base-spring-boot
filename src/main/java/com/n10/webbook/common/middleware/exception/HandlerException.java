package com.n10.webbook.common.middleware.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
// Xử lý exception
@RestControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public final ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError;
        Throwable cause = ex.getCause();
        if (cause != null) {
            apiError = new ApiError("Generic Exception", ex.getLocalizedMessage(), ex.getCause().getMessage());
        } else {
            apiError = new ApiError("Generic Exception", ex.getLocalizedMessage());
        }

        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ApiError> handleCustomException(CustomException ex, WebRequest request) {
        ApiError apiError = new ApiError(ex);
        return new ResponseEntity<>(apiError, ex.status);
    }
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            org.springframework.beans.TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiError apiError = new ApiError("Argument type mismatch", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        ApiError apiError = new ApiError("No handler found", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseErrorValidation error = new ResponseErrorValidation(400, ex.getMessage());
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return new ResponseEntity<Object>(error,HttpStatus.BAD_REQUEST);
    }
}