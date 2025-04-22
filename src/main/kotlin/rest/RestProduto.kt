package rest

import controller.ProdutoController
import domain.entities.Produto
import io.vertx.core.cli.annotations.Description
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import rest.base.RestBase
import rest.request.ProdutoRequest
import rest.util.criarResponse

@RequestScoped
@Tag(name = "Produto", description = "EndPoints do produto")
@Path("/produto")
class RestProduto : RestBase<ProdutoController> {

    @Inject
    constructor(controller: ProdutoController) {
        this.controller = controller
    }

    @GET
    @Path("/listar")
    @Description("Listar produtos")
    fun listarProdutos(): List<Produto> {
        return controller.listarProdutos()
    }


    @POST
    @Description("Cadastrar Produto")
    fun criarProduto(produto: ProdutoRequest): Response {
        controller.cadastrarProduto(produto)
        return criarResponse(Response.Status.CREATED, "Produto cadastrado com sucesso")
    }

    @PUT
    @Description("Editar Produto")
    @Path("{id}")
    fun editarProduto(produto: ProdutoRequest, @PathParam("id") id: Int): Response {
        controller.editarProduto(produto, id)
        return criarResponse(Response.Status.OK, "Produto editado com sucesso")
    }

    @DELETE
    @Path("{id}")
    @Description("Deletar Produto")
    fun deletarProduto(@PathParam("id") id: Int) {
        controller.deletarProduto(id)
    }

    @GET
    @Description("Buscar Produto por ID")
    @Path("{id}")
    fun buscarProdutoPorId(@PathParam("id") id: Int): Produto? {
        return controller.buscarProdutoPorId(id)
    }

    @POST
    @Description("Buscar Produto por IDs")
    @Path("/buscarPorIds")
    fun bucarProdutoPorIds(ids: Set<Int>): List<Produto> {
        return controller.buscarProdutoPorIds(ids)
    }
}