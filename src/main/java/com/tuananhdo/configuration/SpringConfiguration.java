package com.tuananhdo.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class SpringConfiguration implements WebMvcConfigurer {

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        Path userPhotoDir = Paths.get(dirName);
        String userPhotoPath = userPhotoDir.toFile().getAbsolutePath();
        File file = new File("/" + userPhotoPath);
        if (!file.exists()) {
            Files.createDirectories(userPhotoDir);
        }
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + userPhotoPath + "/");
    }
}
