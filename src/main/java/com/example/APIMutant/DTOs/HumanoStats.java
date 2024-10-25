package com.example.APIMutant.DTOs;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class HumanoStats {
    private int count_mutant_dna;
    private int count_human_dna;
    private float ratio;
}
