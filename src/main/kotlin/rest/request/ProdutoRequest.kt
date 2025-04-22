package rest.request

import com.fasterxml.jackson.annotation.JsonProperty
import domain.entities.Categoria
import domain.entities.Produto
import java.io.Serializable
import java.math.BigDecimal

data class ProdutoRequest(
    @JsonProperty("nome")
    val nome: String,

    @JsonProperty("descricao")
    val descricao: String,

    @JsonProperty("preco")
    val preco: BigDecimal,

    @JsonProperty("categoria")
    val categoria: Categoria
) : Serializable

fun ProdutoRequest.toProduto(): Produto {
    return Produto(
        nome = this.nome,
        descricao = this.descricao,
        preco = this.preco,
        categoria = this.categoria,
        id = null
    )
}