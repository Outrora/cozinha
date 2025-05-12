package rest

import controller.FilaController
import domain.entities.Fila
import io.vertx.core.cli.annotations.Description
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@RequestScoped
@Tag(name = "Fila", description = "EndPoints do Pedido")
@Path("/fila")
class RestFila @Inject constructor(val controller: FilaController) {

    @GET
    @Path("/atual")
    @Description("Pega fila atual")
    fun filaAtual(): Fila {
        return controller.filaAtual()
    }

    @GET
    @Path("/preparacao")
    @Description("Pega fila atual em estado de preparacao")
    fun filaAtualPreparacao(): Fila {
        return controller.filaAtualEmPreparacao()
    }

    @POST
    @Path("/")
    @Description("Pega fila pelo dia")
    fun filaAtualDia(data: String): Fila {
        return controller.filaAtualDia(data)
    }


}