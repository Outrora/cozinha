package config

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.smallrye.reactive.messaging.memory.InMemoryConnector


class KafkaTestResourceLifecycleManager : QuarkusTestResourceLifecycleManager {

    override fun start(): MutableMap<String?, String?> {
        val env: MutableMap<String?, String?> = HashMap<String?, String?>()
        val props1 = InMemoryConnector.switchIncomingChannelsToInMemory(
            "pedido-cadastro",
            "pedido-alterado-central"
        )
        val props2 = InMemoryConnector.switchOutgoingChannelsToInMemory(
            "pedido-alterado-produto",
            "produto-cadastrado"
        )
        env.putAll(props1)
        env.putAll(props2)
        return env
    }

    override fun stop() {
        InMemoryConnector.clear()
    }
}