package com.userauth.be.graphicalauthbackend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.util.Set;

@Data
public class UserDTO {

//    private Integer id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 12, max = 300)
    private String passWord;

//    private String passImag;

    private String passImag;

    private Set<String> userRoles;


}
