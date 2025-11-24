package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void testGetStats() {
        // Simulamos 40 mutantes y 100 humanos
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void testGetStats_DivisionByZero() {
        // Simulamos 0 humanos para verificar que no explote por división por cero
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0, response.getRatio()); // O lo que tu lógica defina (0 o infinito)
    }
}