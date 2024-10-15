package services;

import Verifications.Verificacion;
import entities.Humano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import respositories.HumanoRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class HumanoServiceImpl implements HumanoService {

    @Autowired
    private HumanoRepository humanoRepository;

    @Override
    public boolean isMutant(String[] ADN) {
        if (!Verificacion.validarADN(ADN)) {  //valido que no haya inconsistencias
            throw new IllegalArgumentException("El ADN ingresado no es válido.");
        }

        boolean esMutante = verificarMutante(ADN);

        // Guardar en la base de datos el resultado
        Humano humano = new Humano();
        humano.setDNA(ADN);
        humano.setMutant(esMutante);
        humanoRepository.save(humano);

        return esMutante;
    }

    private boolean verificarMutante(String[] ADN) {
        int N = ADN.length;
        StringBuilder unirCadenas = new StringBuilder(); // Concatenar todas las cadenas de ADN en un solo StringBuilder
        Set<Character> letrasEncontradas = new HashSet<>();  //Para verificar si encontré 2 secuencias distintas

        for (String cadena : ADN) {
            unirCadenas.append(cadena);
        }

        int f = 0;
        int c = 0;
        int d = 0;

        // Verificar coincidencias en filas
        do {
            char letra = unirCadenas.charAt(f);
            if (f > 2 &&
                    letra == unirCadenas.charAt(f - 1) &&
                    letra == unirCadenas.charAt(f - 2) &&
                    letra == unirCadenas.charAt(f - 3)) {

                letrasEncontradas.add(letra);
                if (letrasEncontradas.size() >= 2) {
                    return true; // Dos coincidencias con letras distintas encontradas
                }
                f += 3;
            }
            f++;
        } while (f < unirCadenas.length());

        // Verificar coincidencias en columnas
        int cambioColumna = 0;
        do {
            char letra = unirCadenas.charAt(c);
            if (cambioColumna <= N - 1 &&
                    c + (N * 3) < unirCadenas.length() &&
                    letra == unirCadenas.charAt(c + N) &&
                    letra == unirCadenas.charAt(c + (N * 2)) &&
                    letra == unirCadenas.charAt(c + (N * 3))) {

                letrasEncontradas.add(letra);
                if (letrasEncontradas.size() >= 2) {
                    return true; // Dos coincidencias con letras distintas encontradas
                }
                c += N * 4;
            } else {
                c += N;
            }
            if (c >= unirCadenas.length()) {
                cambioColumna++;
                c = cambioColumna;
            }
        } while (cambioColumna < N);

        // Verificar coincidencias en diagonales
        // Diagonales de izquierda a derecha
        do {
            char letra = unirCadenas.charAt(d);
            if (d <= unirCadenas.length() - (N * 4) &&
                    letra == unirCadenas.charAt(d + N + 1) &&
                    letra == unirCadenas.charAt(d + (N * 2) + 2) &&
                    letra == unirCadenas.charAt(d + (N * 3) + 3)) {
                letrasEncontradas.add(letra);
                if (letrasEncontradas.size() >= 2) {
                    return true; // Dos coincidencias con letras distintas encontradas
                }
            }
        // Diagonales de derecha a izquierda
            if (d <= unirCadenas.length() - (N * 4) &&
                    d % N >= 3 &&
                    letra == unirCadenas.charAt(d + N - 1) &&
                    letra == unirCadenas.charAt(d + (N * 2) - 2) &&
                    letra == unirCadenas.charAt(d + (N * 3) - 3)) {
                letrasEncontradas.add(letra);
                if (letrasEncontradas.size() >= 2) {
                    return true; // Dos coincidencias con letras distintas encontradas
                }
            }
            d++;
        } while (d < unirCadenas.length());

        return false; //Si el Hash nunca tuvo un tamaño mayor a 2, entonces nunca hubo coincidencias suficientes.
    }
}