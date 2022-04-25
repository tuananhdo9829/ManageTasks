package com.tuananhdo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUlti {

    public static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUlti.class);

    public static void saveFileDir(String uploadDir, String fileName, MultipartFile multipartFile) {
        Path path = Paths.get(uploadDir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        } catch (IOException e) {
            LOGGER.info("Could not create directory file :" + path);
        }
        try (InputStream inputStream = multipartFile.getInputStream();) {
            Path pathFile = path.resolve(fileName);
            Files.copy(inputStream, pathFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.info("Could not save file :" + fileName);
        }
    }

    public static void cleanFileDir(String dir) {
        Path pathDir = Paths.get(dir);

        try {
            Files.list(pathDir).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        LOGGER.info("Could not delete file :" + file);
                    }
                }
            });
        } catch (IOException e) {
            LOGGER.info("Could not list file :" + pathDir);
        }
    }

    public static void deleteFileDir(String dirFile) {
        cleanFileDir(dirFile);
        try {
            Files.delete(Paths.get(dirFile));
        } catch (Exception exception) {
            LOGGER.info("Could not delete file :" + dirFile);

        }
    }
}
