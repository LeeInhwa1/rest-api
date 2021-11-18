package com.example.employee.management.test.service;

import com.example.employee.management.test.model.Authentication;
import com.example.employee.management.test.model.User;

import java.util.List;

public interface MariaService {

    User getUserById(Long id);

    List<Authentication> getAuthentications();
}
