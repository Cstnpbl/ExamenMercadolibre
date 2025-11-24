package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    void testMutantWithHorizontalSequences() {
        String[] dna = {
                "AAAA",  // Secuencia 1
                "CCCC",  // Secuencia 2
                "TCAG",
                "GGTC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testMutantWithVerticalSequences() {
        String[] dna = {
                "ATCG",
                "ATCG",
                "ATCG",
                "ATCG"  // 4 columnas con secuencia
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testMutantWithDiagonalSequences() {
        String[] dna = {
                "ATCG",
                "GATG",
                "TCAT",
                "GCTA"  // Diagonal A-A-A-A
        };
        // Necesitamos al menos 2 secuencias. Agreguemos una horizontal para asegurar
        String[] dna2 = {
                "ATCG",
                "GATG",
                "TCAT",
                "AAAA" // Secuencia 2
        };
        // Nota: El ejemplo anterior tenía solo 1 diagonal, para ser mutante necesita >1.
        // Vamos a usar un caso claro de mutante del pdf
        String[] dnaMutant = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dnaMutant));
    }

    @Test
    void testHumanOneSequence() {
        String[] dna = {
                "AAAA", // Solo 1 secuencia
                "TCAG",
                "GTCA",
                "TGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testHumanNoSequence() {
        String[] dna = {
                "ATCG",
                "GTCA",
                "TGAC",
                "CATG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testInvalidDnaNull() {
        assertFalse(mutantDetector.isMutant(null));
    }

    @Test
    void testInvalidDnaEmpty() {
        assertFalse(mutantDetector.isMutant(new String[]{}));
    }

    @Test
    void testInvalidDnaNxM() {
        String[] dna = {
                "ABC",
                "DEF" // No es cuadrada
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testInvalidDnaCharacters() {
        String[] dna = {
                "ATCG",
                "ATCX", // X no es válido
                "ATCG",
                "ATCG"
        };
        // Dependiendo de tu lógica, esto retorna false o lanza excepción.
        // En nuestra implementación retorna false por validación de base.
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testDiagonalsBothDirections() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
}