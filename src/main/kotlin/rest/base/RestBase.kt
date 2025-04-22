package rest.base

import controller.base.ControllerBase
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Produces(MediaType.APPLICATION_JSON + ";charsert=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charsert=utf-8")
abstract class RestBase<T : ControllerBase<*>>() {

    protected lateinit var controller: T
}