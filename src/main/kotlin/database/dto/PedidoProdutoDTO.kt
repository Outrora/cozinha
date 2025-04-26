package database.dto

import jakarta.persistence.*

@Entity
@Table(name = "pedido_produto")
class PedidoProdutoDTO {

    @EmbeddedId
    lateinit var id: PedidoFilaIdDTO

    @ManyToOne
    @MapsId("pedido")
    @JoinColumn(name = "pedido_id")
    lateinit var pedido: PedidoDTO

    @ManyToOne
    @MapsId("produto")
    @JoinColumn(name = "produto_id")
    lateinit var produto: ProdutoDTO

    @JoinColumn(name = "quantidade_produto")
    var quantidade: Int? = null
}