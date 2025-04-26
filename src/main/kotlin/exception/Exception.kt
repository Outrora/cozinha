package exception

class ProdutoNotFoundException(message: String = "Produto não encontrado") :
    RuntimeException(message)

class ProdutoInvalidoException(messages: List<String> = listOf("Produto inválido")) :
    RuntimeException(messages.joinToString("; "))


class PedidoNotFoundException(message: String = "Pedido não encontrado") :
    RuntimeException(message)