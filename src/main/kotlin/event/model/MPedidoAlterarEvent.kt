package event.model

import domain.entities.EstadoPedido

data class MPedidoAlterarEvent(val id: String, val estadoPedido: EstadoPedido) {
}