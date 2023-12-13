package ch.mse.image_archive.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping(value = "/static_images/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Cacheable("static_images")
    public byte[] static_image(@PathVariable String path) throws IOException {
        System.out.println("File is loaded from actual ressources folder");
        File imageFile = new ClassPathResource("images/" + path).getFile();
        try (InputStream in = new FileInputStream(imageFile)) {
            return in.readAllBytes();
        }
    }

    @ResponseBody
    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Cacheable("images")
    public byte[] image(@PathVariable int id) throws IOException, SQLException {
        System.out.println("File is loaded from actual ressources folder");
        Optional<Image> img = imgRepo.findById(id);
        try (InputStream in = img.get().getContent().getBinaryStream()) {
            return in.readAllBytes();
        }
    }

    @PostMapping("/images")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
            throws IOException, SerialException, SQLException {
        Image newImage = new Image();

        InputStream in = file.getInputStream();
        Blob imgContent = new SerialBlob(in.readAllBytes());
        newImage.setContent(imgContent);

        imgRepo.save(newImage);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @GetMapping("/images")
    public String listUploadedFiles(Model model) throws IOException {
        List<String> ids = StreamSupport.stream(imgRepo.findAll().spliterator(), false)
                .map(img -> "images/" + img.getId())
                .collect(Collectors.toList());

        model.addAttribute("files", ids);

        return "uploadForm";
    }
}
