package controller

import domain.entities.Fila
import domain.services.FilaService
import helps.CriarMocksFila
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import java.util.*

@ExtendWith(MockKExtension::class)
class FilaControllerTest {

    @MockK
    private lateinit var service: FilaService
    private lateinit var controller: FilaController

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        controller = FilaController(service)
    }

    @Test
    fun `Deve Retornar Fila Atual`() {
        val fila = CriarMocksFila.criarFila()
        every { service.listarFilaHoje() } returns fila

        val resultado = controller.filaAtual()

        expectThat(resultado)
            .isNotNull()
            .isA<Fila>()
            .and {
                get { listaPedidos }.isEqualTo(fila.listaPedidos)
                get { diaFila }.isEqualTo(fila.diaFila)
            }

        verify(exactly = 1) { service.listarFilaHoje() }

    }

    @Test
    fun `Deve Retornar Fila filaAtualDia`() {
        val fila = CriarMocksFila.criarFila()
        every { service.listarFilaPorDia(any(Date::class)) } returns fila

        val resultado = controller.filaAtualDia("2023-10-01")

        expectThat(resultado)
            .isNotNull()
            .isA<Fila>()
            .and {
                get { listaPedidos }.isEqualTo(fila.listaPedidos)
                get { diaFila }.isEqualTo(fila.diaFila)
            }

        verify(exactly = 1) { service.listarFilaPorDia(any(Date::class)) }

    }

    @Test
    fun `Deve Retornar Fila Atual EmPreparacao`() {
        val fila = CriarMocksFila.criarFila()
        every { service.listarFilaEmPreparacao() } returns fila

        val resultado = controller.filaAtualEmPreparacao()

        expectThat(resultado)
            .isNotNull()
            .isA<Fila>()
            .and {
                get { listaPedidos }.isEqualTo(fila.listaPedidos)
                get { diaFila }.isEqualTo(fila.diaFila)
            }

        verify(exactly = 1) { service.listarFilaEmPreparacao() }

    }


}