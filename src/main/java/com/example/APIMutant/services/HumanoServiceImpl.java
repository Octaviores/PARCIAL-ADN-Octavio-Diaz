package com.example.APIMutant.services;

import com.example.APIMutant.Verifications.Verificacion;
import com.example.APIMutant.DTOs.HumanoRequest;
import com.example.APIMutant.DTOs.HumanoResponse;
import com.example.APIMutant.entities.Humano;
import org.springframework.stereotype.Service;
import com.example.APIMutant.respositories.HumanoRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class HumanoServiceImpl implements HumanoService {


    private HumanoRepository humanoRepository;

    public HumanoServiceImpl (HumanoRepository humanoRepository){
        this.humanoRepository = humanoRepository;
    }

    @Override
    public HumanoResponse isMutant(HumanoRequest humanoRequest) {
        System.out.println("Inicio de isMutant");
        String[] ADN = humanoRequest.getDna();

        if (ADN == null) {
            System.out.println("ADN es null");
        } else {
            System.out.println("ADN recibido: " + Arrays.toString(ADN));
        }

        if (!Verificacion.validarADN(ADN)) {  // Valido que no haya inconsistencias
            System.out.println("ADN no válido");
            throw new IllegalArgumentException("El ADN ingresado no es válido.");
        }

        // Verificar si el ADN ya está en la base de datos
        String concatenatedDNA = String.join(",", ADN);
        Optional<Humano> existente = humanoRepository.findByDNA(concatenatedDNA);

        if (existente.isPresent()) {
            boolean esMutante = existente.get().isMutant();
            String mensaje = esMutante ? "Mutante detectado previamente" : "Humano detectado previamente";
            System.out.println("Resultado previamente encontrado en la base de datos");
            return new HumanoResponse(esMutante, mensaje);
        }

        System.out.println("Verificando si es mutante");
        // Verificar si es mutante
        boolean esMutante = verificarMutante(ADN);

        // Guardar en la base de datos el resultado
        Humano humano = new Humano();
        humano.setDNA(ADN);
        humano.setMutant(esMutante);
        humanoRepository.save(humano);

        String mensaje = esMutante ? "Es mutante" : "No es mutante";
        System.out.println("Resultado: " + mensaje);
        return new HumanoResponse(esMutante, mensaje);
    }

    private boolean verificarMutante(String[] ADN) {

        int N = ADN.length;
        StringBuilder unirCadenas = new StringBuilder(); // Concatenar todas las cadenas de ADN en un solo StringBuilder
        Set<Character> letrasEncontradas = new HashSet<>();  // Para verificar si encontré 2 secuencias distintas

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

                // Imprimir la secuencia encontrada
                System.out.println("Coincidencia en fila: " + letra + " en posiciones: " + (f - 3) + ", " + (f - 2) + ", " + (f - 1) + ", " + f);

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

                // Imprimir la secuencia encontrada
                System.out.println("Coincidencia en columna: " + letra + " en posiciones: " + (c) + ", " + (c + N) + ", " + (c + N * 2) + ", " + (c + N * 3));

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

                // Imprimir la secuencia encontrada
                System.out.println("Coincidencia diagonal (izquierda a derecha): " + letra + " en posiciones: " +
                        d + ", " + (d + N + 1) + ", " + (d + (N * 2) + 2) + ", " + (d + (N * 3) + 3));

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

                // Imprimir la secuencia encontrada
                System.out.println("Coincidencia diagonal (derecha a izquierda): " + letra + " en posiciones: " +
                        d + ", " + (d + N - 1) + ", " + (d + (N * 2) - 2) + ", " + (d + (N * 3) - 3));

                letrasEncontradas.add(letra);
                if (letrasEncontradas.size() >= 2) {
                    return true; // Dos coincidencias con letras distintas encontradas
                }
            }
            d++;
        } while (d < unirCadenas.length());

        return false; // Si el Hash nunca tuvo un tamaño mayor a 2, entonces nunca hubo coincidencias suficientes.
    }

}