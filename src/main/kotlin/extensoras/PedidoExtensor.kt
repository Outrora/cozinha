package extensoras

import database.dto.PedidoDTO
import domain.entities.Pedido
import java.time.LocalDateTime

fun Pedido.toDTO(): PedidoDTO {
    val pedidoDTO = PedidoDTO();

    pedidoDTO.id = this.id
    pedidoDTO.codigoCliente = this.codigoCliente
    pedidoDTO.estadoPedido = this.estadoPedido
    pedidoDTO.dataCriacao = LocalDateTime.now()

    return pedidoDTO

   
}