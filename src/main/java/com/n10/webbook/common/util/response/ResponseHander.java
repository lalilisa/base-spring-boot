package com.n10.webbook.common.util.response;

import com.n10.webbook.common.dto.FormResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//Cusom ResponseEntity
public class ResponseHander {

    public  static ResponseEntity<Object> response(Object data,HttpStatus status){


        return  new ResponseEntity<Object>(new FormResponse(true,data,null), status);
    }
}
