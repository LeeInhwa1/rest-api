package com.example.employee.management.test.controller;

import com.example.employee.management.test.model.FileData;
import com.example.employee.management.test.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "2. 파일 테스트")
@RestController
@RequestMapping("file")
public class FileController {

    private final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    /**
     * 파일 업로드
     *
     * @param file
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @ApiOperation(value = "파일 업로드")
    @PostMapping(value = "upload")
    public ResponseEntity<String> upload(MultipartFile file) throws IllegalStateException, IOException {
        fileService.store(file);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * 파일 다운로드
     *
     * @param filename
     * @return
     */
    @ApiOperation(value = "파일 다운로드")
    @GetMapping(value = "download")
    public ResponseEntity<Resource> serveFile(@RequestParam(value = "filename") String filename) {

        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * 파일 전체 삭제
     *
     * @return
     */
    @ApiOperation(value = "파일 전체 삭제")
    @PostMapping(value = "delete/all")
    public ResponseEntity<String> deleteAll() {
        fileService.deleteAll();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * 파일 삭제
     *
     * @return
     */
    @ApiOperation(value = "파일 단건 삭제")
    @PostMapping(value = "delete/file")
    public ResponseEntity<String> deleteFile(@RequestParam(value = "filename") String filename) {
        fileService.delete(filename);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * 파일 출력
     * @return
     */
    @ApiOperation(value = "파일 리스트 출력")
    @GetMapping("fileList")
    public ResponseEntity<List<FileData>> getListFiles() {
        List<FileData> fileInfos = fileService.loadAll()
                .map(path ->{
                    FileData data = new FileData();
                    String filename = path.getFileName().toString();
                    data.setFilename(filename);
                    data.setUrl(MvcUriComponentsBuilder.fromMethodName(FileController.class,
                            "serveFile", filename).build().toString());
                    try {
                        data.setSize(Files.size(path));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                    return data;
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


}
