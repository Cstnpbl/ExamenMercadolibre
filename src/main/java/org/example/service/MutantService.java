package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public boolean analyzeDna(String[] dna) {
        // 1. Generar Hash (Concatenar array simple para demo, en prod usar SHA-256)
        String dnaHash = String.join("", dna);

        // 2. Verificar caché en BD
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        // 3. Si no existe, analizar
        boolean isMutant = mutantDetector.isMutant(dna);

        // 4. Guardar resultado
        DnaRecord record = DnaRecord.builder()
                .dnaHash(dnaHash)
                .isMutant(isMutant)
                .build();
        dnaRecordRepository.save(record);

        return isMutant;
    }
}