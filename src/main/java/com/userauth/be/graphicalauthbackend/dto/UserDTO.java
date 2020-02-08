package com.userauth.be.graphicalauthbackend.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String passWord;


}
