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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


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

        if (userRepository.existsByuserName(userDTO.getUserName())) {
            return new BackendMessage("User Already Exsist");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return new BackendMessage("User Already Exsist");
        }

        UserRegister userRegister = new UserRegister();
        userRegister.setUserName(userDTO.getUserName());
        userRegister.setEmail(userDTO.getEmail());
        userRegister.setFirstName(userDTO.getFirstName());
        userRegister.setLastName(userDTO.getLastName());
        userRegister.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
        try {
            Blob blob = new SerialBlob(userDTO.getPassImag().getBytes());
            userRegister.setData(blob);
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        try {
        //  System.out.println(" with OriginalFilename "+userDTO.getPassImag());
//            System.out.println(" with ContentType "+userDTO.getPassImag().getContentType());
//            System.out.println(" with Bytes "+userDTO.getPassImag().getBytes());
        // System.out.println(" withDi comprss "+decompressBytes(compressBytes(extractBytes(userDTO.getPassImag()))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        Set<String> stringSet = userDTO.getUserRoles();
        Set<Role> roleSet = new HashSet<>();
        if (stringSet == null) {
            Role role = roleRepository.findByName(RoleType.ROLE_USER).orElseThrow(() -> new RuntimeException("There's no such a Role"));
            roleSet.add(role);
        } else {
            stringSet.forEach(roles -> {
                switch (roles) {
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
        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return new TokenDTO(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getFirstName(), userDetails.getLastName(), jtoken, userRoles);
    }

    @Override
    public String getImage(String username) throws SQLException {

        if (username != null) {

            if (!userRepository.existsByuserName(username)) {
                return "nouser";

            }
            UserRegister userregister = userRepository.findByUserName(username);


            Blob blob = userregister.getData();
            byte[] bdata = userregister.getData().getBytes(1, (int) blob.length());
            String data1 = new String(bdata);
            return data1;
//        System.out.println(" with ContentType "+userDetails.getPassImag().getContentType());
        }
        return null;
    }


//    public static byte[] compressBytes(byte[] data) {
//
//        Deflater deflater = new Deflater();
//        deflater.setInput(data);
//        deflater.finish();
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        while (!deflater.finished()) {
//            int count = deflater.deflate(buffer);
//            byteArrayOutputStream.write(buffer, 0, count);
//        }
//        try {
//            byteArrayOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return byteArrayOutputStream.toByteArray();
//    }
//
//    public static byte[] decompressBytes(byte[] data) {
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        try {
//        while (!inflater.finished()) {
//
//                int count = inflater.inflate(buffer);
//                byteArrayOutputStream.write(buffer, 0, count);
//
//        }
//        byteArrayOutputStream.close();
//        } catch (DataFormatException | IOException e) {
//            e.printStackTrace();
//        }
//        return byteArrayOutputStream.toByteArray();
//    }


}
