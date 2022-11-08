package com.n10.webbook.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Convert;
import java.util.Map;


@Data
@NoArgsConstructor
public class QueryDto {
    // số lượng data tối đa 1 trang
    @Schema(example = "1")
    int pageSize;

    // trang thứ n
    @Schema(example = "2")
    int pageNumber;


    // thuộc tính để tìm kiếm
    private String[] property;

    // danh sách thuộc tính để sắp xếp
    @Schema()
    private String[] sort;

    // keyword tìm kiếm
    @Schema(example = "im")
    private String search;

    //filter truyền vào 1 chuỗi json string  VD: {"username":"trimai"}
//    @Parameter(hidden = true)
    private String filter;

    //Chuyển filter thành Map
    @Parameter(hidden = true)
    private Map<String,Object> filters;

    //convert string filter sang json
    public void setFilters() throws JsonProcessingException {
        if(this.filter!=null && this.filter.length()>0) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.filters = objectMapper.readValue(filter, Map.class);
        }
    }
}

