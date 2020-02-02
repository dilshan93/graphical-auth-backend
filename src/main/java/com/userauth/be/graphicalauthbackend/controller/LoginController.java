package com.userauth.be.graphicalauthbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/getAll")
    public ResponseEntity<List<String>> getAllUsers(){
        List<String> newList = new ArrayList<>();
        newList.add("Dilshan");
        newList.add("Sahan");
        newList.add("Dulitha");
        return new ResponseEntity<>(newList, HttpStatus.OK);
    }
}
