package com.userauth.be.graphicalauthbackend.security.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String passWord;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String userName, String email, String firstName, String lastName, String passWord, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passWord = passWord;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UserRegister userRegister){

        List<GrantedAuthority> authorities =userRegister.getRoles().stream().map(value -> new SimpleGrantedAuthority(value.getName().name())).collect(Collectors.toList());
        return new UserDetailsImpl(userRegister.getId(),userRegister.getUserName(), userRegister.getEmail(), userRegister.getFirstName(), userRegister.getLastName(),
                userRegister.getPassWord(), authorities);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id);
    }

}
