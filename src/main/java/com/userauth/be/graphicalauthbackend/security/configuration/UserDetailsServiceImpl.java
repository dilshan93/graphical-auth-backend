package com.userauth.be.graphicalauthbackend.security.configuration;

import com.userauth.be.graphicalauthbackend.entity.UserRegister;
import com.userauth.be.graphicalauthbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserRegister userRegister = userRepository.findByuserName(s).orElseThrow(() -> new UsernameNotFoundException("Not Found : "+ s));
        return UserDetailsImpl.build(userRegister);
    }
}
