package com.example.employee.management.test.dao.maria;

import com.example.employee.management.test.model.Authentication;
import com.example.employee.management.test.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MariaUserMapper {

    User getUserById(Long id);

    List<Authentication> getAuthentications();


}
