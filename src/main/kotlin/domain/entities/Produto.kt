package domain.entities

import java.math.BigDecimal
import java.math.RoundingMode

data class Produto(
    val nome: String,
    val descricao: String,
    val preco: BigDecimal,
    val categoria: Categoria,
    var id: Int?
) {
    init {
        preco.setScale(2, RoundingMode.HALF_EVEN)
    }
}