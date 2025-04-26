package it.produto

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

@QuarkusTest
class ListarProdutoIT {

    @Test
    fun `Deve Listar Todos Produtos Corretamente`() {
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/produto/listar")
            .then()
            .statusCode(200)
    }

    @Test
    fun `Deve Listar Produto Corretamente`() {
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/produto/1")
            .then()
            .statusCode(200)
    }

    @Test
    fun `Deve Listar Produtos com IDS Corretamente`() {

        var ids = setOf(1, 2)

        given()
            .contentType(ContentType.JSON)
            .body(ids)
            .`when`()
            .post("/produto/buscarPorIds")
            .then()
            .statusCode(200)
    }
}