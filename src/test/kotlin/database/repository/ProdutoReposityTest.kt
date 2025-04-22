package database.repository

import database.dto.ProdutoDTO
import domain.entities.Produto
import exception.ProdutoNotFoundException
import extensoras.toDTO
import helps.CriarMocks.criarListaComProdutos
import helps.CriarMocks.criarProduto
import helps.CriarMocks.criarProdutoComId
import io.mockk.*
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.*

@ExtendWith(MockKExtension::class)
class ProdutoReposityTest {

    @SpyK
    private var produtoRepository: ProdutoRepository = ProdutoRepository()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Deve Cadastrar Produto Corretamente`() {
        val produto = criarProduto()
        justRun { produtoRepository.persist(any(ProdutoDTO::class)) }

        produtoRepository.casdastrarProduto(produto)

        verify(exactly = 1) { produtoRepository.persist(any(ProdutoDTO::class)) }
    }

    @Test
    fun `Deve Editar Produto Corretamente`() {
        val produto = criarProduto().copy(id = 1)

        every { produtoRepository.findById(any()) } returns produto.toDTO()
        justRun { produtoRepository.persist(any(ProdutoDTO::class)) }

        produtoRepository.editarProduto(produto)

        verify(exactly = 1) { produtoRepository.findById(1) }
        verify(exactly = 1) { produtoRepository.persist(any(ProdutoDTO::class)) }
    }

    @Test
    fun `Nao Deve Editar Produto Pois nao Achou o Produto`() {
        val produto = criarProduto().copy(id = 1)

        every { produtoRepository.findById(any()) } returns null
        justRun { produtoRepository.persist(any(ProdutoDTO::class)) }

        expectCatching { produtoRepository.editarProduto(produto) }
            .isFailure()
            .isA<ProdutoNotFoundException>()

        verify(exactly = 1) { produtoRepository.findById(1) }
        verify(exactly = 0) { produtoRepository.persist(any(ProdutoDTO::class)) }
    }

    @Test
    fun `Deve Deletar Produto Corretamente`() {
        val produto = criarProduto().copy(id = 1)

        every { produtoRepository.findById(any()) } returns produto.toDTO()
        justRun { produtoRepository.delete(any(ProdutoDTO::class)) }

        produtoRepository.deletarProduto(1)

        verify(exactly = 1) { produtoRepository.findById(1) }
        verify(exactly = 1) { produtoRepository.delete(any(ProdutoDTO::class)) }
    }

    @Test
    fun `Nao Deve Deletar Produto Pois nao Achou o Produto`() {
        val produto = criarProduto().copy(id = 1)

        every { produtoRepository.findById(any()) } returns null
        justRun { produtoRepository.delete(any(ProdutoDTO::class)) }

        expectCatching { produtoRepository.deletarProduto(1) }
            .isFailure()
            .isA<ProdutoNotFoundException>()

        verify(exactly = 1) { produtoRepository.findById(1) }
        verify(exactly = 0) { produtoRepository.persist(any(ProdutoDTO::class)) }
    }

    @Test
    fun `Deve Buscar Produto Por Id Corretamente`() {
        val produto = criarProdutoComId()

        every { produtoRepository.findById(any()) } returns produto.toDTO()

        val retorno = produtoRepository.buscarProdutoPorId(1)

        expectThat(retorno)
            .isNotNull()
            .isA<Produto>()
            .isEqualTo(produto)

        verify(exactly = 1) { produtoRepository.findById(1) }
    }


    @Test
    fun `Deve Buscar Produto Por Id Corretamente e retornar vazio`() {

        every { produtoRepository.findById(any()) } returns null

        val retorno = produtoRepository.buscarProdutoPorId(1)

        expectThat(retorno)
            .isNull()

        verify(exactly = 1) { produtoRepository.findById(1) }
    }

    @Test
    fun `Deve Buscar todos Produto  Corretamente`() {
        val lista = criarListaComProdutos()
        val produtoDTOS = lista.map { it.toDTO() }
        val query: PanacheQuery<ProdutoDTO> = mockk()

        every { produtoRepository.findAll() } returns query
        every { query.list() } returns produtoDTOS

        val retorno = produtoRepository.listarProdutos()

        expectThat(retorno)
            .isNotEmpty()
            .hasSize(lista.size)
            .contains(lista)

        verify(exactly = 1) { produtoRepository.findAll() }
    }


    @Test
    fun `Deve Buscar Produto Por Ids Corretamente`() {
        val lista = criarListaComProdutos()
        val ids = lista.map { it.id!! }.toSet()
        val produtoDTOS = lista.map { it.toDTO() }
        val query: PanacheQuery<ProdutoDTO> = mockk()


        every { produtoRepository.find("id IN ?1", ids) } returns query
        every { query.stream() } returns produtoDTOS.stream()

        val retorno = produtoRepository.buscarProdutoPorId(ids)

        expectThat(retorno)
            .isNotEmpty()
            .hasSize(lista.size)
            .contains(lista)

        verify(exactly = 1) { produtoRepository.find("id IN ?1", ids) }
    }


}