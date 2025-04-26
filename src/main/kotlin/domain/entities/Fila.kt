package domain.entities

data class Fila(
    val listaPedidos: List<Pedido>,
    val diaFila: String
) {
}