package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void testAnalyzeDna_NewMutant() {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        // Simulamos que NO existe en BD
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        // Simulamos que el detector dice que es mutante
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        // Verificamos que se guardó en BD
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_CachedResult() {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        DnaRecord existingRecord = new DnaRecord();
        existingRecord.setMutant(true);

        // Simulamos que YA existe en BD
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        // Verificamos que NO se llamó al detector ni a save (ahorro de recursos)
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }
}