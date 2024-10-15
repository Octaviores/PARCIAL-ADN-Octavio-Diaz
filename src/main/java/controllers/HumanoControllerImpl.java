package controllers;



import entities.Humano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.HumanoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/humanos")
public class HumanoControllerImpl implements HumanoController{

    @Autowired
    private HumanoService humanoService;



    @Override
    @PostMapping("/mutant")
    public ResponseEntity<?> verificarMutante(@RequestBody Humano humano) {
        System.out.println("Verificando mutante para: " + humano);
        try {
            // Pasar el arreglo de ADN al servicio
            if (humanoService.isMutant(humano.getDNA())) {
                return new ResponseEntity<>("Es mutante", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No es mutante", HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    }

