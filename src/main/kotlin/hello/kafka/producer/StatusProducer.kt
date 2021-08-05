package hello.kafka.producer

import hello.kafka.model.StatusMessage

interface StatusProducer {
    fun sendMessage(status: StatusMessage): Boolean
}