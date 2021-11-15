package com.example.employee.management.test.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUserDetailsService {

    UserDetails loadUserByUsername(String username);
}
