package controllers;



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
public class HumanoControllerImpl {

    @Autowired
    private HumanoService humanoService;

    @PostMapping("/mutant")
    public ResponseEntity<String> verificarMutante(@RequestBody String[] ADN) {
        // Devolución del metodo isMutant
        try {
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
