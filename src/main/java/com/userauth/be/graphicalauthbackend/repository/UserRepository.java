package com.userauth.be.graphicalauthbackend.repository;

import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserRegister, Long>, JpaSpecificationExecutor<UserRegister> {

}
