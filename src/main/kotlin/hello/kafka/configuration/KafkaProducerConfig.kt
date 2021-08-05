package hello.kafka.configuration

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Property

@Factory
class KafkaProducerConfig(
    @Property(name = "kafka.topic.status") val statusTopic: String) {

    @Bean
    fun statusTopic(): String {
        return statusTopic
    }
}