package event.producer

import domain.entities.EstadoPedido
import event.model.MPedidoAlterarEvent
import event.model.ProdutoEvent
import extensoras.toEvent
import helps.CriarMocksProduto.criarProduto
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@ExtendWith(MockKExtension::class)
class PedidoProducerTest {

    @MockK
    private lateinit var emiter: Emitter<MPedidoAlterarEvent>

    private  lateinit var producer: PedidoProducer;

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        producer = criarProducer()
    }


    private fun criarProducer(): PedidoProducer {
        val classe = PedidoProducer();
        val field = PedidoProducer::class.memberProperties.find { it.name == "emiter" }
                as? KMutableProperty1<PedidoProducer, Any>
        field?.isAccessible = true
        field?.set(classe, emiter)
        return classe
    }

    @Test
    fun `Deve alterar o estado Pedido Corretamente`() {
        val pedido = MPedidoAlterarEvent("1", EstadoPedido.EM_PREPARACAO)
        every { emiter.send(any()) } returns CompletableFuture.completedFuture(null)
        producer.alterarProduto("1", EstadoPedido.EM_PREPARACAO)
        verify (exactly = 1) { emiter.send(pedido) }
    }

    @Test
    fun `Deve mostar erro o cadastrar Produto`() {
        val pedido = MPedidoAlterarEvent("1", EstadoPedido.EM_PREPARACAO)
        val futureComErro = CompletableFuture<Void?>()
        futureComErro.completeExceptionally(RuntimeException("Falha de envio"))
        every { emiter.send(any()) } returns futureComErro
        producer.alterarProduto("1", EstadoPedido.EM_PREPARACAO)
        verify (exactly = 1) { emiter.send(pedido) }
    }


}