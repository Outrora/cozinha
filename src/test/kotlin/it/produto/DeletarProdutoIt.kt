package it.produto

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

@QuarkusTest
class DeletarProdutoIt {

    @Test
    fun `Deve Deletar Produto Corretamente`() {
        
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/produto/1")
            .then()
            .statusCode(204)
    }
}