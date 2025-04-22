package domain.entities

import java.math.BigDecimal
import java.math.RoundingMode

data class Produto(
    var nome: String,
    var descricao: String,
    var preco: BigDecimal,
    var categoria: Categoria,
    var id: Int?
) {
    init {
        preco.setScale(2, RoundingMode.HALF_EVEN)
    }
}