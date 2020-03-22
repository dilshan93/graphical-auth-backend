package com.userauth.be.graphicalauthbackend.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String passWord;
}
