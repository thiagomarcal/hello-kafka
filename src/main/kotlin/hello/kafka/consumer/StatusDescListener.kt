package hello.kafka.consumer

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@KafkaListener(offsetReset = OffsetReset.EARLIEST, groupId = "StatusDescListener")
class StatusDescListener {

    companion object {
        val log : Logger = LoggerFactory.getLogger(StatusDescListener::class.java.simpleName)
    }

    @Topic("status-stream-desc-out")
    fun count(@KafkaKey desc: String, count: Long) {
        log.info("Status DESC message Successfully consumed -> key: $desc - count: $count")
    }
}