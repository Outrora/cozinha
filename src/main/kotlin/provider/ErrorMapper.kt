package provider

import exception.ProdutoInvalidoException
import exception.ProdutoNotFoundException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.ResponseStatus
import rest.util.criarResponse

@Provider
class ErrorMapper : ExceptionMapper<Exception> {

    @ResponseStatus(500)
    override fun toResponse(exception: Exception?): Response {

        return when (exception) {
            is ProdutoNotFoundException -> {
                criarResponse(Status.NOT_FOUND, exception.message!!)
            }

            is ProdutoInvalidoException -> {
                criarResponse(Status.BAD_REQUEST, exception.message!!)
            }

            else -> criarResponse(Status.INTERNAL_SERVER_ERROR, exception?.message ?: "Erro interno do servidor")
        }

    }
}