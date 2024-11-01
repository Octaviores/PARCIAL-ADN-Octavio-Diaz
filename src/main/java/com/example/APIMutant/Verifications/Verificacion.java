package com.example.APIMutant.Verifications;

public class Verificacion {
    public static boolean validarADN(String[] ADN) {
        if (ADN == null) {
            throw new IllegalArgumentException("El ADN no puede ser null.");
        }

        int N = ADN.length;

        if (N < 4) {
            throw new IllegalArgumentException("La matriz de ADN debe tener al menos 4x4.");
        }

        for (String cadena : ADN) {
            if (cadena == null || cadena.length() != N) {
                throw new IllegalArgumentException("La matriz de ADN debe ser cuadrada NxN.");
            }

            for (char letra : cadena.toCharArray()) {
                if ("ATCG".indexOf(letra) == -1) {
                    throw new IllegalArgumentException("El ADN contiene letras no permitidas.");
                }
            }
        }

        return true;
    }
}
