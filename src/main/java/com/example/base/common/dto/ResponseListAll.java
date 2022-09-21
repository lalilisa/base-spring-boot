package com.example.base.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListAll {
    private int totalPage;
    private int currentPage;
    private int currentData;
    private Object data;
}
