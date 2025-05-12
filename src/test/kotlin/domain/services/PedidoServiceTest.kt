package domain.services

import adapter.FilaAdapter
import domain.entities.EstadoPedido
import event.producer.PedidoProducer
import exception.ErroValidacao
import exception.ProdutoInvalidoException
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
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure

@ExtendWith(MockKExtension::class)
class PedidoServiceTest {

    @MockK
    private lateinit var fila: FilaAdapter

    @MockK
    private lateinit var producer: PedidoProducer

    private lateinit var service: PedidoService


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        service = PedidoService(fila, producer)
    }

    @Test
    fun `Deve cadastrar pedido com sucesso`() {
        val pedido = CriarMocksFila.criarPedido()

        justRun { fila.cadastrarPedido(any(), any()) }


        service.cadastrarPedido(pedido)

        verify {
            fila.cadastrarPedido(withArg {
                expectThat(it)
                    .isEqualTo(pedido)
            }, 0)
        }
    }

    @Test
    fun `Deve editar pedido com sucesso`() {
        val pedido = CriarMocksFila.criarPedido()

        pedido.estadoPedido = EstadoPedido.EM_PREPARACAO

        every { fila.buscarPedidoPorId(any()) } returns pedido
        justRun { producer.alterarProduto(any(), any()) }
        justRun { fila.alterarStatusPedido(any(),any()) }


        service.editarEstado(pedido.id!!, EstadoPedido.FINALIZADO)

        verify { fila.alterarStatusPedido(pedido.id!!, EstadoPedido.FINALIZADO) }
        verify { producer.alterarProduto(pedido.id!!, EstadoPedido.FINALIZADO) }
        verify { fila.alterarStatusPedido(any(),any()) }
    }

    @Test
    fun `Deve nao criar pedido com produtos vazios`() {
        val pedido = CriarMocksFila.criarPedido()
        pedido.produtos = emptyList()

        expectCatching {
            service.cadastrarPedido(pedido)
        }
            .isFailure()
            .isA<ProdutoInvalidoException>()
            .get {
                expectThat(this.message)
                    .isEqualTo("Pedido deve conter pelo menos um produto")
            }

    }


    @Test
    fun `Deve lancar exeção ao editar pedido`() {
        val pedido = CriarMocksFila.criarPedido()

        pedido.estadoPedido = EstadoPedido.PAGAMENTO_APROVADO

        every { fila.buscarPedidoPorId(any()) } returns pedido
        justRun { producer.alterarProduto(any(), any()) }
        justRun { fila.alterarStatusPedido(any(),any()) }


        expectCatching { service.editarEstado(pedido.id!!, EstadoPedido.PAGAMENTO_APROVADO) }
            .isFailure()
            .isA<ErroValidacao>()

        verify(exactly = 0) { fila.alterarStatusPedido(pedido.id!!, EstadoPedido.EM_PREPARACAO) }
        verify(exactly = 0) { producer.alterarProduto(pedido.id!!, EstadoPedido.EM_PREPARACAO) }
        verify(exactly = 0) { fila.alterarStatusPedido(any(),any()) }
    }

    @Test
    fun `Deve lancar exeção ao editar pedido quando nao pode ir para outro`() {
        val pedido = CriarMocksFila.criarPedido()

        pedido.estadoPedido = EstadoPedido.FINALIZADO

        every { fila.buscarPedidoPorId(any()) } returns pedido
        justRun { producer.alterarProduto(any(), any()) }
        justRun { fila.alterarStatusPedido(any(),any()) }


        expectCatching { service.editarEstado(pedido.id!!, EstadoPedido.EM_PREPARACAO) }
            .isFailure()
            .isA<ErroValidacao>()

        verify(exactly = 0) { fila.alterarStatusPedido(pedido.id!!, EstadoPedido.EM_PREPARACAO) }
        verify(exactly = 0) { producer.alterarProduto(pedido.id!!, EstadoPedido.EM_PREPARACAO) }
        verify(exactly = 0) { fila.alterarStatusPedido(any(),any()) }
    }

}