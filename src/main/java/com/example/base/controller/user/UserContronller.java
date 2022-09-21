package com.example.base.controller.user;


import com.example.base.common.dto.QueryDto;
import com.example.base.common.dto.Upload;
import com.example.base.common.util.response.ResponseHander;
import com.example.base.common.util.upload.StorageFileNotFoundException;
import com.example.base.common.util.upload.StorageService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ResponseEntity<Object> listUser(@ParameterObject QueryDto queryDto) throws JsonProcessingException {
        System.out.println(queryDto);
        return ResponseHander.response(userService.findAlls(queryDto), HttpStatus.OK);
    }
    @PostMapping("users")
    public ResponseEntity<Object> createUser(@Valid  @RequestBody  UserDto userEntity) throws JsonProcessingException {
        System.out.println(userEntity);
        return ResponseHander.response(userEntity, HttpStatus.OK);
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

    @PostMapping(value = "/s",consumes = "multipart/form-data")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }


}
