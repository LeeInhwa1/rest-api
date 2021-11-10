package com.example.employee.management.test.controller;

import com.example.employee.management.test.util.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// API 테스트를 위한 컨트롤러
@Api(tags = "1. 테스트 HELLO WORD 출력")
@RestController
public class TestController {

    @Autowired
    private MessageUtils messageUtils;

    // TEST HELLO WORLD 출력
    @ApiOperation(value = "Hello World String 출력")
    @GetMapping(value = "helloworld/string")
    @ResponseBody
    public String helloWorldString() {
       return "helloWorld";
    }

    // MODEL RETURN JSON 출력
    @ApiOperation(value = "Hello World Model 출력")
    @GetMapping(value = "helloworld/json")
    @ResponseBody
    public Hello helloWorldHson() {
        Hello hello = new Hello();
        hello.message = "helloworld";
        return hello;
    }


    //화면 출력 > hellowWorld.ftl
    @ApiOperation(value = "Hello World 페이지 출력")
    @GetMapping(value = "helloworld/page")
    public String helloWorld() {
        return "helloworld";
    }


    //메시지 프로퍼티 확인
    @ApiOperation(value = "message properties 출력")
    @GetMapping(value = "message/test")
    public String messageTest() {
        // ex) messageUtils.getMessage("goods.err.msg", new String[]{String.valueOf(prdId), errorMsg}
        return messageUtils.getMessage("site.count", new String[]{"첫번째"});
    }

    @Getter
    @Setter
    public static class Hello {
        private String message;
    }

}
