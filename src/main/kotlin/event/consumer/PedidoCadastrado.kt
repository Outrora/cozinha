package event.consumer

import adapter.FilaAdapter
import controller.PedidoController
import event.model.MPedidoAlterarEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Incoming
import rest.request.PedidoRequest
import java.util.logging.Logger

@ApplicationScoped
class PedidoCadastrado @Inject constructor(
    private val controller: PedidoController,
    private val filaAdapter: FilaAdapter
) {

    private val LOG: Logger = Logger.getLogger(this::class.simpleName);

    @Incoming("pedido-cadastro")
    fun processarCadastro(pedidoRequest: PedidoRequest) {
        LOG.info("Recebendo pedido cadastrado: $pedidoRequest")
        try {
            controller.cadastrarPedido(pedidoRequest)
            LOG.info("Pedido cadastrado com sucesso: $pedidoRequest")
        } catch (e: Exception) {
            LOG.severe("Erro ao cadastrar pedido: ${e.message}")
        }
    }

    @Incoming("pedido-alterado-central")
    fun processarAlteracao(model: MPedidoAlterarEvent) {
        LOG.info("Recebendo pedido alterado :$model")
        filaAdapter.alterarStatusPedido(model.id, model.estadoPedido)
    }
}