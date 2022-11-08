package com.n10.webbook.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// form trả về khi get all
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListAll {
    private int totalPage;
    private int currentPage;
    private int currentData;
    private Object data;
}
