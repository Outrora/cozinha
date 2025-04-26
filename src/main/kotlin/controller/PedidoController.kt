package controller


import domain.entities.EstadoPedido
import domain.entities.Pedido
import domain.entities.ProdutoQuantidade
import domain.services.PedidoService
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import rest.request.PedidoRequest

@RequestScoped
class PedidoController @Inject constructor(var service: PedidoService) {

    fun cadastrarPedido(request: PedidoRequest) {
        val pedido = Pedido(EstadoPedido.EM_PREPARACAO)
        pedido.codigoCliente = request.codigoCliente
        pedido.produtos = request.produtos.map { ProdutoQuantidade(it.id, it.quantidade) }
        service.cadastrarPedido(pedido, request.codigoCliente ?: 0)
    }

    fun editarEstado(idPedido: Int, estado: EstadoPedido) {
        service.editarEstado(idPedido, estado)
    }


}