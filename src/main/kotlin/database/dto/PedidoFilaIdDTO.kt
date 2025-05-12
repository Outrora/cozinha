package database.dto

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class PedidoFilaIdDTO(
    var pedido: String? = null,
    var produto: Int? = null
) : Serializable