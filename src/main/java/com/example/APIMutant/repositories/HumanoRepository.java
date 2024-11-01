package com.example.APIMutant.repositories;


import com.example.APIMutant.entities.Humano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HumanoRepository extends JpaRepository<Humano, Long> {
    Optional<Humano> findByDNA(String DNA);
    int countByIsMutant(Boolean bool);
}
