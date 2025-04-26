package database.dto

import domain.entities.EstadoPedido
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pedido")
class PedidoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null;

    lateinit var dataCriacao: LocalDateTime

    @JoinColumn(name = "cliente_id", nullable = true)
    var codigoCliente: Int? = null

    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var produtos: List<PedidoProdutoDTO> = mutableListOf();

    @Enumerated(EnumType.STRING)
    lateinit var estadoPedido: EstadoPedido

    constructor()

    constructor(
        produtos: List<PedidoProdutoDTO>,
        estadoPedido: EstadoPedido,
        codigoCliente: Int?,
        dataCriacao: LocalDateTime,
        id: Int?
    ) {
        this.produtos = produtos
        this.estadoPedido = estadoPedido
        this.codigoCliente = codigoCliente
        this.dataCriacao = dataCriacao
        this.id = id
    }
}