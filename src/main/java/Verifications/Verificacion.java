package Verifications;

public class Verificacion {
    public static boolean validarADN(String[] ADN) {
        if (ADN == null) {
            return false; // En caso de Recibir un Array null
        }

        int N = ADN.length;

        if (N == 0) {
            return false; // En caso Recibir un array vacío
        }

        for (String cadena : ADN) {
            if (cadena == null || cadena.length() != N) {
                return false; // En caso de Recibir un array de NxM en vez de NxN o tecibir un array de NxN de nulls
            }

            // Aca se verifican que no se ingresen letras diferntes de "A, T, C, G"
            for (char letra : cadena.toCharArray()) {
                if ("ATCG".indexOf(letra) == -1) {
                    return false; // Letras no permitidas encontradas
                }
            }
        }

        return true; // El ADN es válido
    }
}
