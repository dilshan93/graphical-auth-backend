package com.userauth.be.graphicalauthbackend.service.impl;

import com.userauth.be.graphicalauthbackend.dto.BackendMessage;
import com.userauth.be.graphicalauthbackend.dto.LoginUserDTO;
import com.userauth.be.graphicalauthbackend.dto.TokenDTO;
import com.userauth.be.graphicalauthbackend.dto.UserDTO;
import com.userauth.be.graphicalauthbackend.entity.Role;
import com.userauth.be.graphicalauthbackend.entity.RoleType;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import com.userauth.be.graphicalauthbackend.repository.RoleRepository;
import com.userauth.be.graphicalauthbackend.repository.UserRepository;
import com.userauth.be.graphicalauthbackend.security.configuration.UserDetailsImpl;
import com.userauth.be.graphicalauthbackend.security.util.JwtUtils;
import com.userauth.be.graphicalauthbackend.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Transactional
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public BackendMessage createUser(UserDTO userDTO) {

        if (userRepository.existsByuserName(userDTO.getUserName())){
            return new BackendMessage("User Already Exsist");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())){
            return new BackendMessage("User Already Exsist");
        }

        UserRegister userRegister = new UserRegister();
        userRegister.setUserName(userDTO.getUserName());
        userRegister.setEmail(userDTO.getEmail());
        userRegister.setFirstName(userDTO.getFirstName());
        userRegister.setLastName(userDTO.getLastName());
        userRegister.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));

        Set<String> stringSet = userDTO.getUserRoles();
        Set<Role> roleSet = new HashSet<>();
        if (stringSet == null){
            Role role = roleRepository.findByName(RoleType.ROLE_USER).orElseThrow(() -> new RuntimeException("There's no such a Role"));
            roleSet.add(role);
        }else{
            stringSet.forEach(roles -> {
                switch (roles){
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("There's no such a Role"));
                        roleSet.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleType.ROLE_USER).orElseThrow(() -> new RuntimeException("There's no such a Role"));
                        roleSet.add(userRole);
                }
            });
        }

        userRegister.setRoles(roleSet);
        userRepository.save(userRegister);

        return new BackendMessage("Registered Successfully Complete!");
    }

    @Override
    public TokenDTO getUser(LoginUserDTO loginUserDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDTO.getUserName(),
                        loginUserDTO.getPassWord()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jtoken = jwtUtils.jwtTokengeneration(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> userRoles =userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return new TokenDTO(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getFirstName(), userDetails.getLastName(), jtoken, userRoles);
    }


//    @Override
//    public List<UserDTO> getAllUserDetails() {
//
//
//        List<UserRegister> userRegisters = userRepository.findAll();
//        List<UserDTO> userDTOS = new ArrayList<>();
//        for (UserRegister userRegister : userRegisters){
//            UserDTO userDTO = new UserDTO();
//            BeanUtils.copyProperties(userRegister, userDTO);
//            userDTOS.add(userDTO);
//        }
//
//        return userDTOS;
//    }
//
//    @Override
//    public void createUser(UserDTO userDTO) {
//
//        UserRegister userRegister = new UserRegister();
//        userRegister.setUserName(userDTO.getUserName());
//        userRegister.setEmail(userDTO.getEmail());
//        userRegister.setFirstName(userDTO.getFirstName());
//        userRegister.setLastName(userDTO.getLastName());
//        userRegister.setPassWord(userDTO.getPassWord());
//        userRepository.save(userRegister);
//
//    }
//
//    @Override
//    public UserRegister getUser(LoginUserDTO loginUserDTO) {
//
//        UserRegister userRegister = userRepository.findAllByUserNameAndPassWord(loginUserDTO.getUserName(), loginUserDTO.getPassWord());
//        if(userRegister != null){
//
//           return userRegister;
//        } else {
//            return null;
//        }
//
//    }


}
