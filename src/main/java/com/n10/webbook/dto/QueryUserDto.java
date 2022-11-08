package com.n10.webbook.dto;

import com.n10.webbook.common.dto.QueryDto;
import io.swagger.v3.oas.annotations.media.Schema;


public class QueryUserDto extends QueryDto {

    // thuộc tính để tìm kiếm
    @Schema(allowableValues = {"username","password"},defaultValue = "[\"username\",\"password\"]")
    private String[] property;


}
