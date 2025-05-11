package database.repository

import database.dto.PedidoDTO
import database.help.ProdutosIds
import domain.entities.EstadoPedido
import domain.entities.Pedido
import exception.PedidoNotFoundException
import exception.ProdutoInvalidoException
import extensoras.paraLocalDateTimeFim
import extensoras.paraLocalDateTimeInicio
import extensoras.toPedido
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PedidoRepository : PanacheRepository<PedidoDTO> {

    fun buscarPedidoPorId(id: String): Pedido {
        val pedido = find("id", id).firstResult() ?: throw PedidoNotFoundException()

        return pedido.toPedido()
    }

    @Transactional
    fun cadastrarPedido(pedido: PedidoDTO, produtos: List<ProdutosIds>) {
        persist(pedido)
        cadastarProdutoPedido(pedido.id!!, produtos)
    }

    private fun cadastarProdutoPedido(pedidoid: String, produtos: List<ProdutosIds>) {
        val enitity = getEntityManager();
        val query = """
                INSERT INTO pedido_produto
                (quantidade, produto_id, pedido_id)
                VALUES (:quantidade, :produto, :pedido)
                """
            .trimIndent()

        try {
            produtos.forEach { produto ->
                enitity.createNativeQuery(query).apply {
                    setParameter("quantidade", produto.quantidade)
                    setParameter("produto", produto.idProduto)
                    setParameter("pedido", pedidoid)
                    executeUpdate()
                }
            }
        } catch (_: Exception) {
            throw ProdutoInvalidoException(listOf("Erro ao cadastrar pedido"))
        }

    }

    fun buscarPedidoPelaData(data: String): List<PedidoDTO> {
        return find(
            "dataCriacao > ?1 and dataCriacao < ?2",
            data.paraLocalDateTimeInicio(),
            data.paraLocalDateTimeFim()
        ).list()
    }

    fun buscarPedidoPelaDataEPreparacao(data: String, estado: EstadoPedido): List<PedidoDTO> {
        return find(
            "dataCriacao > ?1 and dataCriacao < ?2 and estadoPedido =?3",
            data.paraLocalDateTimeInicio(),
            data.paraLocalDateTimeFim(),
            estado
        )
            .list()
    }

    @Transactional
    fun alterarEstadoPedido(idPedido: String, estado: EstadoPedido) {
        val pedido = find("id", idPedido).firstResult()

        pedido?.apply {
            pedido.estadoPedido = estado
            persist(this)
        } ?: throw PedidoNotFoundException()

    }


}