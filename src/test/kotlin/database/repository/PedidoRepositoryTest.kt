package database.repository

import database.dto.PedidoDTO
import domain.entities.EstadoPedido
import exception.PedidoNotFoundException
import exception.ProdutoInvalidoException
import extensoras.paraLocalDateTimeFim
import extensoras.paraLocalDateTimeInicio
import extensoras.toDTO
import helps.CriarMocksFila
import io.mockk.*
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class PedidoRepositoryTest {

    @SpyK
    private var repository: PedidoRepository = PedidoRepository()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init()
    }

    @Test
    fun `Deve Cadastrar Pedido Corretamente`() {
        val dto = CriarMocksFila.criarPedido().toDTO()
        val lista = CriarMocksFila.criarProdutosIds()
        val entityManager = mockk<EntityManager>()
        val query = mockk<Query>()

        justRun { repository.persist(any(PedidoDTO::class)) }
        every { repository.getEntityManager() } returns entityManager
        every { entityManager.createNativeQuery(any(String::class)) } returns query
        every { query.setParameter("quantidade", any()) } returns query
        every { query.setParameter("produto", any()) } returns query
        every { query.setParameter("pedido", any()) } returns query
        every { query.executeUpdate() } returns 1


        repository.cadastrarPedido(dto, lista)

        verify(exactly = 1) { repository.persist(any(PedidoDTO::class)) }
        verify(exactly = lista.size) { entityManager.createNativeQuery(any()) }
        verify(exactly = 3 * lista.size) { query.setParameter(any(String::class), any()) }
        verify(exactly = lista.size) { query.executeUpdate() }


    }

    @Test
    fun `Nao deve Cadastrar Pedido Corretamente`() {
        val dto = CriarMocksFila.criarPedido().toDTO()
        val lista = CriarMocksFila.criarProdutosIds()
        val entityManager = mockk<EntityManager>()
        val query = mockk<Query>()

        justRun { repository.persist(any(PedidoDTO::class)) }
        every { repository.getEntityManager() } returns entityManager
        every { entityManager.createNativeQuery(any(String::class)) } returns query
        every { query.setParameter("quantidade", any()) } returns query
        every { query.setParameter("produto", any()) } returns query
        every { query.setParameter("pedido", any()) } returns query
        every { query.executeUpdate() } throws Exception("Erro")


        expectThrows<ProdutoInvalidoException> { repository.cadastrarPedido(dto, lista) }
            .isNotNull()

        verify(exactly = 1) { repository.persist(any(PedidoDTO::class)) }
        verify { entityManager.createNativeQuery(any()) }
        verify { query.setParameter(any(String::class), any()) }
        verify { query.executeUpdate() }
    }

    @Test
    fun `Deve Buscar Pedidos Pela Data Corretamente`() {
        val data = "2024-03-20"
        val pedidosDTO = listOf(CriarMocksFila.criarPedido().toDTO())
        val query = mockk<PanacheQuery<PedidoDTO>>()

        every {
            repository.find(
                any(String::class),
                any(LocalDateTime::class),
                any(LocalDateTime::class)
            )
        } returns query

        every { query.list() } returns pedidosDTO


        val resultado = repository.buscarPedidoPelaData(data)


        expectThat(resultado)
            .isNotNull()
            .hasSize(1)
            .isEqualTo(pedidosDTO)

        verify(exactly = 1) {
            repository.find(
                "dataCriacao > ?1 and dataCriacao < ?2",
                data.paraLocalDateTimeInicio(),
                data.paraLocalDateTimeFim()
            )
        }
        verify(exactly = 1) { query.list() }
    }

    @Test
    fun `Deve Buscar Pedidos Pela Data E Estado Corretamente`() {
        // Given
        val data = "2024-03-20"
        val estado = EstadoPedido.EM_PREPARACAO
        val pedidosDTO = listOf(CriarMocksFila.criarPedido().toDTO())
        val query = mockk<PanacheQuery<PedidoDTO>>()

        every {
            repository.find(
                any(String::class),
                any(LocalDateTime::class),
                any(LocalDateTime::class),
                any(EstadoPedido::class)
            )
        } returns query
        every { query.list() } returns pedidosDTO

        // When
        val resultado = repository.buscarPedidoPelaDataEPreparacao(data, estado)

        // Then
        expectThat(resultado)
            .isNotNull()
            .hasSize(1)
            .isEqualTo(pedidosDTO)

        verify(exactly = 1) {
            repository.find(
                "dataCriacao > ?1 and dataCriacao < ?2 and estadoPedido =?3",
                data.paraLocalDateTimeInicio(),
                data.paraLocalDateTimeFim(),
                estado
            )
        }
        verify(exactly = 1) { query.list() }
    }

    @Test
    fun `Deve Alterar Estado Do Pedido Corretamente`() {
        // Given
        val idPedido = "1"
        val novoEstado = EstadoPedido.entries.toTypedArray().random()
        val pedidoDTO = CriarMocksFila.criarPedido().toDTO()
        val queryMock = mockk<PanacheQuery<PedidoDTO>>()

        every { repository.find(any(), idPedido) } returns queryMock
        every { queryMock.firstResult() } returns pedidoDTO
        justRun { repository.persist(any(PedidoDTO::class)) }

        // When
        repository.alterarEstadoPedido(idPedido, novoEstado)

        // Then
        verify(exactly = 1) { repository.find("id", idPedido) }
        verify(exactly = 1) { repository.persist(any(PedidoDTO::class)) }
    }

    @Test
    fun `Nao Deve Alterar Estado Do Pedido Quando Pedido Nao Encontrado`() {
        // Given
        val idPedido = "1"
        val novoEstado = EstadoPedido.entries.toTypedArray().random()
        val queryMock = mockk<PanacheQuery<PedidoDTO>>()

        every { repository.find(any(), idPedido) } returns queryMock
        every { queryMock.firstResult() } returns null

        // When/Then
        expectThrows<PedidoNotFoundException> {
            repository.alterarEstadoPedido(idPedido, novoEstado)
        }

        verify(exactly = 1) { repository.find("id", idPedido) }
        verify(exactly = 0) { repository.persist(any(PedidoDTO::class)) }
    }

    @Test
    fun `Deve lançar um exeção quando o nao encotrar pedido`() {
        // Given
        val idPedido = "1"
        val novoEstado = EstadoPedido.entries.toTypedArray().random()
        val queryMock = mockk<PanacheQuery<PedidoDTO>>()

        every { repository.find(any(), idPedido) } returns queryMock
        every { queryMock.firstResult() } returns null

        // When/Then
        expectThrows<PedidoNotFoundException> {
            repository.buscarPedidoPorId(idPedido)
        }

        verify(exactly = 1) { repository.find("id", idPedido) }
    }


}