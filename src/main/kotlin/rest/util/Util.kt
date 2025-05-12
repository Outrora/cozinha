package rest.util

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.ws.rs.core.Response

data class ApiResponse(
    @JsonProperty("status")
    val status: Int,

    @JsonProperty("messagem")
    val message: String,
    val data: Any? = null,

    @JsonProperty("timeStamp")
    val timestamp: Long = System.currentTimeMillis()
)

fun criarResponse(status: Response.Status, body: String): Response {
    val response = ApiResponse(
        status = status.statusCode,
        message = body
    )

    return Response
        .status(status)
        .entity(response)
        .build()
}