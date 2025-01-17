package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void deveSaudarBomDia() {
        int horaValidaDia = 9;

        String saudacaoDia = SaudacaoUtil.saudar(horaValidaDia);

        assertEquals("Bom dia", saudacaoDia);
    }

    @Test
    public void deveSaudarBoaTarde() {
        int horaValidaTarde = 15;

        String saudacaoTarde = SaudacaoUtil.saudar(horaValidaTarde);

        assertEquals("Boa tarde", saudacaoTarde);
    }

    @Test
    public void deveSaudarBoaNoite() {
        int horaValidaNoite = 18;

        String saudacaoNoturna = SaudacaoUtil.saudar(horaValidaNoite);

        assertEquals("Boa noite", saudacaoNoturna);
    }

    @Test
    public void deveSaudarBomDiaApartirDas5h() {
        int horaValidaDia = 5;

        String saudacaoDia = SaudacaoUtil.saudar(horaValidaDia);

        assertEquals("Bom dia", saudacaoDia);
    }

    @Test
    public void deveSaudarBoaNoiteAteAs4h() {
        int horaValidaNoite = 4;

        String saudacaoDia = SaudacaoUtil.saudar(horaValidaNoite);

        assertEquals("Boa noite", saudacaoDia);
    }

    @Test
    public void saudarDeveLancarException() {
        int horaInvalida = -1;


        Executable executable = () -> SaudacaoUtil.saudar(horaInvalida);

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Hora inválida", illegalArgumentException.getMessage());
    }

}
