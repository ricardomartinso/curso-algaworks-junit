package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes no utilitário de ContaBancaria")
class ContaBancariaTest {

    @Test
    void deveCriarContaBancariaQuandoSaldoValido() {

        BigDecimal saldoPositivo = BigDecimal.valueOf(100);
        BigDecimal saldoZero = BigDecimal.valueOf(0);
        BigDecimal saldoNegativo = BigDecimal.valueOf(-100);

        ContaBancaria contaBancaria = new ContaBancaria(saldoZero);
        ContaBancaria contaBancaria2 = new ContaBancaria(saldoPositivo);
        ContaBancaria contaBancaria3 = new ContaBancaria(saldoNegativo);


        assertEquals(contaBancaria.saldo(), BigDecimal.valueOf(0));
        assertEquals(contaBancaria2.saldo(), BigDecimal.valueOf(100));
        assertEquals(contaBancaria3.saldo(), BigDecimal.valueOf(-100));

    }

    @DisplayName("Deve lançar IllegalArgumentException ao criar ContaBancaria com valor null")
    @Test
    void deveLancarIllegalExceptionAoCriarContaBancariaComValorNull() {
        BigDecimal saldoNull = null;

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            new ContaBancaria(saldoNull);
        });

    }

    @DisplayName("Deve realizar um saque quando houver saldo disponível")
    @Test
    void deveRealizarUmSaqueQuandoSaldoMaiorIgualAoSaque() {
        //Arrange
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));

        //Act
        contaBancaria.saque(BigDecimal.valueOf(50));

        //Assert
        assertEquals(contaBancaria.saldo(), BigDecimal.valueOf(50));
    }

    @Test
    void saqueComValoresInvalidosDeveLancarIllegalArgumentException() {

        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));

        assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.saque(BigDecimal.valueOf(0));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.saque(BigDecimal.valueOf(-10));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.saque(null);
        });
    }

    @Test
    void saqueComSaldoInsuficienteDeveLancarRuntimeException() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            contaBancaria.saque(BigDecimal.valueOf(150));
        });

        assertEquals("Saldo insuficiente", error.getMessage());
    }


    @Test
    void depositoComValoresInvalidosDeveLancarIllegalArgumentException() {

        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));

        assertThrows(IllegalArgumentException.class, () -> {
           contaBancaria.deposito(BigDecimal.valueOf(0));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.deposito(null);
        });

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.deposito(BigDecimal.valueOf(-100));
        });


        assertEquals("Depósito não pode ser null, zero ou negativo", error.getMessage());
    }

    @Test
    void deveRealizarUmDeposito() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(100));
        contaBancaria.deposito(BigDecimal.valueOf(50));
        assertEquals(contaBancaria.saldo(), BigDecimal.valueOf(150));
    }

}
