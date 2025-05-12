package it.pedido

import config.KafkaTestResourceLifecycleManager
import domain.entities.EstadoPedido
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import rest.request.PedidoProdutoRequest
import rest.request.PedidoRequest

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager::class)
class PedidoIT {

    @Test
    fun `Deve cadastrar pedido corretamente`() {
        val request = PedidoRequest(
            id = "2000",
            produtos = listOf(
                PedidoProdutoRequest(id = 1, quantidade = 2),
                PedidoProdutoRequest(id = 2, quantidade = 3)
            )
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .post("/pedido")
            .then()
            .statusCode(201)
    }

    @Test
    fun `Deve Editar Estado do Pedido corretamente`() {

        given()
            .contentType(ContentType.TEXT)
            .body(EstadoPedido.FINALIZADO.name)
            .`when`()
            .put("/pedido/estado/1")
            .then()
            .log().all()
            .statusCode(200)
    }
}