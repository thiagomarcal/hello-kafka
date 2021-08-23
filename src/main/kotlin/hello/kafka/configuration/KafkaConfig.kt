package hello.kafka.configuration

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Property
import javax.inject.Named

@Factory
class KafkaConfig(
    @Property(name = "app.topic.status") val statusTopic: String,
    @Property(name = "app.topic.status-stream-desc-out") val statusStreamDescOutTopic: String
) {

    @Bean
    @Named("statusTopic")
    fun statusTopic(): String {
        return statusTopic
    }

    @Bean
    @Named("statusStreamDescOutTopic")
    fun statusStreamDescOutTopic(): String {
        return statusStreamDescOutTopic
    }
}