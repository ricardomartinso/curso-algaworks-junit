package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrinho de Compras Testes")
class CarrinhoCompraTest {

    static Cliente cliente = new Cliente(1L, "Ricardo");

    Produto p1 = new Produto(
            1L,
            "Monitor AOC 144Hz FULL HD",
            "Monitor gamer full hd com taxa de refresh rate de 144hz",
            BigDecimal.valueOf(1900.9)
    );

    Produto p2 = new Produto(
            2L,
            "RTX 4080",
            "Placa de vídeo para resoluçÕes em 4K",
            BigDecimal.valueOf(3200.50)
    );

    Produto p3 = new Produto(
            3L,
            "Teclado HyperX Elite FPS",
            "Teclado mecânico com switch red",
            BigDecimal.valueOf(400)
    );

    CarrinhoCompra carrinhoCompra;


    @Nested
    @DisplayName("Dado um carrinho de compras com 3 itens")
    class CarrinhoComprasComTresItens {

        ItemCarrinhoCompra item1 = new ItemCarrinhoCompra(p1,1);
        ItemCarrinhoCompra item2 = new ItemCarrinhoCompra(p2,1);
        ItemCarrinhoCompra item3 = new ItemCarrinhoCompra(p3,1);


        @BeforeEach
        void beforeEach() {
            carrinhoCompra = new CarrinhoCompra(cliente, List.of(item1, item2, item3));
        }

        @Nested
        @DisplayName("Quando o cliente listar os 3 itens do carrinho")
        class ListaItensCarrinho {

            @Test
            @DisplayName("Então deve listar os 3 itens do carrinho")
            void deveListarOsItensDoCarrinho () {

                List<ItemCarrinhoCompra> itensCarrinho = carrinhoCompra.getItens();

                assertEquals(3, itensCarrinho.size(), "O carrinho deve conter 3 itens");

                assertTrue(itensCarrinho.stream().anyMatch(item -> item.getProduto().getNome().equals("Monitor AOC 144Hz FULL HD")));
                assertTrue(itensCarrinho.stream().anyMatch(item -> item.getProduto().getNome().equals("RTX 4080")));
                assertTrue(itensCarrinho.stream().anyMatch(item -> item.getProduto().getNome().equals("Teclado HyperX Elite FPS")));
            }
        }

        @Nested
        @DisplayName("Quando o cliente adicionar um produto diferente ao carrinho")
        class AdicionaItemDiferenteAoCarrinho {

            @Test
            @DisplayName("Então adiciona item diferente ao carrinho")
            void adicionaItemDiferenteAoCarrinho() {
                Produto p4 = new Produto(
                        4L,
                        "Produto Aleatorio",
                        "Produto aleatorio descricao",
                        BigDecimal.valueOf(250.45)
                );

                carrinhoCompra.adicionarProduto(p4, 2);

                int tamanhoEsperadoAposAdicao = 4;

                assertEquals(tamanhoEsperadoAposAdicao, carrinhoCompra.getItens().size());
            }
        }

        @Nested
        @DisplayName("Quando o cliente adicionar um produto já existente no carrinho")
        class AdicionaItemIgualAoCarrinho {

            @Test
            @DisplayName("Então aumenta a quantidade do item já existente")
            void adicionaItemIgualAoCarrinho() {

                int quantidadeAdicionada = 1;
                int tamanhoEsperadoAposAdicao = carrinhoCompra.getItens().size();
                Optional<ItemCarrinhoCompra> itemCarrinho = carrinhoCompra.getItens().stream().filter(item -> item.getProduto().equals(p3)).findFirst();
                int quantidadeDoProdutoAposAdicao = quantidadeAdicionada + itemCarrinho.get().getQuantidade();

                carrinhoCompra.adicionarProduto(p3, quantidadeAdicionada);

                assertEquals(tamanhoEsperadoAposAdicao, carrinhoCompra.getItens().size()); // carrinho manter o mesmo tamanho
                assertEquals(quantidadeDoProdutoAposAdicao, itemCarrinho.get().getQuantidade()); // aumentar a quantidade de item do carrinho de acordo com a variavel quantidadeAdicionada
            }
        }

    }

    @Nested
    @DisplayName("Dado um cliente válido")
    class ClienteValido {

        @Nested
        @DisplayName("Quando listar o cliente")
        class ListaCliente {

            @Test
            @DisplayName("Então deve listar um cliente válido")
            void deveListarOClienteValido() {

                carrinhoCompra = new CarrinhoCompra(cliente);

                assertEquals(carrinhoCompra.getCliente().getId(), 1L);
            }

        }


    }


}