package com.n10.webbook.controller.user;


import com.n10.webbook.cloudinary.CloudinaryService;
import com.n10.webbook.common.dto.Upload;
import com.n10.webbook.common.util.ConvertDtoToEntity;
import com.n10.webbook.common.util.response.ResponseHander;
import com.n10.webbook.common.util.upload.StorageFileNotFoundException;
import com.n10.webbook.common.util.upload.StorageService;
import com.n10.webbook.dto.QueryUserDto;
import com.n10.webbook.dto.UserDto;
import com.n10.webbook.entity.user.UserEntity;
import com.n10.webbook.mail.SendMail;
import com.n10.webbook.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
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


    @Autowired
    private CloudinaryService cloudinaryService;
    @PostMapping(value = "img",consumes = { "multipart/form-data" })
    public ResponseEntity<?> upl(@ModelAttribute Upload upload){


        return ResponseEntity.ok(cloudinaryService.uploadURl(upload.getFile()));
    }

    @Autowired
    private SendMail sendMail;
    @PostMapping("sendmail")
    public ResponseEntity<?> mail() throws MessagingException {

        sendMail.sendMessage("maivantri309@gmail.com","maivantri309@gmail.com","test","Hello");
        return ResponseEntity.ok("success");
    }

}
