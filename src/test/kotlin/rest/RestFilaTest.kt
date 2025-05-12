package rest

import controller.FilaController
import helps.CriarMocksFila
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class RestFilaTest {

    @MockK
    private lateinit var fila: FilaController
    private lateinit var rest: RestFila

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        rest = RestFila(fila)
    }

    @Test
    fun `Deve retornar fila atual`() {
        val filaEsperada = CriarMocksFila.criarFila(5)
        every { fila.filaAtual() } returns filaEsperada

        val resultado = rest.filaAtual()

        assertEquals(filaEsperada, resultado)
    }

    @Test
    fun `Deve retornar fila atual em preparacao`() {
        val filaEsperada = CriarMocksFila.criarFila(5)// Substitua com a instância esperada
        every { fila.filaAtualEmPreparacao() } returns filaEsperada

        val resultado = rest.filaAtualPreparacao()

        assertEquals(filaEsperada, resultado)
    }

    @Test
    fun `Deve retornar fila pelo dia`() {
        val data = "2023-10-01"
        val filaEsperada = CriarMocksFila.criarFila(5)
        every { fila.filaAtualDia(data) } returns filaEsperada

        val resultado = rest.filaAtualDia(data)

        assertEquals(filaEsperada, resultado)
    }
}