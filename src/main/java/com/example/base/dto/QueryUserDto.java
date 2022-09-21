package com.example.base.dto;

import com.example.base.common.dto.QueryDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;



public class QueryUserDto extends QueryDto {

    // thuộc tính để tìm kiếm
    @Schema(allowableValues = {"username","password"},defaultValue = "[\"username\",\"password\"]")
    private String[] property;


}
