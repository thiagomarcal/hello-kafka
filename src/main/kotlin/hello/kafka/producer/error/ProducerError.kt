package hello.kafka.producer.error

enum class ProducerError(val desc: String) {
    ERROR_TO_PUBLISH_MESSAGE("Error to publish message."),
    ERROR_TO_PARSE_MESSAGE("Error to parse message.");
}