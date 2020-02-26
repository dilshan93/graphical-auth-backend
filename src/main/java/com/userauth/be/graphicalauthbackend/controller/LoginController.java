package com.userauth.be.graphicalauthbackend.controller;

import com.userauth.be.graphicalauthbackend.dto.LoginUserDTO;
import com.userauth.be.graphicalauthbackend.dto.UserDTO;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import com.userauth.be.graphicalauthbackend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOS = loginService.getAllUserDetails();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping("/Save")
    public ResponseEntity<UserRegister> saveUser(@Valid @RequestBody UserDTO userDTO){
        loginService.createUser(userDTO);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/getUserLogin")
    public ResponseEntity<UserRegister> getLoginUser(@Valid @RequestBody LoginUserDTO loginUserDTO){
        UserRegister userRegister = loginService.getUser(loginUserDTO);
        return new ResponseEntity<>(userRegister, HttpStatus.OK);
    }
}
