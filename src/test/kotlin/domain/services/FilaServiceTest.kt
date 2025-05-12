package domain.services

import adapter.FilaAdapter
import domain.entities.Fila
import extensoras.paraData
import helps.CriarMocksFila
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class FilaServiceTest {

    @MockK
    private lateinit var fila: FilaAdapter
    private lateinit var service: FilaService


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        service = FilaService(fila)
    }

    @Test
    fun `Deve listar fila hoje com sucesso`() {
        val filaHoje = CriarMocksFila.criarFila()

        every { fila.listarFilaPedidoHoje() } returns filaHoje

        val retorno = service.listarFilaHoje()

        expectThat(retorno)
            .isEqualTo(filaHoje)
            .isA<Fila>()

        verify { fila.listarFilaPedidoHoje() }
    }

    @Test
    fun `Deve listar fila por dia com sucesso`() {
        val filaHoje = CriarMocksFila.criarFila()

        every { fila.listarFilaPorDia(any()) } returns filaHoje

        val retorno = service.listarFilaPorDia("2023-10-01".paraData())

        expectThat(retorno)
            .isEqualTo(filaHoje)
            .isA<Fila>()

        verify { fila.listarFilaPorDia(any()) }
    }

    @Test
    fun `Deve listar fila em preparacao com sucesso`() {
        val filaHoje = CriarMocksFila.criarFila()

        every { fila.listarFilaPedidoHojeEmPreparacao() } returns filaHoje

        val retorno = service.listarFilaEmPreparacao()

        expectThat(retorno)
            .isEqualTo(filaHoje)
            .isA<Fila>()

        verify { fila.listarFilaPedidoHojeEmPreparacao() }
    }
}