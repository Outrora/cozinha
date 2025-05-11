package rest.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class PedidoRequest(

    @JsonProperty("id_produto")
    var id: String,

    @JsonProperty("codigoCliente")
    var codigoCliente: Int? = null,

    @JsonProperty("lista_produtos")
    var produtos: List<PedidoProdutoRequest>,

    ) : Serializable

data class PedidoProdutoRequest(
    @JsonProperty("id")
    var id: Int? = null,

    @JsonProperty("quantidade")
    var quantidade: Int? = null,

    ) : Serializable