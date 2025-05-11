package controller

import domain.entities.Fila
import domain.services.FilaService
import extensoras.paraData
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject

@RequestScoped
class FilaController @Inject constructor(private var service: FilaService) {

    fun filaAtual(): Fila {
        return service.listarFilaHoje();
    }

    fun filaAtualDia(data: String): Fila {
        return service.listarFilaPorDia(data.paraData());
    }

    fun filaAtualEmPreparacao(): Fila {
        return service.listarFilaEmPreparacao();
    }


}