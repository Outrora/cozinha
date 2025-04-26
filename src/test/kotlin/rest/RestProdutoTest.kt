package rest

import controller.ProdutoController
import helps.CriarMocks
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import jakarta.ws.rs.core.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import rest.util.ApiResponse
import strikt.api.expectThat
import strikt.assertions.*

@ExtendWith(MockKExtension::class)
class RestProdutoTest {

    @MockK
    private lateinit var produtoController: ProdutoController

    lateinit var rest: RestProduto

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        rest = RestProduto(produtoController)
    }

    @Test
    fun `Deve listar produtos Corretamente`() {
        val lista = CriarMocks.criarListaComProdutos();
        every { produtoController.listarProdutos() } returns lista

        val resultado = rest.listarProdutos()

        expectThat(resultado)
            .isNotNull()
            .hasSize(lista.size)
            .containsExactly(resultado)

        verify(exactly = 1) { produtoController.listarProdutos() }
    }

    @Test
    fun `Deve cadastrar produto Corretamente`() {
        val produto = CriarMocks.criarProdutoRequest()
        justRun { produtoController.cadastrarProduto(produto) }

        val resultado = rest.criarProduto(produto)

        expectThat(resultado)
            .isNotNull()
            .isA<Response>()

        expectThat(resultado.status)
            .isEqualTo(Response.Status.CREATED.statusCode)

        expectThat(resultado.entity)
            .isA<ApiResponse>()

        verify(exactly = 1) { produtoController.cadastrarProduto(produto) }
    }

    @Test
    fun `Deve editar produto Corretamente`() {
        val produto = CriarMocks.criarProdutoRequest()
        val id = 1
        justRun { produtoController.editarProduto(produto, id) }

        val resultado = rest.editarProduto(produto, id)

        expectThat(resultado)
            .isNotNull()
            .isA<Response>()

        expectThat(resultado.status)
            .isEqualTo(Response.Status.OK.statusCode)

        expectThat(resultado.entity)
            .isA<ApiResponse>()

        verify(exactly = 1) { produtoController.editarProduto(produto, id) }
    }

    @Test
    fun `Deve deletar produto Corretamente`() {
        val id = 1
        justRun { produtoController.deletarProduto(id) }

        rest.deletarProduto(id)

        verify(exactly = 1) { produtoController.deletarProduto(id) }
    }

    @Test
    fun `Deve buscar produto por id Corretamente`() {
        val produto = CriarMocks.criarProdutoComId()
        val id = 1
        every { produtoController.buscarProdutoPorId(id) } returns produto

        val resultado = rest.buscarProdutoPorId(id)

        expectThat(resultado)
            .isNotNull()
            .isEqualTo(produto)

        verify(exactly = 1) { produtoController.buscarProdutoPorId(id) }
    }

    @Test
    fun `Deve buscar produto por ids Corretamente`() {
        val produtos = CriarMocks.criarListaComProdutos()
        val ids = produtos.map { it.id!! }.toSet()
        every { produtoController.buscarProdutoPorIds(ids) } returns produtos

        val resultado = rest.bucarProdutoPorIds(ids)

        expectThat(resultado)
            .isNotNull()
            .hasSize(produtos.size)
            .containsExactly(resultado)

        verify(exactly = 1) { produtoController.buscarProdutoPorIds(ids) }
    }
}