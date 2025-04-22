package domain.services

import database.repository.ProdutoRepository
import domain.entities.Produto
import domain.services.base.BaseService
import exception.ProdutoInvalidoException
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.math.BigDecimal

@ApplicationScoped
class ProdutoService : BaseService<ProdutoRepository> {

    @Inject
    constructor(repository: ProdutoRepository) {
        this.repository = repository
    }


    fun cadastrarProduto(produto: Produto) {
        validacaoDeProduto(produto)
        repository.casdastrarProduto(produto)
    }

    fun editarProduto(produto: Produto) {
        validacaoDeProduto(produto, true)
        repository.editarProduto(produto)
    }

    private fun validacaoDeProduto(produto: Produto, isEdicao: Boolean = false) {

        val erros = mutableListOf<String>()

        if (isEdicao && produto.id == null) {
            erros.add("ID do produto é obrigatório para edição")
        }

        with(produto) {
            when {
                nome.isBlank() -> erros.add("Nome do produto não pode estar vazio")
                nome.length < 3 -> erros.add("Nome do produto deve ter no mínimo 3 caracteres")
                nome.length > 100 -> erros.add("Nome do produto deve ter no máximo 100 caracteres")
            }

            when {
                preco <= BigDecimal.ZERO -> erros.add("Preço do produto deve ser maior que zero")
                preco > BigDecimal("1000000.00") -> erros.add("Preço do produto excede o valor máximo permitido")
            }

            when {
                descricao.isBlank() -> erros.add("Descrição do produto não pode estar vazia")
                descricao.length > 500 -> erros.add("Descrição do produto deve ter no máximo 500 caracteres")
            }

            if (erros.isNotEmpty()) {
                throw ProdutoInvalidoException(erros)
            }
        }
    }

    fun listarProdutos(): List<Produto> {
        return repository.listarProdutos()
    }

    fun buscarProdutoPorId(id: Int): Produto? {
        return repository.buscarProdutoPorId(id)
    }

    fun bucarProdutoPorIds(ids: Set<Int>): List<Produto> {
        return repository.buscarProdutoPorId(ids)
    }

    fun deletarProduto(id: Int) {
        repository.deletarProduto(id)
    }
}