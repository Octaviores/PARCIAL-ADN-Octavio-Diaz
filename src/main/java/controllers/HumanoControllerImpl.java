package controllers;



import entities.Humano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.HumanoService;

@RestController
@RequestMapping("/api/humanos")
public class HumanoControllerImpl implements HumanoController{

    @Autowired
    private HumanoService humanoService;


    @PostMapping("/mutant")
    @Override
    public ResponseEntity<String> verificarMutante(@RequestBody Humano humano) {
        try {
            // Convertir el atributo DNA de tipo String a un arreglo de cadenas
            String[] ADN = humano.getDNA().split(",");

            // Verificar si el ADN es mutante
            if (humanoService.isMutant(ADN)) {
                return new ResponseEntity<>("Es mutante", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No es mutante", HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    }

