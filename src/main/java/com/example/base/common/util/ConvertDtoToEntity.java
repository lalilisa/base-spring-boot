package com.example.base.common.util;

import com.example.base.entity.user.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
