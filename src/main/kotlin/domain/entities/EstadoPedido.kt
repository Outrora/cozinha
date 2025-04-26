package domain.entities

enum class EstadoPedido {

    PENDENTE,
    EM_PREPARACAO,
    PRONTO,
    RETIRADO,
    CANCELADO;

    companion object {
        fun fromString(value: String): EstadoPedido {
            return valueOf(value.uppercase())
        }
    }
}