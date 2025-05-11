package domain.services

import database.repository.ProdutoRepository
import domain.entities.Categoria
import domain.entities.Produto
import event.producer.ProdutoProducer
import exception.ProdutoInvalidoException
import helps.CriarMocksProduto.criarListaComProdutos
import helps.CriarMocksProduto.criarProduto
import helps.CriarMocksProduto.criarProdutoComId
import helps.CriarMocksProduto.criarTextoAleatorio
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.*
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class ProdutoServiceTest {

    @MockK
    private lateinit var produtoProducer: ProdutoProducer

    @MockK
    private lateinit var produtoRepository: ProdutoRepository

    lateinit var produtoService: ProdutoService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        produtoService = ProdutoService(produtoRepository, produtoProducer)
    }

    @Test
    fun `Deve cadastrar Produto Corretamente`() {
        val produto = criarProduto()

        every { produtoRepository.casdastrarProduto(any(Produto::class)) } returns 1
        justRun { produtoProducer.cadastarProduto(any(Produto::class)) }

        produtoService.cadastrarProduto(produto)

        verify(exactly = 1) { produtoRepository.casdastrarProduto(produto) }
    }

    @Test
    fun `Deve ditar Produto Corretamente`() {
        val produto = criarProdutoComId()
        justRun { produtoRepository.editarProduto(any(Produto::class)) }

        produtoService.editarProduto(produto)

        verify(exactly = 1) { produtoRepository.editarProduto(produto) }
    }

    @Test
    fun `Deve listar Todos Produtos Corretamente`() {
        val produtos = criarListaComProdutos()
        every { produtoRepository.listarProdutos() } returns produtos

        val resultado = produtoService.listarProdutos()

        expectThat(resultado)
            .isNotEmpty()
            .hasSize(produtos.size)
            .isEqualTo(produtos)

        verify(exactly = 1) { produtoRepository.listarProdutos() }
    }

    @Test
    fun `Deve buscar Produto Por Id Produtos Corretamente`() {
        val produtos = criarProdutoComId()

        every { produtoRepository.buscarProdutoPorId(produtos.id!!) } returns produtos

        val resultado = produtoService.buscarProdutoPorId(produtos.id!!)

        expectThat(resultado)
            .isNotNull()
            .isEqualTo(produtos)

        verify(exactly = 1) { produtoRepository.buscarProdutoPorId(produtos.id!!) }
    }

    @Test
    fun `Deve retonar erros quando tenta cadastar produto`() {
        val produto = Produto(
            nome = "X",
            descricao = "",
            preco = BigDecimal.ZERO,
            categoria = Categoria.lanche,
            id = null
        )

        justRun { produtoRepository.casdastrarProduto(produto) }

        expectCatching { produtoService.cadastrarProduto(produto) }
            .isFailure()
            .isA<ProdutoInvalidoException>()

        verify(exactly = 0) { produtoRepository.casdastrarProduto(produto) }
    }

    @Test
    fun `Deve retonar erros quando tenta editar produto`() {
        val produto = Produto(
            nome = criarTextoAleatorio(200),
            descricao = criarTextoAleatorio(501),
            preco = BigDecimal("10000000.00"),
            categoria = Categoria.lanche,
            id = null
        )

        justRun { produtoRepository.editarProduto(produto) }

        expectCatching { produtoService.editarProduto(produto) }
            .isFailure()
            .isA<ProdutoInvalidoException>()

        verify(exactly = 0) { produtoRepository.editarProduto(produto) }
    }

    @Test
    fun `Deve retonar erros quando tenta editar produto e passa nome vazio`() {
        val produto = criarProduto().copy(nome = "")

        justRun { produtoRepository.editarProduto(produto) }

        expectCatching { produtoService.editarProduto(produto) }
            .isFailure()
            .isA<ProdutoInvalidoException>()

        verify(exactly = 0) { produtoRepository.editarProduto(produto) }
    }

    @Test
    fun `Deve buscar Produto Por Ids Produtos Corretamente`() {
        val produtos = criarListaComProdutos()
        val ids = produtos.map { it.id!! }.toSet()

        every { produtoRepository.buscarProdutoPorId(ids) } returns produtos

        val resultado = produtoService.bucarProdutoPorIds(ids)

        expectThat(resultado)
            .isNotNull()
            .isEqualTo(produtos)
            .isNotEmpty()
            .hasSize(produtos.size)

        verify(exactly = 1) { produtoRepository.buscarProdutoPorId(ids) }
    }

    @Test
    fun `Deve buscar deletar Produtos Corretamente`() {
        val produto = criarProdutoComId()
        justRun { produtoRepository.deletarProduto(produto.id!!) }
        produtoService.deletarProduto(produto.id!!)
        verify(exactly = 1) { produtoRepository.deletarProduto(produto.id!!) }
    }
}