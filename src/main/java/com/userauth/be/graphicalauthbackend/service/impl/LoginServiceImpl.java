package com.userauth.be.graphicalauthbackend.service.impl;

import com.userauth.be.graphicalauthbackend.dto.LoginUserDTO;
import com.userauth.be.graphicalauthbackend.dto.UserDTO;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import com.userauth.be.graphicalauthbackend.repository.UserRepository;
import com.userauth.be.graphicalauthbackend.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserDTO> getAllUserDetails() {


        List<UserRegister> userRegisters = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (UserRegister userRegister : userRegisters){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userRegister, userDTO);
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public void createUser(UserDTO userDTO) {

        UserRegister userRegister = new UserRegister();
        userRegister.setUserName(userDTO.getUserName());
        userRegister.setEmail(userDTO.getEmail());
        userRegister.setFirstName(userDTO.getFirstName());
        userRegister.setLastName(userDTO.getLastName());
        userRegister.setPassWord(userDTO.getPassWord());
        userRepository.save(userRegister);

    }

    @Override
    public UserRegister getUser(LoginUserDTO loginUserDTO) {

        UserRegister userRegister = userRepository.findAllByUserNameAndPassWord(loginUserDTO.getUserName(), loginUserDTO.getPassWord());
        if(userRegister != null){

           return userRegister;
        } else {
            return null;
        }

    }


}
