import controller.ProdutoController
import domain.entities.Produto
import domain.services.ProdutoService
import exception.ProdutoInvalidoException
import exception.ProdutoNotFoundException
import helps.CriarMocksProduto.criarListaComProdutos
import helps.CriarMocksProduto.criarProduto
import helps.CriarMocksProduto.criarProdutoRequest
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

@ExtendWith(MockKExtension::class)
class ProdutoControllerTest {

    @MockK
    private lateinit var produtoService: ProdutoService

    private lateinit var controller: ProdutoController

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        controller = ProdutoController(produtoService)
    }

    @Test
    fun `Deve Cadastrar Produto Corretamente`() {
        every { produtoService.cadastrarProduto(any(Produto::class)) } returns Unit

        controller.cadastrarProduto(criarProdutoRequest())

        verify(exactly = 1) { produtoService.cadastrarProduto(any(Produto::class)) }
    }

    @Test
    fun `Deve Editar Produto Corretamente`() {

        val produtoR = criarProdutoRequest()
        justRun { produtoService.editarProduto(any(Produto::class)) }
        controller.editarProduto(produtoR, 1)
        verify(exactly = 1) {
            produtoService.editarProduto(withArg { produto ->
                expectThat(produto) {
                    get { this.id }.isEqualTo(1)
                    get { nome }.isEqualTo(produtoR.nome)
                    get { descricao }.isEqualTo(produtoR.descricao)
                    get { preco }.isEqualTo(produtoR.preco)
                    get { categoria }.isEqualTo(produtoR.categoria)
                }
            })
        }
    }

    @Test
    fun `deve lista Todos Os Produto Corretamente`() {

        val lista = criarListaComProdutos()
        every { produtoService.listarProdutos() } returns lista

        val retorno = controller.listarProdutos()

        expectThat(retorno)
            .isNotNull()
            .isNotEmpty()
            .hasSize(lista.size)
            .contains(lista)

        verify(exactly = 1) { produtoService.listarProdutos() }
    }

    @Test
    fun `deve deletar Produto Corretamente`() {
        justRun { produtoService.deletarProduto(any()) }

        controller.deletarProduto(1)

        verify(exactly = 1) { produtoService.deletarProduto(1) }
    }

    @Test
    fun `deve buscar Produto Por Id Os Produtos Corretamente`() {

        val produto = criarProduto()
        every { produtoService.buscarProdutoPorId(any()) } returns produto

        val retorno = controller.buscarProdutoPorId(1)

        expectThat(retorno)
            .isNotNull()
            .isSameInstanceAs(produto)
            .isEqualTo(produto)

        verify(exactly = 1) { produtoService.buscarProdutoPorId(1) }
    }

    @Test
    fun `buscar Produto Por Ids Produto Corretamente`() {

        val lista = criarListaComProdutos()
        val id = lista.map { it.id!! }.toSet()

        every { produtoService.bucarProdutoPorIds(any()) } returns lista

        val retorno = controller.buscarProdutoPorIds(id);

        expectThat(retorno)
            .isNotNull()
            .isNotEmpty()
            .hasSize(lista.size)
            .contains(lista)

        verify(exactly = 1) { produtoService.bucarProdutoPorIds(id) }
    }


    @Test
    fun `deve lançar exceção quando produto não for encontrado`() {
        // Given
        val idInexistente = 999
        every { produtoService.buscarProdutoPorId(idInexistente) } throws ProdutoNotFoundException()

        expectCatching { controller.buscarProdutoPorId(idInexistente) }
            .isFailure()
            .isA<ProdutoNotFoundException>()

        verify(exactly = 1) { produtoService.buscarProdutoPorId(idInexistente) }
    }

    @Test
    fun `deve validar produto antes de cadastrar`() {
        // Given
        val produtoInvalido = criarProdutoRequest().copy(nome = "")
        every { produtoService.cadastrarProduto(any()) } throws
                ProdutoInvalidoException(listOf("Nome é obrigatório"))


        expectCatching { controller.cadastrarProduto(produtoInvalido) }
            .isFailure()
            .isA<ProdutoInvalidoException>()


        verify(exactly = 1) { produtoService.cadastrarProduto(any(Produto::class)) }
    }

}