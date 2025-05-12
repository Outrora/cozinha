package it.fila

import config.KafkaTestResourceLifecycleManager
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager::class)
class BuscarFilaIT {

    @Test
    fun `Deve Listar a fila corretamente`() {
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/fila/atual")
            .then()
            .statusCode(200)
    }

    @Test
    fun `Deve Listar a fila corretamente com preparacao`() {
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/fila/preparacao")
            .then()
            .statusCode(200)
    }

    @Test
    fun `Deve Listar a fila corretamente pelo dia`() {
        given()
            .contentType(ContentType.JSON)
            .body("2023-10-01")
            .`when`()
            .post("/fila")
            .then()
            .log().all()
            .statusCode(200)
    }
}