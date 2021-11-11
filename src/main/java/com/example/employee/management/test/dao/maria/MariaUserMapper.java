package com.example.employee.management.test.dao.maria;

import com.example.employee.management.test.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MariaUserMapper {

    User getUserById(Long id);
}
