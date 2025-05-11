package event.model

import domain.entities.EstadoPedido

data class MPedidoAlterarEvent(var id: String, var estadoPedido: EstadoPedido) {
}