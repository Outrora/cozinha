package extensoras

import database.dto.ProdutoDTO
import domain.entities.Produto


fun ProdutoDTO.toProduto(): Produto {
    return Produto(
        nome = this.nome,
        descricao = this.descricao,
        preco = this.preco,
        categoria = this.categoria,
        id = this.id
    )
}