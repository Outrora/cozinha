package controller

import domain.entities.Fila
import domain.services.FilaService
import extensoras.paraData
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject

@RequestScoped
class FilaController @Inject constructor(var service: FilaService) {

    fun FilaAtual(): Fila {
        return service.listarFilaHoje();
    }

    fun FilaAtualDia(data: String): Fila {
        return service.listarFilaPorDia(data.paraData());
    }

    fun FilaAtualEmPreparacao(): Fila {
        return service.listarFilaEmPreparacao();
    }


}