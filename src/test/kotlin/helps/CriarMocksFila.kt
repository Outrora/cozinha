package helps

import database.dto.PedidoDTO
import database.dto.PedidoFilaIdDTO
import database.dto.PedidoProdutoDTO
import database.help.ProdutosIds
import domain.entities.EstadoPedido
import domain.entities.Fila
import domain.entities.Pedido
import domain.entities.ProdutoQuantidade
import extensoras.toDTO
import rest.request.PedidoProdutoRequest
import rest.request.PedidoRequest
import java.time.LocalDateTime

object CriarMocksFila {

    fun criarListaProdutoDTO(quantidade: Int = 5): List<PedidoDTO> {
        return List(quantidade) { criarPedidoDTO() }
    }

    fun criarPedidoDTO(): PedidoDTO {
        val pedido = PedidoDTO()
        pedido.id = (1..1000).random().toString()
        pedido.estadoPedido = EstadoPedido.entries.toTypedArray().random()
        pedido.produtos = List((1..15).random()) { criarPedidoProdutoDTO(pedido) }
        pedido.codigoCliente = (1..1000).random()
        pedido.dataCriacao = LocalDateTime.now().minusDays((0..30).random().toLong())
        return pedido
    }

    fun criarPedidoProdutoDTO(pedido: PedidoDTO): PedidoProdutoDTO {
        val id = PedidoFilaIdDTO(
            pedido = (1..1000).random().toString(),
            produto = (1..1000).random()
        )
        val dto = PedidoProdutoDTO()
        dto.id = id
        dto.quantidade = (1..10).random();
        dto.produto = CriarMocksProduto.criarProduto().toDTO()
        dto.pedido = pedido

        return dto
    }

    fun criarFila(quantidadeProduto: Int = 10): Fila {
        val list = mutableListOf<Pedido>()

        for (i in 1..quantidadeProduto) {
            list.add(criarPedido())
        }
        return Fila(
            listaPedidos = list,
            diaFila = "2023-10-01"
        )
    }

    fun criarPedido(): Pedido {

        val produto: List<ProdutoQuantidade> = criarListaProdutoQuantidade()

        return Pedido(
            id = (1..1000).random().toString(),
            codigoCliente = (1..1000).random(),
            produtos = produto,
            horaInclusao = "2023-10-01T10:00:00",
            estadoPedido = EstadoPedido.entries.toTypedArray().random()
        )
    }

    fun criarListaProdutoQuantidade(): List<ProdutoQuantidade> {
        return CriarMocksProduto.criarListaComProdutos().map { it ->
            ProdutoQuantidade(
                idProduto = it.id,
                quantidade = (1..10).random(),
                produto = it
            )
        }
    }

    fun criarProdutosIds(): List<ProdutosIds> {
        return criarListaProdutoQuantidade().map { it ->
            ProdutosIds(
                idProduto = it.idProduto!!,
                quantidade = it.quantidade!!
            )
        }
    }

    fun criarPedidoRequest(): PedidoRequest {
        val pedidoProdutos = List(
            (1..15)
                .random()
        )
        { PedidoProdutoRequest((1..10).random(), (1..10).random()) }

        return PedidoRequest(
            (100..1000).random().toString(),
            (1..1000).random(),
            pedidoProdutos
        )
    }
}