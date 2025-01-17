package com.algaworks.junit.utilidade;

import java.math.BigDecimal;
import java.util.Objects;

public class ContaBancaria {

    BigDecimal saldo;

    public ContaBancaria(BigDecimal saldo) {
        if (Objects.isNull(saldo)) {
            throw new IllegalArgumentException("Saldo não pode ser null");
        }

        this.saldo = saldo;
    }

    public void saque(BigDecimal valor) {
        // compareTo returns 0 if value equals to compared value
        // compareTo returns -1 if values lower than compared value
        if (Objects.isNull(valor) || valor.compareTo(BigDecimal.ZERO) == 0 || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saque não pode ser null, zero ou negativo");
        }

        if (saldo.compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        saldo = saldo.subtract(valor);
    }

    public void deposito(BigDecimal valor) {
        // compareTo returns 0 if value equals to compared value
        // compareTo returns -1 if values lower than compared value
        if (Objects.isNull(valor) || valor.compareTo(BigDecimal.ZERO) == 0 || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Depósito não pode ser null, zero ou negativo");
        }
        saldo = saldo.add(valor);
    }

    public BigDecimal saldo() {
        return this.saldo;
    }
}
