package com.example.base.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormResponse {
    private boolean success;
    private Object data;
    private String error;
}
