package ch.mse.image_archive.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {
    @ResponseBody
    @RequestMapping(value = "/images/{path}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @Cacheable("images")
    public byte[] image(@PathVariable String path) throws IOException {
        System.out.println("File is loaded from actual ressources folder");
        File imageFile = new ClassPathResource("images/" + path).getFile();
        try (InputStream in = new FileInputStream(imageFile)) {
            return in.readAllBytes();
        }
    }
}
