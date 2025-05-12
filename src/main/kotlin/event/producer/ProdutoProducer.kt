package event.producer

import domain.entities.Produto
import event.model.ProdutoEvent
import extensoras.toEvent
import io.smallrye.reactive.messaging.annotations.Broadcast
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory

class ProdutoProducer {

    @Inject
    @Channel("produto-cadastrado")
    @Broadcast
    private lateinit var emiter: Emitter<ProdutoEvent>


    fun cadastarProduto(produto: Produto) {
        emiter.send(produto.toEvent()).thenRun {
            log.info("Produto cadastrado com sucesso: ${produto.nome}")
        }.exceptionally {
            log.error("Erro ao cadastrar produto: ${produto.nome}", it)
            throw it
        }

    }

    companion object {
        private val log = LoggerFactory.getLogger(ProdutoProducer::class.java)
    }

}