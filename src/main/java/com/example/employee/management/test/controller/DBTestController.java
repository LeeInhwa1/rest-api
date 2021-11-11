package com.example.employee.management.test.controller;

import com.example.employee.management.test.model.User;
import com.example.employee.management.test.service.MariaService;
import com.example.employee.management.test.service.MysqlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "3. DB 연동 테스트")
@RestController
@RequestMapping(value = "db")
public class DBTestController {
    private final Logger LOGGER = LoggerFactory.getLogger(DBTestController.class);


    @Autowired
    private MysqlService mysqlService;

    @Autowired
    private MariaService mariaService;

    @ApiOperation(value = "MySQL 테스트")
    @GetMapping(value = "mysql/get/user/id")
    public User getMysqlUserById(Long id) {
        return mysqlService.getUserById(id);
    }

    @ApiOperation(value = "MariaDB 테스트")
    @GetMapping(value = "maria/get/user/id")
    public User getMariaUserById(Long id) {
        return mariaService.getUserById(id);
    }


}
