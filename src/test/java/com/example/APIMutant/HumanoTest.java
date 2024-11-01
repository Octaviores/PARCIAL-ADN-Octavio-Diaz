package com.example.APIMutant;

import com.example.APIMutant.DTOs.HumanoResponse;
import com.example.APIMutant.DTOs.HumanoStats;
import com.example.APIMutant.entities.Humano;
import com.example.APIMutant.services.HumanoServiceImpl;
import com.example.APIMutant.repositories.HumanoRepository;
import com.example.APIMutant.DTOs.HumanoRequest;
import com.example.APIMutant.services.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HumanoTest {

    @Mock
    private HumanoRepository humanoRepository;

    @InjectMocks
    private HumanoServiceImpl humanoService;

    @InjectMocks
    private StatsService statsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNonSquareDNA() {
        String[] dna = {
                "AAAATGA",
                "TTGCAGT",
                "GCGTCCC",
                "CCCCTGG",
                "GTAGTCT",
                "AGTCACA" // Matriz 6x7
        };

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertThrows(IllegalArgumentException.class, () -> humanoService.isMutant(humanoRequest),
                "La matriz de ADN debe ser cuadrada NxN.");
    }


    @Test
    public void testMutantRows() {
        System.out.println("TestRows");
        String[] dna = {
                "AAAAAA",
                "TTGCAG",
                "GCGTCC",
                "CCCCTG",
                "GTAGTC",
                "AGTCAC"
        };

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertTrue(humanoService.isMutant(humanoRequest).isMutant());
    }

    @Test
    public void testMutantColumns() {

        String[] dna = {
                "ATGCGA",
                "ATGCGA",
                "ATGCGA",
                "TTGCAG",
                "GTAGTC",
                "AGTCAC"
        };

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertTrue(humanoService.isMutant(humanoRequest).isMutant());
    }

    @Test
    public void testMutantDiagonals() {

        String[] dna = {
                "ATGCGA",
                "TTGCAT",
                "GCGATA",
                "TTATGA",
                "GTTCAA",
                "AGTCAC"
        };

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertTrue(humanoService.isMutant(humanoRequest).isMutant());
    }


    @Test
    public void testInvalidCharacterInMatrix() {
        String[] dna = {
                "AAAATGA",
                "TTGCAGX", // Contiene un carácter no válido "X"
                "GCGTCCC",
                "CCCCTGG",
                "GTAGTCT",
                "AGTCACA"
        };

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertThrows(IllegalArgumentException.class, () -> humanoService.isMutant(humanoRequest),
                "La matriz contiene caracteres no válidos.");
    }


    @Test
    public void testEmptyMatrix() {
        String[] dna = {};

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertThrows(IllegalArgumentException.class, () -> humanoService.isMutant(humanoRequest),
                "La matriz no debe estar vacía.");
    }

    @Test
    public void testHumanDNA() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertFalse(humanoService.isMutant(humanoRequest).isMutant());
    }


    @Test
    public void testNullDNAArray() {
        // Cuando el ADN es null, debería lanzar una excepción
        HumanoRequest humanoRequest = HumanoRequest.builder().dna(null).build();
        assertThrows(IllegalArgumentException.class, () -> humanoService.isMutant(humanoRequest),
                "ADN es null, por lo tanto debería lanzar una excepción.");
    }

    @Test
    public void testMutantExistsInDatabase() {
        // Supongamos que el ADN ya existe y es un mutante.
        String[] dna = {
                "AAAAGT",
                "TTGCAG",
                "GCGTAC",
                "CCCCTG",
                "GTAGTC",
                "AGTCAC"
        };

        Humano existingHumano = new Humano();
        existingHumano.setDNA(new String[]{Arrays.toString(dna)});
        existingHumano.setMutant(true); // Asumimos que es un mutante

        // Simular que el repositorio retorna este humano cuando se busca por ADN.
        when(humanoRepository.findByDNA(Arrays.toString(dna))).thenReturn(Optional.of(existingHumano));

        // Crear la solicitud
        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();

        // Llamar al método isMutant
        HumanoResponse response = humanoService.isMutant(humanoRequest);

        // Verificar que se retorna la respuesta correcta
        assertTrue(response.isMutant());
        assertEquals("Es mutante", response.getMessage());
    }



    @Test
    public void testSingleLetterDNA() {
        // Caso límite de matriz 1x1
        String[] dna = {"A"};
        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertThrows(IllegalArgumentException.class, () -> humanoService.isMutant(humanoRequest),
                "La matriz 1x1 no puede considerarse para un ADN válido de mutante.");
    }
    @Test
    public void testInvalidCharacterAtEnd() {
        // ADN con un carácter no permitido al final
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTX" // Letra 'X' no válida al final
        };

        HumanoRequest humanoRequest = HumanoRequest.builder().dna(dna).build();
        assertThrows(IllegalArgumentException.class, () -> humanoService.isMutant(humanoRequest),
                "El ADN contiene un carácter no permitido al final.");
    }

    @Test
    public void testGetStats() throws Exception {
        // Configurar valores simulados para countByIsMutant
        when(humanoRepository.countByIsMutant(true)).thenReturn(10);  // Ej. 10 mutantes
        when(humanoRepository.countByIsMutant(false)).thenReturn(40); // Ej. 40 humanos

        // Llamar al método getStats
        HumanoStats stats = statsService.getStats();

        // Verificar los resultados esperados
        assertEquals(10, stats.getCount_mutant_dna());
        assertEquals(40, stats.getCount_human_dna());
        assertEquals(0.25f, stats.getRatio(), 0.001f); // Ratio esperado

        // Verificar si se imprimieron las estadísticas
        verify(humanoRepository).countByIsMutant(true);
        verify(humanoRepository).countByIsMutant(false);
    }

    @Test
    public void testGetStatsExceptionHandling() throws Exception {
        // Simular una excepción en el método countByIsMutant
        when(humanoRepository.countByIsMutant(true)).thenThrow(new RuntimeException("Error al contar mutantes"));

        // Ejecutar el método y esperar la excepción
        Exception exception = assertThrows(Exception.class, () -> {
            statsService.getStats();
        });

        // Verificar el mensaje de la excepción
        assertEquals("Error al contar mutantes", exception.getMessage());

        // Verificar si se imprimieron los mensajes en consola
        verify(humanoRepository).countByIsMutant(true);
    }
    @Test
    public void testGetDNAArrayWithNonNullDNA() {
        // Crear un objeto Humano con un valor de DNA no nulo
        Humano humano = new Humano();
        humano.setDNA(new String[]{"ATGC", "CGTA", "TACG", "GCTA"});

        // Verificar que el metodo getDNAArray devuelve el array esperado
        String[] expectedArray = {"ATGC", "CGTA", "TACG", "GCTA"};
        assertArrayEquals(expectedArray, humano.getDNAArray());
    }

    @Test
    public void testGetDNAArrayWithNullDNA() {
        // Crear un objeto Humano con DNA nulo
        Humano humano = new Humano();
        humano.setDNA(null);

        // Verificar que el metodo getDNAArray devuelve un array vacío
        assertArrayEquals(new String[0], humano.getDNAArray());
    }

}
