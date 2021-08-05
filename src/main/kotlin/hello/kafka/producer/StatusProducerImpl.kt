package hello.kafka.producer

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import hello.kafka.model.StatusMessage
import hello.kafka.producer.error.ProducerError
import hello.kafka.producer.error.ProducerException
import io.micronaut.configuration.kafka.annotation.KafkaClient
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class StatusProducerImpl(
    private val statusTopic: String,
    private val objectMapperJSON: ObjectMapper,
    @KafkaClient private val producer: Producer<String, String>
) : StatusProducer {

    companion object {
        val log: Logger = LoggerFactory.getLogger("StatusProducerImpl")
    }

    override fun sendMessage(status: StatusMessage): Boolean {
        try {
            log.info("Sending message $status to kafka -> topic: $statusTopic")
            producer.send(ProducerRecord(statusTopic, status.key, parseMessageToJsonString(status)))
        } catch (ex: Exception) {
            log.error(ProducerError.ERROR_TO_PUBLISH_MESSAGE.desc, ex)
            return false
        }

        return true
    }

    private fun parseMessageToJsonString(status: StatusMessage): String {
        try {
            return objectMapperJSON.writeValueAsString(status)
        } catch (ex: JsonProcessingException) {
            log.error(ProducerError.ERROR_TO_PARSE_MESSAGE.desc, ex)
            throw ProducerException(ProducerError.ERROR_TO_PARSE_MESSAGE.desc)
        }
    }
}