package controller


import domain.entities.EstadoPedido
import domain.entities.Pedido
import domain.entities.ProdutoQuantidade
import domain.services.PedidoService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import rest.request.PedidoRequest

@ApplicationScoped
class PedidoController @Inject constructor(private var service: PedidoService) {

    fun cadastrarPedido(request: PedidoRequest) {
        val pedido = Pedido(EstadoPedido.PEDIDO_CADASTRADO)
        pedido.id = request.id
        pedido.codigoCliente = request.codigoCliente
        pedido.produtos = request.produtos.map { ProdutoQuantidade(it.id, it.quantidade) }
        service.cadastrarPedido(pedido, request.codigoCliente ?: 0)
    }

    fun editarEstado(idPedido: String, estado: EstadoPedido) {
        service.editarEstado(idPedido, estado)
    }


}