package controllers;

import entities.Humano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.HumanoServiceImpl;

@RestController
@RequestMapping("/api/humanos")
public class HumanoControllerImpl implements HumanoController {
    @Autowired
    private HumanoServiceImpl humanoServiceImpl;

    @Override
    @PostMapping("/mutant")
    public ResponseEntity<?> verificarMutante(@RequestBody Humano humano) {
        // Convierte el ADN de String a un arreglo, asumiendo que se envía como un solo String
        String[] dnaArray = humano.getDNA().split(",");
        boolean esMutante = humanoServiceImpl.isMutant(dnaArray);

        if (esMutante) {
            return new ResponseEntity<>("Es mutante", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No es mutante", HttpStatus.FORBIDDEN);
        }
    }
}
