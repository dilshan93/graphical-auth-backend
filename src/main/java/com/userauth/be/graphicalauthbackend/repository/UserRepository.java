package com.userauth.be.graphicalauthbackend.repository;

import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRegister, Long>, JpaSpecificationExecutor<UserRegister> {

//    UserRegister findAllByUserNameAndPassWord(String userName, String Password);
//    UserRegister findByUserName(String userName);

    Optional<UserRegister> findByuserName(String username);

    Boolean existsByuserName(String username);

    Boolean existsByEmail(String email);

    UserRegister findByUserName(String username);
}
