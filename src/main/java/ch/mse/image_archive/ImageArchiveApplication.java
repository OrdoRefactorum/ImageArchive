package ch.mse.image_archive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ImageArchiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageArchiveApplication.class, args);
    }
}