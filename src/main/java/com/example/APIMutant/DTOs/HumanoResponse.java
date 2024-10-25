package com.example.APIMutant.DTOs;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class HumanoResponse {
    private boolean isMutant;
    private String message;
}