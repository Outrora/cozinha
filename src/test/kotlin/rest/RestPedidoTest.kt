package rest

import controller.PedidoController
import domain.entities.EstadoPedido
import helps.CriarMocksFila
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import jakarta.ws.rs.core.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import rest.util.ApiResponse
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

@ExtendWith(MockKExtension::class)
class RestPedidoTest {

    @MockK
    private lateinit var controller: PedidoController

    private lateinit var restPedido: RestPedido

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
        restPedido = RestPedido(controller)
    }

    @Test
    fun `Deve cadastrar pedido corretamente`() {
        val pedidoRequest = CriarMocksFila.criarPedidoRequest()
        justRun { controller.cadastrarPedido(any()) }

        val response = restPedido.cadastrarPedido(pedidoRequest)

        expectThat(response)
            .isNotNull()
            .isA<Response>()
            .get {
                expectThat(status)
                    .isEqualTo(Response.Status.CREATED.statusCode)

                expectThat(entity)
                    .isA<ApiResponse>()
                    .get { expectThat(message).isNotNull().isEqualTo("Pedido cadastrado com sucesso") }
            }
    }

    @Test
    fun `Deve editar pedido corretamente`() {
        justRun { controller.editarEstado(any(), any()) }

        val response = restPedido.editarEstado(1, EstadoPedido.EM_PREPARACAO)

        expectThat(response)
            .isNotNull()
            .isA<Response>()
            .get {
                expectThat(status)
                    .isEqualTo(Response.Status.OK.statusCode)

                expectThat(entity)
                    .isA<ApiResponse>()
                    .get { expectThat(message).isNotNull().isEqualTo("Estado do pedido editado com sucesso") }
            }
    }


}