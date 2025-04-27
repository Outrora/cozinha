package adapter

import database.dto.PedidoDTO
import database.repository.PedidoRepository
import domain.entities.EstadoPedido
import domain.entities.Fila
import exception.ProdutoInvalidoException
import extensoras.paraData
import helps.CriarMocksFila
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isNotNull

@ExtendWith(MockKExtension::class)
class FilaBancoPostgressTest {

    @MockK
    private lateinit var pedidoRepository: PedidoRepository
    private lateinit var filaBancoPostgress: FilaBancoPostgress

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        filaBancoPostgress = FilaBancoPostgress(pedidoRepository);
    }

    @Test
    fun `Deve Cadastrar Pedido Corretamente`() {
        val pedido = CriarMocksFila.criarPedido()
        justRun {
            pedidoRepository
                .cadastrarPedido(
                    any(PedidoDTO::class),
                    any()
                )
        }

        filaBancoPostgress.cadastrarPedido(pedido, 1)

        verify {
            pedidoRepository.cadastrarPedido(
                any(),
                withArg { expectThat(it).isNotNull().hasSize(pedido.produtos.size) }
            )
        }
    }

    @Test
    fun `Nao deve Cadastrar Pedido e retornar ProdutoInvalidoException`() {
        val pedido = CriarMocksFila.criarPedido()
        pedido.produtos[0].idProduto = null
        justRun {
            pedidoRepository
                .cadastrarPedido(
                    any(PedidoDTO::class),
                    any()
                )
        }

        expectThrows<ProdutoInvalidoException> { filaBancoPostgress.cadastrarPedido(pedido, 1) }
            .isA<ProdutoInvalidoException>()
            .isNotNull()

        verify(exactly = 0) {
            pedidoRepository.cadastrarPedido(
                any(),
                any()
            )
        }
    }

    @Test
    fun `Nao deve Cadastrar Pedido e retornar ProdutoInvalidoException quantidade produto`() {
        val pedido = CriarMocksFila.criarPedido()
        pedido.produtos[0].quantidade = null
        justRun {
            pedidoRepository
                .cadastrarPedido(
                    any(PedidoDTO::class),
                    any()
                )
        }

        expectThrows<ProdutoInvalidoException> { filaBancoPostgress.cadastrarPedido(pedido, 1) }
            .isA<ProdutoInvalidoException>()
            .isNotNull()

        verify(exactly = 0) {
            pedidoRepository.cadastrarPedido(
                any(),
                any()
            )
        }
    }

    @Test
    fun `Deve Listar Fila de Pedido Hoje`() {
        val fila = CriarMocksFila.criarListaProdutoDTO();

        every {
            pedidoRepository
                .buscarPedidoPelaData(any())
        } returns fila

        val resposta = filaBancoPostgress.listarFilaPedidoHoje()

        expectThat(resposta)
            .isNotNull()
            .isA<Fila>()
            .get { listaPedidos }
            .hasSize(fila.size)


        verify(exactly = 1) {
            pedidoRepository.buscarPedidoPelaData(withArg {
                expectThat(it)
                    .isNotNull()
                    .isA<String>()
            })
        }
    }

    @Test
    fun `Deve Listar Fila de Pedido Hoje em Preparação`() {
        val fila = CriarMocksFila.criarListaProdutoDTO();

        every {
            pedidoRepository
                .buscarPedidoPelaDataEPreparacao(any(), any())
        } returns fila

        val resposta = filaBancoPostgress.listarFilaPedidoHojeEmPreparacao()

        expectThat(resposta)
            .isNotNull()
            .isA<Fila>()
            .get { listaPedidos }
            .hasSize(fila.size)


        verify(exactly = 1) {
            pedidoRepository.buscarPedidoPelaDataEPreparacao(withArg {
                expectThat(it)
                    .isNotNull()
                    .isA<String>()
            }, EstadoPedido.EM_PREPARACAO)
        }
    }

    @Test
    fun `Deve Listar Fila de Pedido por Dia`() {
        val fila = CriarMocksFila.criarListaProdutoDTO();

        every {
            pedidoRepository
                .buscarPedidoPelaData(any())
        } returns fila

        val resposta = filaBancoPostgress.listarFilaPorDia("2023-10-01".paraData())

        expectThat(resposta)
            .isNotNull()
            .isA<Fila>()
            .get { listaPedidos }
            .hasSize(fila.size)


        verify(exactly = 1) {
            pedidoRepository.buscarPedidoPelaData(any())
        }
    }

    @Test
    fun `Deve Alterar Status Pedido`() {
        val idPedido = 1
        val estado = EstadoPedido.entries.toTypedArray().random()

        justRun {
            pedidoRepository.alterarEstadoPedido(idPedido, estado)
        }

        filaBancoPostgress.alterarStatusPedido(idPedido, estado)

        verify(exactly = 1) {
            pedidoRepository.alterarEstadoPedido(idPedido, estado)
        }
    }


}