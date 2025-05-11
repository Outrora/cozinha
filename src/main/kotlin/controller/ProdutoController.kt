package controller

import controller.base.ControllerBase
import domain.entities.Produto
import domain.services.ProdutoService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import rest.request.ProdutoRequest
import rest.request.toProduto

@ApplicationScoped
class ProdutoController @Inject constructor(override  var service: ProdutoService) : ControllerBase<ProdutoService> {

    fun cadastrarProduto(produto: ProdutoRequest) {
        service.cadastrarProduto(produto.toProduto())
    }

    fun listarProdutos(): List<Produto> {
        return service.listarProdutos()
    }

    fun editarProduto(produto: ProdutoRequest, id: Int) {
        val produto = produto.toProduto()
        produto.id = id;
        service.editarProduto(produto)
    }

    fun deletarProduto(id: Int) {
        service.deletarProduto(id)
    }

    fun buscarProdutoPorId(id: Int): Produto? {
        return service.buscarProdutoPorId(id)
    }

    fun buscarProdutoPorIds(ids: Set<Int>): List<Produto> {
        return service.bucarProdutoPorIds(ids)
    }
}