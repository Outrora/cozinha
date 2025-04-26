package adapter

import domain.entities.EstadoPedido
import domain.entities.Fila
import domain.entities.Pedido
import java.util.*

interface FilaAdapter {

    fun cadastrarPedido(pedido: Pedido, idCliente: Int = 0)
    fun listarFilaPedidoHoje(): Fila
    fun listarFilaPedidoHojeEmPreparacao(): Fila
    fun listarFilaPorDia(data: Date): Fila
    fun alterarStatusPedido(idPedido: Int, status: EstadoPedido)


}