package com.userauth.be.graphicalauthbackend.service;

import com.userauth.be.graphicalauthbackend.dto.LoginUserDTO;
import com.userauth.be.graphicalauthbackend.dto.UserDTO;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;

import java.util.List;

public interface LoginService {

    List<UserDTO> getAllUserDetails();

    void createUser(UserDTO userDTO);

    UserRegister getUser(LoginUserDTO loginUserDTO);
}
