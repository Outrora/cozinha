package controller

import domain.entities.EstadoPedido
import domain.services.PedidoService
import helps.CriarMocksFila
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@ExtendWith(MockKExtension::class)
class PedidoContollerTest {

    @MockK
    private lateinit var pedidoService: PedidoService
    private lateinit var pedidoController: PedidoController


    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        pedidoController = PedidoController(pedidoService)
    }

    @Test
    fun `Deve Cadastrar Pedido Corretamente`() {
        val request = CriarMocksFila.criarPedidoRequest()

        justRun { pedidoService.cadastrarPedido(any(), any()) }

        pedidoController.cadastrarPedido(request)

        verify(exactly = 1) {
            pedidoService.cadastrarPedido(withArg {
                expectThat(it) {
                    get { codigoCliente }.isEqualTo(request.codigoCliente)
                    get { produtos }.hasSize(request.produtos.size)
                }
            }, withArg { expectThat(it).isEqualTo(request.codigoCliente) })
        }
    }

    @Test
    fun `Deve Editar Estado Pedido Corretamente`() {
        val idPedido = (1..100).random().toString()
        val estado = EstadoPedido.entries.toTypedArray().random()

        justRun { pedidoService.editarEstado(any(), any()) }

        pedidoController.editarEstado(idPedido, estado)

        verify(exactly = 1) {
            pedidoService.editarEstado(idPedido, estado)
        }
    }


}