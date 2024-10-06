package controllers;

import entities.Humano;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface HumanoController {
    public ResponseEntity<?> verificarMutante(@RequestBody Humano humano);
}
