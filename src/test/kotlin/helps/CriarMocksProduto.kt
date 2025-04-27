package helps

import domain.entities.Categoria
import domain.entities.Produto
import rest.request.ProdutoRequest

object CriarMocksProduto {

    private val nomes = listOf(
        "X-Burger", "Pizza", "Batata Frita",
        "Refrigerante", "Suco", "Sobremesa"
    )

    private val descricoes = listOf(
        "Produto muito bom",
        "Produto especial",
        "Produto destaque do dia"
    )

    fun criarProdutoRequest(): ProdutoRequest {
        return ProdutoRequest(
            nome = nomes.random(),
            descricao = descricoes.random(),
            preco = (1..1000).random().toBigDecimal(),
            categoria = Categoria.entries.toTypedArray().random()
        )
    }

    fun criarTextoAleatorio(tamanho: Int): String {
        val caracteres = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..tamanho)
            .map { caracteres.random() }
            .joinToString("")
    }

    fun criarProdutoComId(): Produto {
        val produto = criarProduto()
        produto.id = (1..1000).random()
        return produto
    }

    fun criarProduto(): Produto {
        return Produto(
            nome = nomes.random(),
            descricao = descricoes.random(),
            preco = (1..1000).random().toBigDecimal(),
            categoria = Categoria.entries.toTypedArray().random(),
            id = null
        )
    }

    fun criarListaComProdutos(): List<Produto> {
        return List((1..15).random()) { criarProdutoComId() }
    }


}

