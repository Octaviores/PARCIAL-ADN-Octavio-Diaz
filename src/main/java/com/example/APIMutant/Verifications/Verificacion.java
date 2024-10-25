package com.example.APIMutant.Verifications;

public class Verificacion {
    public static boolean validarADN(String[] ADN) {
        if (ADN == null) {
            System.out.println("ADN es null");
            return false;
        }

        int N = ADN.length;
        System.out.println("Longitud del array ADN: " + N);

        if (N == 0) {
            System.out.println("Array vacío");
            return false;
        }

        for (String cadena : ADN) {
            System.out.println("Cadena actual: " + cadena);
            if (cadena == null || cadena.length() != N) {
                System.out.println("Cadena inválida: longitud incorrecta o null");
                return false;
            }

            for (char letra : cadena.toCharArray()) {
                if ("ATCG".indexOf(letra) == -1) {
                    System.out.println("Letra no permitida encontrada: " + letra);
                    return false;
                }
            }
        }

        return true;
    }
}