package br.senai.sc.newsupertrunfo.controller;

import br.senai.sc.newsupertrunfo.model.dto.ImageDTO;
import br.senai.sc.newsupertrunfo.model.entity.Image;
import br.senai.sc.newsupertrunfo.service.ImageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping("/img")
@Controller
@CrossOrigin
public class ImageController {

    private static String accessKey;
    private static String secretKey;
    private ImageService imageService;

    @Value("${secretKey}")
    public void setup(String secretKey) {
        ImageController.secretKey = secretKey;
    }

    @Value("${accessKey}")
    public void setup2(String accessKey) {
        ImageController.accessKey = accessKey;
    }

    @PostMapping("/post")
    public ResponseEntity<Image> addImage(@RequestBody MultipartFile file) {

        try {
            String bucketName = "bucket-romario";
            String keyName = "imageTest";

            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

            ImageDTO imageDTO = new ImageDTO(UUID.randomUUID().toString(), keyName);
            Image image = new Image();
            BeanUtils.copyProperties(imageDTO, image);
            imageService.addImage(image);

            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<String>> findImages() {
        List<String> imageUrls = imageService.findImages().stream()
                .map(image -> generatePresignedUrl(image.getKeyName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(imageUrls);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<String> findImage(@PathVariable String keyName){
        return ResponseEntity.ok(generatePresignedUrl(keyName));
    }

    private String generatePresignedUrl(String keyName) {
        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();
            String bucketName = "bucket-romario";

            if (amazonS3Client.doesBucketExist(bucketName)) {
                URL url = amazonS3Client.generatePresignedUrl(bucketName, keyName, DateTime
                        .now()
                        .plusDays(1)
                        .toDate());
                return url.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
