package com.example.APIMutant.services;

import com.example.APIMutant.Verifications.Verificacion;
import com.example.APIMutant.DTOs.HumanoRequest;
import com.example.APIMutant.DTOs.HumanoResponse;
import com.example.APIMutant.entities.Humano;
import org.springframework.stereotype.Service;
import com.example.APIMutant.repositories.HumanoRepository;

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

        int totalCoincidencias = 0;

        // Verificar coincidencias en filas
        totalCoincidencias += buscarFilas(unirCadenas, letrasEncontradas);

        // Verificar coincidencias en columnas
        totalCoincidencias += buscarColumnas(unirCadenas, letrasEncontradas, N);

        // Verificar coincidencias en diagonales
        totalCoincidencias += buscarDiagonalIzquierda(unirCadenas, letrasEncontradas, N);
        totalCoincidencias += buscarDiagonalDerecha(unirCadenas, letrasEncontradas, N);

        return totalCoincidencias >= 2; // Si encontraste al menos dos coincidencias, es un mutante
    }

    // Metodo para buscar coincidencias en filas
    private int buscarFilas(StringBuilder unirCadenas, Set<Character> letrasEncontradas) {
        int coincidencias = 0;
        int f = 0;

        do {
            char letra = unirCadenas.charAt(f);
            if (f > 2 &&
                    letra == unirCadenas.charAt(f - 1) &&
                    letra == unirCadenas.charAt(f - 2) &&
                    letra == unirCadenas.charAt(f - 3)) {

                // Imprimir la secuencia encontrada
                System.out.println("Coincidencia en fila: " + letra + " en posiciones: " + (f - 3) + ", " + (f - 2) + ", " + (f - 1) + ", " + f);

                letrasEncontradas.add(letra);
                coincidencias++; // Incrementar el conteo de coincidencias
                f += 3; // Saltar a la siguiente posición relevante
            }
            f++;
        } while (f < unirCadenas.length());

        return coincidencias; // Devolver el total de coincidencias encontradas
    }

    // Metodo para buscar coincidencias en columnas
    private int buscarColumnas(StringBuilder unirCadenas, Set<Character> letrasEncontradas, int N) {
        int coincidencias = 0;
        int c = 0;
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
                coincidencias++; // Incrementar el conteo de coincidencias
                c += N * 4; // Saltar a la siguiente posición relevante
            } else {
                c += N; // Moverse a la siguiente fila en la misma columna
            }
            if (c >= unirCadenas.length()) {
                cambioColumna++;
                c = cambioColumna; // Reiniciar c para la siguiente columna
            }
        } while (cambioColumna < N);

        return coincidencias; // Devolver el total de coincidencias encontradas
    }

    // Metodo para buscar coincidencias en diagonales de izquierda a derecha
    private int buscarDiagonalIzquierda(StringBuilder unirCadenas, Set<Character> letrasEncontradas, int N) {
        int coincidencias = 0;
        int d = 0;

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
                coincidencias++; // Incrementar el conteo de coincidencias
            }
            d++;
        } while (d < unirCadenas.length());

        return coincidencias; // Devolver el total de coincidencias encontradas
    }

    // Metodo para buscar coincidencias en diagonales de derecha a izquierda
    private int buscarDiagonalDerecha(StringBuilder unirCadenas, Set<Character> letrasEncontradas, int N) {
        int coincidencias = 0;
        int d = 0;

        do {
            char letra = unirCadenas.charAt(d);
            if (d <= unirCadenas.length() - (N * 4) &&
                    d % N >= 3 &&
                    letra == unirCadenas.charAt(d + N - 1) &&
                    letra == unirCadenas.charAt(d + (N * 2) - 2) &&
                    letra == unirCadenas.charAt(d + (N * 3) - 3)) {

                // Imprimir la secuencia encontrada
                System.out.println("Coincidencia diagonal (derecha a izquierda): " + letra + " en posiciones: " +
                        d + ", " + (d + N - 1) + ", " + (d + (N * 2) - 2) + ", " + (d + (N * 3) - 3));

                letrasEncontradas.add(letra);
                coincidencias++; // Incrementar el conteo de coincidencias
            }
            d++;
        } while (d < unirCadenas.length());

        return coincidencias; // Devolver el total de coincidencias encontradas
    }



}