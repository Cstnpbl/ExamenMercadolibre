package org.example.model;

import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.entity.DnaRecord;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DnaModelTest {

    @Test
    void testDnaRecordEntity() {
        // Testeamos el Builder y los Getters
        DnaRecord record = DnaRecord.builder()
                .id(1L)
                .dnaHash("hash123")
                .isMutant(true)
                .build();

        assertEquals(1L, record.getId());
        assertEquals("hash123", record.getDnaHash());
        assertTrue(record.isMutant());

        // Testeamos Setters y Constructor vacío
        DnaRecord record2 = new DnaRecord();
        record2.setId(2L);
        assertNotNull(record2);
    }

    @Test
    void testDnaRequestDto() {
        DnaRequest request = new DnaRequest();
        String[] dna = {"ATCG"};
        request.setDna(dna);

        assertArrayEquals(dna, request.getDna());

        // Test toString/Equals/HashCode (generados por @Data)
        DnaRequest request2 = new DnaRequest();
        request2.setDna(dna);
        assertEquals(request, request2);
    }

    @Test
    void testStatsResponseDto() {
        StatsResponse stats = StatsResponse.builder()
                .countMutantDna(10)
                .countHumanDna(20)
                .ratio(0.5)
                .build();

        assertEquals(10, stats.getCountMutantDna());
        assertEquals(20, stats.getCountHumanDna());
        assertEquals(0.5, stats.getRatio());
    }
}