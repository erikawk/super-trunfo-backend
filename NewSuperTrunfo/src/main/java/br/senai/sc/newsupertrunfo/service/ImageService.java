package br.senai.sc.newsupertrunfo.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {

    private static String accessKey;
    private static String secretKey;

    private ImageRepository imageRepository;

    @Value("${secretKey}")
    public void setSecretKey(String secretKey) {
        ImageService.secretKey = secretKey;
    }

    @Value("${accessKey}")
    public void setAccessKey(String accessKey) {
        ImageService.accessKey = accessKey;
    }

    public Image addImage(Image image, File file) {
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

    public List<URL> findImages() {
        return imageRepository.findAll().stream()
                .map(image -> generateUrl(image.getKeyName()))
                .collect(Collectors.toList());
    }

    public URL findUrl(String keyName) {
        return generateUrl(keyName);
    }

    public Image findImg(String keyName) {
        return imageRepository.findById(keyName).orElseThrow();
    }

    private URL generateUrl(String keyName) {
        try {
            String bucketName = "bucket-romario";

            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            if (amazonS3Client.doesBucketExist(bucketName)) {
                System.out.println("KeyName: " + keyName);
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


}
