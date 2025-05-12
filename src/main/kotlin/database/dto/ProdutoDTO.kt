package database.dto

import domain.entities.Categoria
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "produto")
class ProdutoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    lateinit var nome: String
    lateinit var descricao: String
    lateinit var preco: BigDecimal

    @Enumerated(EnumType.STRING)
    lateinit var categoria: Categoria

    constructor(id: Int?, nome: String, descricao: String, preco: BigDecimal, categoria: Categoria) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.preco = preco
        this.categoria = categoria
    }

    constructor() {}
}

