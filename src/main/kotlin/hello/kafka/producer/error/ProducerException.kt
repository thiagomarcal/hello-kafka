package hello.kafka.producer.error

class ProducerException(private val errorMessage: String) : Exception(errorMessage) {}
