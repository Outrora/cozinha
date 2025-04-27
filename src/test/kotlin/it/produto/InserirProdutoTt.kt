package it.produto

import helps.CriarMocksProduto
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@QuarkusTest
class InserirProdutoTt {

    @Test
    fun `Deve Cadastrar Produto Corretamente`() {
        val produto = CriarMocksProduto.criarProdutoRequest()

        given()
            .contentType(ContentType.JSON)
            .body(produto)
            .`when`()
            .post("/produto")
            .then()
            .statusCode(201)
    }

    @Test
    fun `Deve Retornar 400 Quando Produto Invalido`() {
        val produto = CriarMocksProduto.criarProdutoRequest().copy(preco = BigDecimal.valueOf(-1.0))


        given()
            .contentType(ContentType.JSON)
            .body(produto)
            .`when`()
            .post("/produto")
            .then()
            .statusCode(400)
    }
}