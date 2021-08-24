package hello.kafka.stream

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import hello.kafka.model.StatusMessage
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder
import io.micronaut.context.annotation.Factory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.*
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Factory
class StatusStream(
    @param:Named("statusTopic") private val statusTopic: String,
    @param:Named("statusStreamDescOutTopic") private val statusStreamDescOutTopic: String,
    @param:Named("statusStreamSplitOutTopic") private val statusStreamSplitOutTopic: String,
) {

    companion object {
        const val STATUS_DESC_STORE = "status-desc-store"
    }

    private val jsonMapper = ObjectMapper().apply { registerKotlinModule() }


    @Singleton
    @Named("status-desc-count")
    fun statusDescStream(builder: ConfiguredStreamBuilder): KStream<String, StatusMessage> {


        val props = builder.configuration
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name


        props[StreamsConfig.REPLICATION_FACTOR_CONFIG] = 1
        props[ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG] = true
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"


        val statusMessageJsonStream: KStream<String, String> = builder
            .stream(statusTopic, Consumed.with(Serdes.String(), Serdes.String()))

        val statusMessageStream: KStream<String, StatusMessage> = statusMessageJsonStream
            .mapValues { v -> jsonMapper.readValue(v, StatusMessage::class.java) }

        val groupedByWord: KTable<String, Long> = statusMessageStream
            .flatMapValues { value -> value.desc.toLowerCase().split(" ") }
            .groupBy({_, desc -> desc }, Grouped.with(Serdes.String(), Serdes.String()))
            .count(Materialized.`as`(STATUS_DESC_STORE))


        groupedByWord
            .toStream()
            .to(statusStreamDescOutTopic, Produced.with(Serdes.String(), Serdes.Long()))


        statusMessageStream
            .flatMapValues { value -> value.desc.toLowerCase().split(" ") }
            .to(statusStreamSplitOutTopic)


        return statusMessageStream;

    }

}