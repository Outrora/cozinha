package domain.services

import adapter.FilaAdapter
import domain.entities.EstadoPedido
import domain.entities.Pedido
import exception.ProdutoInvalidoException
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject

@RequestScoped
class PedidoService @Inject constructor(private val fila: FilaAdapter) {


    fun cadastrarPedido(pedido: Pedido, idCliente: Int = 0) {
        validacaoDePedido(pedido)
        fila.cadastrarPedido(pedido, idCliente)
    }

    fun editarEstado(idPedido: Int, estado: EstadoPedido) {
        fila.alterarStatusPedido(idPedido, estado)
    }

    private fun validacaoDePedido(pedido: Pedido) {

        with(pedido) {
            when {
                pedido.produtos.isEmpty() -> throw ProdutoInvalidoException(listOf("Pedido deve conter pelo menos um produto"))
            }
        }

    }
}