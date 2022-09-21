package com.example.base.controller.user;


import com.example.base.common.dto.FormResponse;
import com.example.base.common.dto.QueryDto;
import com.example.base.common.dto.Upload;
import com.example.base.common.util.ConvertDtoToEntity;
import com.example.base.common.util.response.ResponseHander;
import com.example.base.common.util.upload.StorageFileNotFoundException;
import com.example.base.common.util.upload.StorageService;
import com.example.base.dto.QueryUserDto;
import com.example.base.dto.UserDto;
import com.example.base.entity.user.UserEntity;
import com.example.base.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import javax.validation.Valid;

@RestController
@RequestMapping("api")
public class UserContronller {

    private final StorageService storageService;

    @Autowired
    private UserService userService;

    public UserContronller(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("users")
    public ResponseEntity<Object> listUser(@ParameterObject QueryUserDto queryDto) throws JsonProcessingException {
        System.out.println(queryDto);
        return ResponseHander.response(userService.findAlls(queryDto), HttpStatus.OK);
    }
    @PostMapping("users")
    public ResponseEntity<Object> createUser(@Valid  @RequestBody  UserDto userDto) throws JsonProcessingException {
        UserEntity userEntity=ConvertDtoToEntity.map(userDto,UserEntity.class);

        return ResponseHander.response(this.userService.create(userEntity), HttpStatus.OK);
    }
    @PutMapping("users")
    public ResponseEntity<Object> updateUser(@RequestBody UserEntity userEntity) throws JsonProcessingException {

        return  ResponseHander.response(userService.update(userEntity), HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = { "multipart/form-data" })
    public String handleFileUpload(@ModelAttribute Upload upload) {
    try {
        storageService.store(upload.getFile());


        return upload.getFile().getOriginalFilename();
    }
    catch (Exception e){
        System.out.println(e);
        return  e.getMessage();
    }

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable("id") Long id) {
        UserEntity userEntity=this.userService.findOneById(id);
        return ResponseHander.response(userEntity,HttpStatus.OK);

    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") Long id) {
        this.userService.deleleById(id);
        return ResponseHander.response(null,HttpStatus.OK);
    }

}
