package event.producer

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
class ProdutoProducerTest {

    @MockK
    private lateinit var emiter: Emitter<ProdutoEvent>

    private  lateinit var producer: ProdutoProducer;

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        producer = criarProdutoProducer()
    }


    private fun criarProdutoProducer(): ProdutoProducer {
        val classe = ProdutoProducer();
        val field = ProdutoProducer::class.memberProperties.find { it.name == "emiter" }
                as? KMutableProperty1<ProdutoProducer, Any>
        field?.isAccessible = true
        field?.set(classe, emiter)
        return classe
    }

    @Test
    fun `Deve cadastrar Produto Corretamente`() {
        val produto = criarProduto().copy(id = 1)
        every { emiter.send(any()) } returns CompletableFuture.completedFuture(null)
        producer.cadastarProduto(produto)
        verify (exactly = 1) { emiter.send(produto.toEvent()) }
    }

    @Test
    fun `Deve mostar erro o cadastrar Produto`() {
        val produto = criarProduto().copy(id = 1)
        val futureComErro = CompletableFuture<Void?>()
        futureComErro.completeExceptionally(RuntimeException("Falha de envio"))
        every { emiter.send(any()) } returns futureComErro
        producer.cadastarProduto(produto)
        verify (exactly = 1) { emiter.send(produto.toEvent()) }
    }


}