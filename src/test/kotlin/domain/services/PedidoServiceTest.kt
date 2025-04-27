package domain.services

import adapter.FilaAdapter
import exception.ProdutoInvalidoException
import helps.CriarMocksFila
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure

class PedidoServiceTest {

    @MockK
    private lateinit var fila: FilaAdapter
    private lateinit var service: PedidoService


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        service = PedidoService(fila)
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

        justRun { fila.alterarStatusPedido(any(), any()) }


        service.editarEstado(pedido.id!!, pedido.estadoPedido)

        verify { fila.alterarStatusPedido(pedido.id!!, pedido.estadoPedido) }
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
}