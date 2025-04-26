package domain.entities

import java.io.Serializable

data class Pedido(
    var id: Int?,
    var codigoCliente: Int?,
    var produtos: List<ProdutoQuantidade>,
    var horaInclusao: String,
    var estadoPedido: EstadoPedido,
) : Serializable {
    constructor(estadoPedido: EstadoPedido) : this(
        null,
        null,
        mutableListOf(),
        "",
        estadoPedido
    )
}

data class ProdutoQuantidade(
    var idProduto: Int? = null,
    var quantidade: Int? = null,
    var produto: Produto? = null
) : Serializable {
}