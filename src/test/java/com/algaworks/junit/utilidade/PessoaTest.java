package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    void assercaoAgrupada() {
        Pessoa pessoa = new Pessoa("Ricardo", "Martins");

        assertAll(
                () -> assertEquals("Ricardo", pessoa.getNome()),
                () -> assertEquals("Martins", pessoa.getSobrenome())
        );
    }
}
