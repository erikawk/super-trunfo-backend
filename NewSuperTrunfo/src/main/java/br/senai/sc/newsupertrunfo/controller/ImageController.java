package br.senai.sc.newsupertrunfo.controller;

import br.senai.sc.newsupertrunfo.model.dto.ImageDTO;
import br.senai.sc.newsupertrunfo.model.entity.Image;
import br.senai.sc.newsupertrunfo.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/img")
@Controller
@CrossOrigin
public class ImageController {

    private ImageService imageService;

    @PostMapping("/post")
    public ResponseEntity<Image> addImage(@RequestParam(name = "file") MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageDTO imageDTO = new ImageDTO(UUID.randomUUID().toString(), file.getName());
        Image image = new Image();
        BeanUtils.copyProperties(imageDTO, image);

        try {
            return ResponseEntity.ok(imageService.addImage(image, file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<URL>> findImages() {
        return ResponseEntity.ok(imageService.findImages());
    }

    @GetMapping("/get/{keyName}")
    public ResponseEntity<URL> findImage(@PathVariable String keyName) {
        return ResponseEntity.ok(imageService.findUrl(keyName));
    }


}
