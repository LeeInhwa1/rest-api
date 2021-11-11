package com.example.employee.management.test.service.impl;

import com.example.employee.management.test.dao.maria.MariaUserMapper;
import com.example.employee.management.test.model.User;
import com.example.employee.management.test.service.MariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MariaServiceImpl implements MariaService {

    @Autowired
    private MariaUserMapper mariaUserMapper;

    @Override
    public User getUserById(Long id) {
        return mariaUserMapper.getUserById(id);
    }
}
