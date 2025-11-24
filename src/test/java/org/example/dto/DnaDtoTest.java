package org.example.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DnaDtoTest {

    @Test
    void testDnaRequest() {
        // 1. Probar Constructor y Getters/Setters
        DnaRequest request1 = new DnaRequest();
        String[] dna = {"AAAA", "CCCC"};
        request1.setDna(dna);

        assertEquals(dna, request1.getDna());

        // 2. Probar toString() (Lombok lo genera)
        String stringRepresentation = request1.toString();
        assertNotNull(stringRepresentation);
        assertTrue(stringRepresentation.contains("DnaRequest"));

        // 3. Probar equals() y hashCode()
        DnaRequest request2 = new DnaRequest();
        request2.setDna(dna); // Mismo contenido

        // Deben ser iguales
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        // Probar con objeto diferente
        DnaRequest request3 = new DnaRequest();
        request3.setDna(new String[]{"TTTT"});
        assertNotEquals(request1, request3);
    }

    @Test
    void testStatsResponse() {
        // 1. Probar Builder (si usaste @Builder) y AllArgsConstructor
        StatsResponse stats1 = StatsResponse.builder()
                .countMutantDna(40)
                .countHumanDna(100)
                .ratio(0.4)
                .build();

        // 2. Probar Getters
        assertEquals(40, stats1.getCountMutantDna());
        assertEquals(100, stats1.getCountHumanDna());
        assertEquals(0.4, stats1.getRatio());

        // 3. Probar toString()
        assertNotNull(stats1.toString());

        // 4. Probar equals() y hashCode()
        StatsResponse stats2 = new StatsResponse(40, 100, 0.4); // Usando AllArgsConstructor

        assertEquals(stats1, stats2);
        assertEquals(stats1.hashCode(), stats2.hashCode());

        // Diferente
        StatsResponse stats3 = new StatsResponse(0, 0, 0);
        assertNotEquals(stats1, stats3);
    }
}