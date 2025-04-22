package exception

class ProdutoNotFoundException(message: String = "Produto não encontrado") :
    RuntimeException(message)

class ProdutoInvalidoException(messages: List<String> = listOf("Produto inválido")) :
    RuntimeException(messages.joinToString("; "))