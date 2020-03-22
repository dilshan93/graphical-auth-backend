package com.userauth.be.graphicalauthbackend.repository;

import com.userauth.be.graphicalauthbackend.entity.Role;
import com.userauth.be.graphicalauthbackend.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
