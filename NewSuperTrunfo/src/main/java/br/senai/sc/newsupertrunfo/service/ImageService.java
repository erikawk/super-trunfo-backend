package br.senai.sc.newsupertrunfo.service;

import br.senai.sc.newsupertrunfo.model.entity.Image;
import br.senai.sc.newsupertrunfo.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {

    private ImageRepository imageRepository;

    public Image addImage(Image image){
        return imageRepository.save(image);
    }

    public List<Image> findImages(){
        return imageRepository.findAll();
    }

}
