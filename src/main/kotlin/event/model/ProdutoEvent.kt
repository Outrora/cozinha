package event.model

import java.math.BigDecimal

data class ProdutoEvent(
    var id: Int,
    var nome: String,
    var preco: BigDecimal
)