package com.example.APIMutant.services;


import com.example.APIMutant.DTOs.HumanoStats;
import com.example.APIMutant.respositories.HumanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatsService {

    @Autowired
    private HumanoRepository humanoRepository;

    public HumanoStats getStats() throws Exception {
        try {
            int mutantQuantity = humanoRepository.countByIsMutant(true);
            int humansQuantity = humanoRepository.countByIsMutant(false);
            float ratio = humansQuantity > 0 ? (float) mutantQuantity / humansQuantity : 0;

            System.out.println("Cantidad Humanos: " + humansQuantity);
            System.out.println("Cantidad Mutantes: " + mutantQuantity);
            System.out.println("Ratio: " + ratio);

            HumanoStats humanoStatsDto = new HumanoStats();
            humanoStatsDto.setCount_mutant_dna(mutantQuantity);
            humanoStatsDto.setCount_human_dna(humansQuantity);
            humanoStatsDto.setRatio(ratio);
            return humanoStatsDto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
