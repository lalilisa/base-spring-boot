package com.example.base.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


//Form data json tra ve chung
@Data
@AllArgsConstructor
public class FormResponse {
    private boolean success;
    private Object data;
    private String error;
}
