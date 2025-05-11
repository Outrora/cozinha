package extensoras

import database.dto.ProdutoDTO
import domain.entities.Produto
import event.model.ProdutoEvent


fun Produto.toDTO(): ProdutoDTO {
    return ProdutoDTO(
        nome = this.nome,
        descricao = this.descricao,
        preco = this.preco,
        categoria = this.categoria,
        id = this.id
    )
}

fun Produto.toEvent(): ProdutoEvent {
    return ProdutoEvent(
        id = this.id!!,
        nome = this.nome,
        preco = this.preco
    )
}
