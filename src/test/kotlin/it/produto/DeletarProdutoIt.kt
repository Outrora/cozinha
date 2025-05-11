package it.produto

import config.KafkaTestResourceLifecycleManager
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager::class)
class DeletarProdutoIt {

    @Test
    fun `Deve Deletar Produto Corretamente`() {

        given()
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/produto/4")
            .then()
            .log().all()
            .statusCode(204)
    }
}