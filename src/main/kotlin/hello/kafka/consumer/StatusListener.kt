package hello.kafka.consumer

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@KafkaListener(offsetReset = OffsetReset.EARLIEST, groupId = "StatusListener")
class StatusListener {

    companion object {
        val log : Logger = LoggerFactory.getLogger(StatusListener::class.java.simpleName)
    }

    @Topic("status")
    fun receive(@KafkaKey key: String, message: String) {
        log.info("Status message Successfully consumed -> key: $key - msg: $message")
    }
}