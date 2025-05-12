package event.model

import java.math.BigDecimal

data class ProdutoEvent(
    val id: Int,
    val nome: String,
    val preco: BigDecimal
)