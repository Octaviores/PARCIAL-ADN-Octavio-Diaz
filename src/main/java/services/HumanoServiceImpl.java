package services;

import entities.Humano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import respositories.HumanoRepository;

@Service
public class HumanoServiceImpl implements HumanoService {

    // Inyectar el repositorio
    @Autowired
    private HumanoRepository humanoRepository;

    @Override
    public boolean isMutant(String[] DNA) {
        int N = DNA.length;
        int TotCoincidencias = 0;  // variable para guardar secuencias

        // UN SOLO For para concatenar las cadenas, no recorrer el ADN
        StringBuilder unirCadenas = new StringBuilder();
        for (String cadena : DNA) {
            unirCadenas.append(cadena);
        }

        // UN SOLO for para recorrer el Array
        for (int i = 0; i < unirCadenas.length(); i++) {
            char letra = unirCadenas.charAt(i);

            // Condicional para filas
            if (i > 2 &&
                    letra == unirCadenas.charAt(i - 1) &&
                    letra == unirCadenas.charAt(i - 2) &&
                    letra == unirCadenas.charAt(i - 3)) {

                TotCoincidencias++;
            }

            // Condicional para diagonal (de izquierda a derecha y de arriba hacia a abajo)
            if (i <= unirCadenas.length() - (N * 4) && // Para que no se vaya fuera del arreglo
                    letra == unirCadenas.charAt(i + N + 1) &&
                    letra == unirCadenas.charAt(i + (N * 2) + 2) &&
                    letra == unirCadenas.charAt(i + (N * 3) + 3)) {

                TotCoincidencias++;
            }

            // Condicional para columnas
            if (i <= unirCadenas.length() - (N * 4) && // Para que no se vaya fuera del arreglo
                    letra == unirCadenas.charAt(i + N) &&
                    letra == unirCadenas.charAt(i + (N * 2)) &&
                    letra == unirCadenas.charAt(i + (N * 3))) {

                TotCoincidencias++;
            }
        }

        Humano humano = new Humano();
        humano.setDNA(unirCadenas.toString());
        humano.setMutant(TotCoincidencias > 1);
        humanoRepository.save(humano);
        // Evaluar si hay más de 1 coincidencia
        return TotCoincidencias > 1; // Devuelve true si hay más de 1 coincidencia
    }
}


