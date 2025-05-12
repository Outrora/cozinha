package domain.services

import adapter.FilaAdapter
import domain.entities.Fila
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import java.util.*

@RequestScoped
class FilaService @Inject constructor(private val fila: FilaAdapter) {

    fun listarFilaHoje(): Fila {
        return fila.listarFilaPedidoHoje()
    }

    fun listarFilaPorDia(data: Date): Fila {
        return fila.listarFilaPorDia(data)
    }

    fun listarFilaEmPreparacao(): Fila {
        return fila.listarFilaPedidoHojeEmPreparacao()
    }
}