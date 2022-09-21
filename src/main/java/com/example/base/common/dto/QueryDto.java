package com.example.base.common.dto;

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
    @Schema(example = "1")
    int pageSize;

    @Schema(example = "2")
    int pageNumber;

    @Schema(allowableValues = {"username","password"},defaultValue = "[\"username\",\"password\"]")
    private String[] property;

    @Schema()
    private String[] sort;


    @Schema(example = "im")
    private String search;

//    @Parameter(hidden = true)
    private String filter;

    @Parameter(hidden = true)
    private Map<String,Object> filters;


    public void setFilters() throws JsonProcessingException {
        if(this.filter!=null && this.filter.length()>0) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.filters = objectMapper.readValue(filter, Map.class);
        }
    }
}

