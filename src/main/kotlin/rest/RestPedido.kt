package rest

import controller.PedidoController
import domain.entities.EstadoPedido
import io.vertx.core.cli.annotations.Description
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import rest.request.PedidoRequest
import rest.util.criarResponse

@RequestScoped
@Tag(name = "Pedido", description = "EndPoints do Pedido")
@Path("/pedido")
class RestPedido @Inject constructor(val controller: PedidoController) {


    @POST
    @Description("Cadastrar Pedido")
    fun cadastrarPedido(request: PedidoRequest): Response {
        controller.cadastrarPedido(request)
        return criarResponse(Response.Status.CREATED, "Pedido cadastrado com sucesso")
    }

    @PUT
    @Path("estado/{id}")
    @Description("Editar Estado do Pedido")
    fun editarEstado(@PathParam("id") idPedido: String, estado: EstadoPedido): Response {
        controller.editarEstado(idPedido, estado)
        return criarResponse(Response.Status.OK, "Estado do pedido editado com sucesso")
    }
}