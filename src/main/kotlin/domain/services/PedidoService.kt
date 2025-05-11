package domain.services

import adapter.FilaAdapter
import domain.entities.EstadoPedido
import domain.entities.Pedido
import event.producer.PedidoProducer
import exception.ErroValidacao
import exception.ProdutoInvalidoException
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class PedidoService @Inject constructor(private val fila: FilaAdapter, private val producer: PedidoProducer) {


    fun cadastrarPedido(pedido: Pedido, idCliente: Int = 0) {
        validacaoDePedido(pedido)
        fila.cadastrarPedido(pedido, idCliente)
    }

    fun editarEstado(idPedido: String, estado: EstadoPedido) {

        if (!estado.podeSerAlterado()) {
            throw ErroValidacao("Não estado do pedido pode ser alterado");
        }

        val pedido = fila.buscarPedidoPorId(idPedido)


        if (pedido.estadoPedido.podeSerAlterado()) {
            val mensagem =
                "Transição inválida: não é permitido mudar de '${pedido.estadoPedido.name}' para '${estado.name}'."
            throw ErroValidacao(mensagem)
        }

        fila.alterarStatusPedido(idPedido, estado);
        producer.alterarProduto(idPedido, estado)
    }

    private fun validacaoDePedido(pedido: Pedido) {

        with(pedido) {
            when {
                pedido.produtos.isEmpty() -> throw ProdutoInvalidoException(listOf("Pedido deve conter pelo menos um produto"))
            }
        }

    }
}