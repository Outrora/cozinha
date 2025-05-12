package steps


import helps.CriarMocksProduto
import io.cucumber.java.pt.Dado
import io.cucumber.java.pt.Entao
import io.cucumber.java.pt.Quando
import io.quarkiverse.cucumber.ScenarioScope



import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import rest.request.ProdutoRequest


class StepDefinitions {

    @Inject
    lateinit var estadoBean: EstadoBean

    @Dado("o usuário preenche os dados do produto")
    fun preencherDadosPedido() {
        estadoBean.setRequest(CriarMocksProduto.criarProdutoRequest())
    }

    @Quando("o usuário envia o produto")
    fun enviarPedido() {


        given()
            .contentType(ContentType.JSON)
            .body(estadoBean.getRequest())
            .`when`()
            .post("/produto")
            .then()
    }

    @Entao("o sistema deve cadastrar o produto com sucesso")
    fun teveCadastrarComSucesso() {
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/produto/1")
            .then()
            .statusCode(200)
    }

}

@ScenarioScope
class EstadoBean() {
    private lateinit var request: ProdutoRequest

      fun getRequest(): ProdutoRequest {
        return request
    }

     fun setRequest(request: ProdutoRequest) {
        this.request = request
    }
}