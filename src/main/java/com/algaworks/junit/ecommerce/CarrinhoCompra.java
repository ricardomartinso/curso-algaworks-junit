package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

public class CarrinhoCompra {

	private final Cliente cliente;
	private final List<ItemCarrinhoCompra> itens;

	public CarrinhoCompra(Cliente cliente) {
		this(cliente, new ArrayList<>());
	}

	public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
		Objects.requireNonNull(cliente);
		Objects.requireNonNull(itens);
		this.cliente = cliente;
		this.itens = new ArrayList<>(itens); // Cria lista caso passem uma imutável
	}

	public List<ItemCarrinhoCompra> getItens() {
		return new ArrayList<>(itens);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void adicionarProduto(Produto produto, int quantidade) {

		if (Objects.isNull(produto)) {
			throw new IllegalArgumentException("Parâmetros não podem ser nulos");
		}
		if (quantidade < 1) {
			throw new IllegalArgumentException("Quantidade do produto não pode ser menor que 1");
		}

		Optional<ItemCarrinhoCompra> produtoExistente = itens.stream().filter(item -> item.getProduto().equals(produto))
				.findFirst();

		if (produtoExistente.isEmpty()) {
			itens.add(new ItemCarrinhoCompra(produto, quantidade));
			return;
		}

		produtoExistente.get().adicionarQuantidade(quantidade);

	}

	public void removerProduto(Produto produto) {
		if (Objects.isNull(produto)) {
			throw new IllegalArgumentException("Parâmetros não podem ser nulos");
		}

		Optional<ItemCarrinhoCompra> produtoExiste = itens.stream().filter(item -> item.getProduto().equals(produto))
				.findFirst();

		if (produtoExiste.isEmpty())
			throw new NoSuchElementException("Produto não existente no carrinho.");

		itens.remove(produtoExiste.get());
	}

	public void aumentarQuantidadeProduto(Produto produto) {
		if (Objects.isNull(produto)) {
			throw new IllegalArgumentException("Parâmetros não podem ser nulos");
		}

		Optional<ItemCarrinhoCompra> produtoExiste = itens.stream().filter(item -> item.getProduto().equals(produto))
				.findFirst();

		if (produtoExiste.isEmpty())
			throw new NoSuchElementException("Produto não existente no carrinho.");

		produtoExiste.get().adicionarQuantidade(1);
	}

	public void diminuirQuantidadeProduto(Produto produto) {
		if (Objects.isNull(produto)) {
			throw new IllegalArgumentException("Parâmetros não podem ser nulos");
		}
		Optional<ItemCarrinhoCompra> produtoExiste = itens.stream().filter(item -> item.getProduto().equals(produto))
				.findFirst();

		if (produtoExiste.isEmpty())
			throw new NoSuchElementException("Produto não existente no carrinho.");

		if (produtoExiste.get().getQuantidade() == 1) {
			itens.remove(produtoExiste.get());
		} else {
			produtoExiste.get().subtrairQuantidade(1);
		}

	}

	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemCarrinhoCompra::getValorTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public int getQuantidadeTotalDeProdutos() {
		return itens.stream().mapToInt(ItemCarrinhoCompra::getQuantidade).sum();
	}

	public void esvaziar() {
		itens.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CarrinhoCompra that = (CarrinhoCompra) o;
		return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itens, cliente);
	}
}
