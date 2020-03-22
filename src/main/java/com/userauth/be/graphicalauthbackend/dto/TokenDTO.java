package com.userauth.be.graphicalauthbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class TokenDTO {

    private Long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String token;
    private String type = "Access";
    private List<String> roles;

    public TokenDTO(Long id, String userName, String email, String firstName, String lastName, String token, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.roles = roles;
    }
}
