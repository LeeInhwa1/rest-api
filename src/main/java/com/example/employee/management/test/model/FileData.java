package com.example.employee.management.test.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileData {

    /**
     * 파일명
     */
    private String filename;

    /**
     * 파일 URL
     */
    private String url;

    /**
     * 파일 사이즈
     */
    private Long size;
}
