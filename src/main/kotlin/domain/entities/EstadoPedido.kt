package domain.entities


enum class EstadoPedido(
    private val podeSerAlterado: Boolean,
    private val proximosEstados: List<EstadoPedido>
) {
    FINALIZADO(true, listOf<EstadoPedido>()),
    CANCELADO(true, listOf<EstadoPedido>()),
    EM_PREPARACAO(true, listOf(CANCELADO, FINALIZADO)),
    PAGAMENTO_APROVADO(false, listOf(EM_PREPARACAO, CANCELADO)),
    PAGAMENTO_RECUSADO(false, listOf(PAGAMENTO_APROVADO, CANCELADO)),
    PEDIDO_CADASTRADO(false, listOf(PAGAMENTO_APROVADO, PAGAMENTO_RECUSADO));

    fun podeTransitarPara(novoEstado: EstadoPedido?): Boolean {
        return proximosEstados.contains(novoEstado)
    }

    fun podeSerAlterado(): Boolean {
        return podeSerAlterado
    }


    companion object {
        fun fromString(value: String): EstadoPedido {
            return valueOf(value.uppercase())
        }
    }
}

