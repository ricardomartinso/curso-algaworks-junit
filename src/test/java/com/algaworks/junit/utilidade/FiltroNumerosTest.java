package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltroNumerosTest {

    @Test
    void deveRetornarNumerosPares() {

        List<Integer> retorno = FiltroNumeros.numerosPares(Arrays.asList(1,2,3,4,5,6));

        List<Integer> esperado = Arrays.asList(2,4,6);

        assertIterableEquals(esperado, retorno);
    }

    @Test
    void deveRetornarNumerosImpares() {
        List<Integer> retorno = FiltroNumeros.numerosImpares(Arrays.asList(1,2,3,4,5,6));
        List<Integer> esperado = Arrays.asList(1,3,5);

        assertIterableEquals(esperado, retorno);
    }

    @Test
    void deveRetornarTrueParaNumerosPositivos() {
        for (int i = 0; i <= 100; i+=2) {
            assertEquals(0, i % 2);
        }
    }
}
