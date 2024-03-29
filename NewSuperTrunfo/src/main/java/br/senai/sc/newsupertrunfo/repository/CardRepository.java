package br.senai.sc.newsupertrunfo.repository;

import br.senai.sc.newsupertrunfo.model.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    Page<Card> findAll(Pageable pageable);

}