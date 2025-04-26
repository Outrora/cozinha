package adapter

import database.dto.PedidoDTO
import database.help.ProdutosIds
import database.repository.PedidoRepository
import domain.entities.EstadoPedido
import domain.entities.Fila
import domain.entities.Pedido
import domain.entities.ProdutoQuantidade
import exception.ProdutoInvalidoException
import extensoras.formatoData
import extensoras.toDTO
import extensoras.toPedido
import extensoras.toProduto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@ApplicationScoped
class FilaBancoPostgress @Inject constructor(private val repository: PedidoRepository) : FilaAdapter {


    override fun cadastrarPedido(pedido: Pedido, idCliente: Int) {
        val pedidoDTO = pedido.toDTO()
        pedidoDTO.codigoCliente = idCliente;

        val produtos: List<ProdutosIds> = pedido.produtos.map { produto ->
            ProdutosIds(
                produto.idProduto ?: throw ProdutoInvalidoException(listOf("ID do produto não pode ser nulo")),
                produto.quantidade ?: throw ProdutoInvalidoException(listOf("Quantidade do produto não pode ser nula")),
            )
        }

        repository.cadastrarPedido(pedidoDTO, produtos)
    }


    override fun listarFilaPedidoHoje(): Fila {
        val dataHoje = formatarData(LocalDate.now())
        val pedidos = repository.buscarPedidoPelaData(dataHoje)
        return criarFila(pedidos, dataHoje)
    }

    override fun listarFilaPedidoHojeEmPreparacao(): Fila {
        val dataHoje = formatarData(LocalDate.now())
        val pedidos = repository.buscarPedidoPelaDataEPreparacao(dataHoje, EstadoPedido.EM_PREPARACAO)
        return criarFila(pedidos, dataHoje)
    }

    override fun listarFilaPorDia(data: Date): Fila {
        val dataFormatada = formatarData(data.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDate())
        val pedidos = repository.buscarPedidoPelaData(dataFormatada)
        return criarFila(pedidos, dataFormatada)
    }

    override fun alterarStatusPedido(idPedido: Int, status: EstadoPedido) {
        repository.alterarEstadoPedido(idPedido, status)
    }

    private fun criarFila(pedidos: List<PedidoDTO>, data: String): Fila {
        val pedidos = pedidos.map { pedidoDTO ->
            pedidoDTO.toPedido().apply {
                produtos =
                    pedidoDTO.produtos.map { ProdutoQuantidade(it.produto.id, it.quantidade, it.produto.toProduto()) }
            }
        }
        return Fila(pedidos, data)
    }

    private fun formatarData(data: LocalDate): String {
        return data.format(DateTimeFormatter.ofPattern(formatoData))
    }
}