package database.repository


import database.dto.ProdutoDTO
import domain.entities.Produto
import exception.ProdutoNotFoundException
import extensoras.toDTO
import extensoras.toProduto
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ProdutoRepository : PanacheRepository<ProdutoDTO> {

    @Transactional
    fun casdastrarProduto(produto: Produto) {
        persist(produto.toDTO())
    }

    @Transactional
    fun editarProduto(produto: Produto) {
        val produtoDTO = findById(produto.id!!.toLong())

        produtoDTO?.apply {
            produtoDTO.nome = produto.nome
            produtoDTO.descricao = produto.descricao
            produtoDTO.preco = produto.preco
            produtoDTO.categoria = produto.categoria
            persist(this)
        } ?: throw ProdutoNotFoundException()

    }

    fun listarProdutos(): List<Produto> {
        return findAll().list().map { it.toProduto() }
    }

    fun buscarProdutoPorId(id: Int): Produto? {
        return findById(id.toLong())?.toProduto()
    }

    @Transactional
    fun deletarProduto(id: Int) {
        val produtoDTO = findById(id.toLong())
        produtoDTO?.let {
            delete(it)
        } ?: throw ProdutoNotFoundException()
    }

    fun buscarProdutoPorId(ids: Set<Int>): List<Produto> {
        return find("id IN ?1", ids)
            .stream()
            .map { it.toProduto() }
            .toList()
    }

}