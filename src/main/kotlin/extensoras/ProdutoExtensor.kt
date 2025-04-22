package extensoras

import database.dto.ProdutoDTO
import domain.entities.Produto


fun Produto.toDTO(): ProdutoDTO {
        return ProdutoDTO(
            nome = this.nome,
            descricao = this.descricao,
            preco = this.preco,
            categoria = this.categoria,
            id = this.id
        )
}
