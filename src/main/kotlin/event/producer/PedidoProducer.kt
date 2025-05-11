package event.producer

import domain.entities.EstadoPedido
import event.model.MPedidoAlterarEvent
import io.smallrye.reactive.messaging.annotations.Broadcast
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory

@ApplicationScoped
class PedidoProducer {

    @Inject
    @Channel("pedido-alterado-produto")
    @Broadcast
    private lateinit var emiter: Emitter<MPedidoAlterarEvent>

    fun alterarProduto(id: String, estado: EstadoPedido) {
        val event = MPedidoAlterarEvent(id, estado)
        emiter.send(event).thenRun {
            log.info("Pedido alterado com sucesso: ${event.id}")
        }.exceptionally {
            log.error("Erro ao cadastrar produto: ${event.id}", it)
            throw it
        }

    }

    companion object {
        private val log = LoggerFactory.getLogger(ProdutoProducer::class.java)
    }

}