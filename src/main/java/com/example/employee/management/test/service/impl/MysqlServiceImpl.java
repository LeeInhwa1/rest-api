package com.example.employee.management.test.service.impl;

import com.example.employee.management.test.dao.mysql.MysqlUserMapper;
import com.example.employee.management.test.model.User;
import com.example.employee.management.test.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlServiceImpl implements MysqlService {

    @Autowired
    private MysqlUserMapper mysqlUserMapper;

    @Override
    public User getUserById(Long id) {
        return mysqlUserMapper.getUserById(id);
    }
}
