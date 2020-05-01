package com.userauth.be.graphicalauthbackend.service;

import com.userauth.be.graphicalauthbackend.dto.BackendMessage;
import com.userauth.be.graphicalauthbackend.dto.LoginUserDTO;
import com.userauth.be.graphicalauthbackend.dto.TokenDTO;
import com.userauth.be.graphicalauthbackend.dto.UserDTO;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface LoginService {

    BackendMessage createUser(UserDTO userDTO);

    TokenDTO getUser(LoginUserDTO loginUserDTO);

    String getImage(String username) throws SQLException;

}
