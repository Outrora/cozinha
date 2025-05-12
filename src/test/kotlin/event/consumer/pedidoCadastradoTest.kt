package event.consumer

import adapter.FilaAdapter
import controller.PedidoController
import domain.entities.EstadoPedido
import event.model.MPedidoAlterarEvent
import helps.CriarMocksFila.criarPedidoRequest

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
import strikt.api.expectThrows
import strikt.assertions.isFailure

@ExtendWith(MockKExtension::class)
class PedidoCadastradoTest {

    @MockK
    lateinit var controller: PedidoController

    @MockK
    lateinit var filaAdapter: FilaAdapter

    lateinit var pedidoCastadrado: PedidoCadastrado

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        pedidoCastadrado = PedidoCadastrado(controller, filaAdapter)
    }

    @Test
    fun `Deve cadastrar pedido corretamente`() {
        val request = criarPedidoRequest()

        justRun { controller.cadastrarPedido(any()) }

        pedidoCastadrado.processarCadastro(request);

        verify(exactly = 1) { pedidoCastadrado.processarCadastro(request) }
    }

    @Test
    fun `Deve mostrar erro que cadastra pedido der erro`() {
        val request = criarPedidoRequest()

        every { controller.cadastrarPedido(any()) } throws Exception("Erro ao cadastrar pedido")

        expectCatching { pedidoCastadrado.processarCadastro(request) }


        verify(exactly = 1) { pedidoCastadrado.processarCadastro(request) }
    }

    @Test
    fun `Deve alterar pedido corretamente`() {
        val event = MPedidoAlterarEvent("1", EstadoPedido.EM_PREPARACAO)

        justRun { filaAdapter.alterarStatusPedido(any(), any()) }

        pedidoCastadrado.processarAlteracao(event)
        verify(exactly = 1) { pedidoCastadrado.processarAlteracao(event) }


    }
}