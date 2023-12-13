package ch.mse.image_archive.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.mse.image_archive.model.Image;
import ch.mse.image_archive.repository.ImageRepository;

@Controller
public class ImageController {

    @Autowired
    ImageRepository imgRepo;

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

    @PostMapping("/images{path}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, SerialException, SQLException {
        Image newImage = new Image();

        InputStream in = file.getInputStream();
        Blob imgContent = new SerialBlob(in.readAllBytes());
        newImage.setContent(imgContent);

        imgRepo.save(newImage);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
