package provider

import exception.ErroValidacao
import exception.ProdutoInvalidoException
import exception.ProdutoNotFoundException
import jakarta.ws.rs.core.Response
import org.junit.jupiter.api.Test
import rest.util.ApiResponse
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

class ErrorMapperTest {

    @Test
    fun `Deve retornar erro 500 quando ocorrer uma exceção`() {
        val errorMapper = ErrorMapper()
        val exception = Exception("Erro interno")

        val response = errorMapper.toResponse(exception);

        expectThat(response)
            .isNotNull()
            .isA<Response>()

        expectThat(response.status)
            .isEqualTo(500)

        expectThat(response.entity)
            .isA<ApiResponse>()
            .and {
                get { status }.isEqualTo(500)
                get { message }.isEqualTo("Erro interno")
            }
    }

    @Test
    fun `Deve retornar erro 404 quando ProdutoNotFoundException`() {
        val errorMapper = ErrorMapper()
        val exception = ProdutoNotFoundException("Produto não encontrado")

        val response = errorMapper.toResponse(exception);

        expectThat(response)
            .isNotNull()
            .isA<Response>()

        expectThat(response.status)
            .isEqualTo(404)

        expectThat(response.entity)
            .isA<ApiResponse>()
            .and {
                get { status }.isEqualTo(404)
                get { message }.isEqualTo("Produto não encontrado")
            }
    }

    @Test
    fun `Deve retornar erro 400 quando ProdutoInvalidoException`() {
        val errorMapper = ErrorMapper()
        val exception = ProdutoInvalidoException(listOf("Produto inválido"))

        val response = errorMapper.toResponse(exception);

        expectThat(response)
            .isNotNull()
            .isA<Response>()

        expectThat(response.status)
            .isEqualTo(400)

        expectThat(response.entity)
            .isA<ApiResponse>()
            .and {
                get { status }.isEqualTo(400)
                get { message }.isEqualTo("Produto inválido")
            }
    }

    @Test
    fun `Deve retornar erro 400 quando ErroValidacao`() {
        val errorMapper = ErrorMapper()
        val exception = ErroValidacao("Produto inválido")

        val response = errorMapper.toResponse(exception);

        expectThat(response)
            .isNotNull()
            .isA<Response>()

        expectThat(response.status)
            .isEqualTo(400)

        expectThat(response.entity)
            .isA<ApiResponse>()
            .and {
                get { status }.isEqualTo(400)
                get { message }.isEqualTo("Produto inválido")
            }
    }

    @Test
    fun `Deve retornar erro 500 quando erro qualquer sem mensagem`() {
        val errorMapper = ErrorMapper()
        val exception = Exception()

        val response = errorMapper.toResponse(exception);

        expectThat(response)
            .isNotNull()
            .isA<Response>()

        expectThat(response.status)
            .isEqualTo(500)

        expectThat(response.entity)
            .isA<ApiResponse>()
            .and {
                get { status }.isEqualTo(500)
                get { message }.isEqualTo("Erro interno do servidor")
            }
    }

    @Test
    fun `Deve retornar erro 400 quando erro qualquer com mensagem`() {
        val errorMapper = ErrorMapper()
        val exception = ErroValidacao()

        val response = errorMapper.toResponse(exception);

        expectThat(response)
            .isNotNull()
            .isA<Response>()

        expectThat(response.status)
            .isEqualTo(400)

        expectThat(response.entity)
            .isA<ApiResponse>()
            .and {
                get { status }.isEqualTo(400)
                get { message }.isEqualTo("Erro de validação")
            }
    }

}