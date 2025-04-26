package database.dto

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class PedidoFilaIdDTO(
    var pedido: Int? = null,
    var produto: Int? = null
) : Serializable