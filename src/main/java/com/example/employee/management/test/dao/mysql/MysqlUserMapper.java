package com.example.employee.management.test.dao.mysql;

import com.example.employee.management.test.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MysqlUserMapper {

    User getUserById(Long id);
}
