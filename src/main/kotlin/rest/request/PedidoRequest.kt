package rest.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class PedidoRequest(

    @JsonProperty("id_produto")
    val id: String,

    @JsonProperty("codigoCliente")
    val codigoCliente: Int? = null,

    @JsonProperty("lista_produtos")
    val produtos: List<PedidoProdutoRequest>,

    ) : Serializable

data class PedidoProdutoRequest(
    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("quantidade")
    val quantidade: Int? = null,

    ) : Serializable