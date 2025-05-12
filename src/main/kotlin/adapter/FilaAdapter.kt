package adapter

import domain.entities.EstadoPedido
import domain.entities.Fila
import domain.entities.Pedido
import java.util.*

interface FilaAdapter {

    fun cadastrarPedido(pedido: Pedido, idCliente: Int = 0)
    fun buscarPedidoPorId(id: String): Pedido
    fun listarFilaPedidoHoje(): Fila
    fun listarFilaPedidoHojeEmPreparacao(): Fila
    fun listarFilaPorDia(data: Date): Fila
    fun alterarStatusPedido(idPedido: String, status: EstadoPedido)


}