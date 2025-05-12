package extensoras

import database.dto.PedidoDTO
import domain.entities.Pedido
import java.time.format.DateTimeFormatter

fun PedidoDTO.toPedido(): Pedido {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val pedido = Pedido(this.estadoPedido);
    pedido.codigoCliente = this.codigoCliente
    pedido.id = this.id
    pedido.horaInclusao = formatter.format(this.dataCriacao)

    return pedido

}