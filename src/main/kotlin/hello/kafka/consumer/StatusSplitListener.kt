package hello.kafka.consumer

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@KafkaListener(offsetReset = OffsetReset.EARLIEST, groupId = "StatusSplitListener")
class StatusSplitListener {

    companion object {
        val log : Logger = LoggerFactory.getLogger(StatusSplitListener::class.java.simpleName)
    }

    @Topic("status-stream-split-out")
    fun count(@KafkaKey key: String, value: String) {
        log.info("Status SPLIT message Successfully consumed -> key: $key === value: $value")
    }
}