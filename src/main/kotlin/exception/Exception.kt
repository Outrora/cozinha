package exception

class ProdutoNotFoundException( override val message: String = "Produto não encontrado") :
    RuntimeException(message)

class ProdutoInvalidoException(messages: List<String> = listOf("Produto inválido")) :
    RuntimeException(messages.joinToString("; "))


class PedidoNotFoundException(override val message: String = "Pedido não encontrado") :
    RuntimeException(message)

class ErroValidacao(override val message: String = "Erro de validação") :
    RuntimeException(message)