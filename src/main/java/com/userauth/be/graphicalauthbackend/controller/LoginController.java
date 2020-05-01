package com.userauth.be.graphicalauthbackend.controller;

import com.userauth.be.graphicalauthbackend.dto.BackendMessage;
import com.userauth.be.graphicalauthbackend.dto.LoginUserDTO;
import com.userauth.be.graphicalauthbackend.dto.TokenDTO;
import com.userauth.be.graphicalauthbackend.dto.UserDTO;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import com.userauth.be.graphicalauthbackend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/Save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO){
        BackendMessage backendMessage = loginService.createUser(userDTO);
        return ResponseEntity.ok(backendMessage);
    }

    @PostMapping("/getUserLogin")
    public ResponseEntity<?> getLoginUser(@Valid @RequestBody LoginUserDTO loginUserDTO){
        TokenDTO tokenDTO = loginService.getUser(loginUserDTO);
        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping(path = { "/get/{username}" })
    public String getImage(@PathVariable("username") String userName) throws SQLException {

        String img = loginService.getImage(userName);
        return img;
    }
}
