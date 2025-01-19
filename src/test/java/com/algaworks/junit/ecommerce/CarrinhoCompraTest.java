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

    Produto p4 = new Produto(
            4L,
            "Produto Aleatorio",
            "Produto aleatorio descricao",
            BigDecimal.valueOf(250.45)
    );

    CarrinhoCompra carrinhoCompra;


    @Nested
    @DisplayName("Dado um carrinho de compras com 3 itens")
    class CarrinhoComprasComTresItens {

        ItemCarrinhoCompra item1 = new ItemCarrinhoCompra(p1,1);
        ItemCarrinhoCompra item2 = new ItemCarrinhoCompra(p2,2);
        ItemCarrinhoCompra item3 = new ItemCarrinhoCompra(p3,3);


        @BeforeEach
        void beforeEach() {
            carrinhoCompra = new CarrinhoCompra(cliente, List.of(item1, item2, item3));
        }

        @Nested
        @DisplayName("Quando listar os 3 itens do carrinho")
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
        @DisplayName("Quando adicionar um produto diferente ao carrinho")
        class AdicionaItemDiferenteAoCarrinho {

            @Test
            @DisplayName("Então adiciona item diferente ao carrinho")
            void adicionaItemDiferenteAoCarrinho() {

                carrinhoCompra.adicionarProduto(p4, 2);

                int tamanhoEsperadoAposAdicao = 4;

                assertEquals(tamanhoEsperadoAposAdicao, carrinhoCompra.getItens().size());
            }
        }

        @Nested
        @DisplayName("Quando adicionar um produto já existente no carrinho")
        class AdicionaItemIgualAoCarrinho {

            @Test
            @DisplayName("Então aumenta a quantidade do item já existente")
            void adicionaItemIgualAoCarrinho() {

                int quantidadeAdicionada = 1;
                Optional<ItemCarrinhoCompra> itemCarrinho = carrinhoCompra.getItens().stream().filter(item -> item.getProduto().equals(p3)).findFirst();
                int quantidadeDeProdutosAposAdicao = quantidadeAdicionada + itemCarrinho.get().getQuantidade();

                carrinhoCompra.adicionarProduto(p3, quantidadeAdicionada);
                assertEquals(3, carrinhoCompra.getItens().size());
                assertEquals(quantidadeDeProdutosAposAdicao, itemCarrinho.get().getQuantidade());
            }
        }

        @Nested
        @DisplayName("Quando adicionar produto utilizando parâmetros inválidos")
        class AdicionaProdutoComParametrosInvalidos {
            @Test
            @DisplayName("Então deve retornar IllegalArgumentException")
            void deveRetornarIllegalArgumentException() {

                IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
                    carrinhoCompra.adicionarProduto(null, 1);
                });
                assertEquals("Parâmetros não podem ser nulos", illegalArgumentException.getMessage());
            }
        }

        @Nested
        @DisplayName("Quando adicionar um produto com quantidade menor que 0")
        class AdicionaProdutoComQuantidadeInvalida {
            @Test
            @DisplayName("Então deve retornar IllegalArgumentException")
            void deveRetornarIllegalArgumentException() {
                IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
                    carrinhoCompra.adicionarProduto(p3, 0);
                });
                assertEquals("Quantidade do produto não pode ser menor que 1", illegalArgumentException.getMessage());
            }
        }

        @Nested
        @DisplayName("Quando tenta remover um produto do carrinho")
        class RemoverProdutoDoCarrinho {
            @Test
            @DisplayName("Então deve remover o item do carrinho")
            void deveRemoverProdutoDoCarrinho() {
                int quantidadeDeItensCarrinho = carrinhoCompra.getItens().size();
                carrinhoCompra.removerProduto(p1);
                assertEquals(quantidadeDeItensCarrinho - 1, carrinhoCompra.getItens().size());
                assertFalse(carrinhoCompra.getItens().stream().anyMatch(item -> item.getProduto().equals(p1)));
            }
        }

        @Nested
        @DisplayName("Quando remover um produto inexistente")
        class RemoverProdutoInexistente {
            @Test
            @DisplayName("Então deve retornar RuntimeException")
            void deveRetornarRuntimeException() {
                RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                    carrinhoCompra.removerProduto(p4);
                });
                assertEquals("Produto não existente no carrinho.", runtimeException.getMessage());

            }
        }

        @Nested
        @DisplayName("Quando remover um produto passando parâmetros inválidos")
        class RemoverProdutoComParametrosInvalidos {
            @Test
            @DisplayName("Então deve retornar IllegalArgumentException")
            void deveRetornarIllegalArgumentException() {
                IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
                    carrinhoCompra.removerProduto(null);
                });
                assertEquals("Parâmetros não podem ser nulos", illegalArgumentException.getMessage());
            }
        }

        @Nested
        @DisplayName("Quando aumentar quantidade de um produto existente")
        class AumentaQuantidadeDoProduto {
            @Test
            @DisplayName("Então deve aumentar a quantidade do produto em 1")
            void aumentaQuantidadeDoProdutoEm1() {

                int quantidadeAntes = carrinhoCompra.getItens().get(0).getQuantidade();

                carrinhoCompra.aumentarQuantidadeProduto(p1);

                int quantidadeDepois = carrinhoCompra.getItens().get(0).getQuantidade();

                assertEquals(quantidadeAntes + 1, quantidadeDepois);
            }
        }

        @Nested
        @DisplayName("Quando aumentar quantidade de um produto inexistente")
        class AumentaQuantidadeDeProdutoInexistente {
            @Test
            @DisplayName("Então deve retornar RuntimeException")
            void deveRetornarRuntimeException() {
                RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
                    carrinhoCompra.aumentarQuantidadeProduto(p4);
                });
                assertEquals("Produto não existente no carrinho.", runtimeException.getMessage());
            }
        }

        @Nested
        @DisplayName("Quando passar parâmetro inválido ao aumentar quantidade do produto")
        class AumentarQuantidadeProdutoInvalido {
            @Test
            @DisplayName("Então deve retornar IllegalArgumentException")
            void deveRetornarIllegalArgumentException() {
                IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
                    carrinhoCompra.aumentarQuantidadeProduto(null);
                });
                assertEquals("Parâmetros não podem ser nulos", illegalArgumentException.getMessage());
            }
        }

        @Nested
        @DisplayName("Quando diminuir quantidade de um produto com quantidade acima de 1")
        class DiminuirQuantidadeDoProdutoQuantidadeMaiorQueUm {
            @Test
            @DisplayName("Então deve diminuir a quantidade do produto em um")
            void diminuirQuantidadeDoProdutoEmUm() {

                int quantidadeAntes = carrinhoCompra.getItens().get(2).getQuantidade();

                carrinhoCompra.diminuirQuantidadeProduto(p3);

                int quantidadeApos = carrinhoCompra.getItens().get(2).getQuantidade();

                assertEquals(quantidadeAntes - 1, quantidadeApos);
            }
        }

        @Nested
        @DisplayName("Quando diminuir quantidade de um produto com quantidade 1")
        class DiminuirQuantidadeDeProdutoQuantidadeUm {
            @Test
            @DisplayName("Então deve remover o produto do carrinho")
            void deveRemoverProdutoDoCarrinho() {
                carrinhoCompra.diminuirQuantidadeProduto(p1);
                assertEquals(2, carrinhoCompra.getItens().size());
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
