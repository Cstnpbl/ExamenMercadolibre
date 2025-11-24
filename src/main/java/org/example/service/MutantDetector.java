package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) return false;

        int n = dna.length;
        // Validación básica: que no haya filas nulas y que sea cuadrada (NxN)
        for (String row : dna) {
            if (row == null || row.length() != n) return false;
        }

        char[][] matrix = new char[n][n];

        // --- VALIDACIÓN DE CARACTERES Y CREACIÓN DE MATRIZ ---
        // Verificamos que SOLO existan A, T, C, G mientras llenamos la matriz.
        for (int i = 0; i < n; i++) {
            char[] rowChars = dna[i].toCharArray();
            for (char c : rowChars) {
                if (!isValidBase(c)) {
                    return false; // Si hay una letra rara, rechazamos inmediatamente.
                }
            }
            matrix[i] = rowChars;
        }
        // -----------------------------------------------------

        int sequenceCount = 0;

        // Recorremos la matriz buscando secuencias
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // 1. Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkLine(matrix, row, col, 0, 1)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // Early termination
                    }
                }

                // 2. Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkLine(matrix, row, col, 1, 0)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // 3. Diagonal Principal (Descendente)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkLine(matrix, row, col, 1, 1)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // 4. Diagonal Secundaria (Ascendente)
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkLine(matrix, row, col, -1, 1)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }

    // Método auxiliar genérico para verificar secuencias
    private boolean checkLine(char[][] matrix, int row, int col, int deltaRow, int deltaCol) {
        char first = matrix[row][col];
        // La validación de base ya se hizo al inicio, pero esto asegura la lógica de comparación
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i * deltaRow][col + i * deltaCol] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidBase(char c) {
        return c == 'A' || c == 'T' || c == 'C' || c == 'G';
    }
}