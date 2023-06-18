package br.senai.sc.newsupertrunfo.repository;

import br.senai.sc.newsupertrunfo.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {


}
