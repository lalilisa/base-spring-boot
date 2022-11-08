package com.n10.webbook.common.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ConvertDtoToEntity")
public class ConvertDtoToEntity {


    private static ModelMapper modelMapper;
    @Autowired
    public void setModelMapper(ModelMapper modelMapper){
        ConvertDtoToEntity.modelMapper = modelMapper;
    }
    public static <T> T map(Object classDto, Class<T> destinationType){

        return modelMapper.map(classDto,destinationType);
    }
}
