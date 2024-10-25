package com.example.APIMutant.services;

import com.example.APIMutant.DTOs.HumanoRequest;
import com.example.APIMutant.DTOs.HumanoResponse;

public interface HumanoService {
    HumanoResponse isMutant(HumanoRequest humanoRequest);
}