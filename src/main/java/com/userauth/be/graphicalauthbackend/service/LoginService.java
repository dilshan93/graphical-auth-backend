package com.userauth.be.graphicalauthbackend.service;

import com.userauth.be.graphicalauthbackend.dto.UserDTO;

import java.util.List;

public interface LoginService {

    List<UserDTO> getAllUserDetails();

    void createUser(UserDTO userDTO);
}
