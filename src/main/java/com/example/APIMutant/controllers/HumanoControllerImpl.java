package com.example.APIMutant.controllers;

import com.example.APIMutant.DTOs.HumanoRequest;
import com.example.APIMutant.DTOs.HumanoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.APIMutant.services.HumanoService;

import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/humanos")
public class HumanoControllerImpl {


    private HumanoService humanoService;

    public HumanoControllerImpl (HumanoService humanoService){
        this.humanoService = humanoService;
    }

    @PostMapping("/mutant")
    public ResponseEntity<HumanoResponse> verificarMutante(@RequestBody HumanoRequest request) {
        System.out.println("Cuerpo de la solicitud: " + request);
        System.out.println("DNA recibido: " + Arrays.toString(request.getDna()));
        try {
            // Pasar el objeto HumanoRequest al servicio
            HumanoResponse response = humanoService.isMutant(request);
            return new ResponseEntity<>(response, response.isMutant() ? HttpStatus.OK : HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new HumanoResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


}
