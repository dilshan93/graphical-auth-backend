package com.userauth.be.graphicalauthbackend.service;

import com.userauth.be.graphicalauthbackend.dto.BackendMessage;
import com.userauth.be.graphicalauthbackend.dto.LoginUserDTO;
import com.userauth.be.graphicalauthbackend.dto.TokenDTO;
import com.userauth.be.graphicalauthbackend.dto.UserDTO;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;

import java.util.List;

public interface LoginService {

    BackendMessage createUser(UserDTO userDTO);

    TokenDTO getUser(LoginUserDTO loginUserDTO);

}
