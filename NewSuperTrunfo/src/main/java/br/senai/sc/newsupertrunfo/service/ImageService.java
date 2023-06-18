package br.senai.sc.newsupertrunfo.service;

import br.senai.sc.newsupertrunfo.controller.ImageController;
import br.senai.sc.newsupertrunfo.model.dto.ImageDTO;
import br.senai.sc.newsupertrunfo.model.entity.Image;
import br.senai.sc.newsupertrunfo.repository.ImageRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {

    private static String accessKey;
    private static String secretKey;
    @Value("${secretKey}")
    public void setup(String secretKey) {
        ImageService.secretKey = secretKey;
    }

    @Value("${accessKey}")
    public void setup2(String accessKey) {
        ImageService.accessKey = accessKey;
    }

    private ImageRepository imageRepository;

    public Image addImage(Image image, File file){
        try {
            String bucketName = "bucket-romario";

            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            amazonS3Client.putObject(new PutObjectRequest(bucketName, file.getName(), file));
            return imageRepository.save(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException();
    }

    private URL generatePresignedUrl(String keyName) {
        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();
            String bucketName = "bucket-romario";

            if (amazonS3Client.doesBucketExist(bucketName)) {
                System.out.println(keyName);
                return amazonS3Client.generatePresignedUrl(bucketName, keyName, DateTime
                        .now()
                        .plusDays(1)
                        .toDate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<URL> findImages() {
        List<URL> imageUrls = imageRepository.findAll().stream()
                .map(image -> generatePresignedUrl(image.getKeyName()))
                .collect(Collectors.toList());

        return imageUrls;
    }

    public URL findImage(String keyName){
        return generatePresignedUrl(keyName);
    }

    public Image findOne(String keyName){
        return imageRepository.findById(keyName).orElseThrow();
    }

}
