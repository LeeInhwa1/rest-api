package com.example.employee.management.test.service.impl;

import com.example.employee.management.test.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service("fileService")
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);


    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                Path root = Paths.get(uploadPath);
                if (!Files.exists(root)) {
                    init();
                }

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, root.resolve(file.getOriginalFilename()),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                throw new Exception("ERROR : File is empty.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            Path root = Paths.get(uploadPath);
            return Files.walk(root, 1)
                    .filter(path -> !path.equals(root));
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return Paths.get(uploadPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
    }

    @Override
    public void delete(String filename) {
        FileSystemUtils.deleteRecursively(Paths.get(String.format("%s/%s", uploadPath, filename)).toFile());
    }
}
