package hello.kafka.grpc.entrypoint

import hello.kafka.KafkaServiceGrpc
import hello.kafka.StatusProduceRequest
import hello.kafka.StatusProduceResponse
import hello.kafka.grpc.exception.GrpcException
import hello.kafka.model.StatusMessage
import hello.kafka.producer.StatusProducer
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import java.util.UUID

@Singleton
class GrpcEntrypoint(private val producer: StatusProducer) : KafkaServiceGrpc.KafkaServiceImplBase() {

    companion object {
        val log: Logger = LoggerFactory.getLogger("GrpcEntrypoint")
    }

    override fun produceStatusEvent(
        request: StatusProduceRequest,
        responseObserver: StreamObserver<StatusProduceResponse>
    ) {
        try {
            log.info("Request received -> producing status event")
            val uuid = UUID.randomUUID()
            val statusMessage = StatusMessage(
                key = uuid.toString(),
                code = request.code.toInt(),
                alias = request.alias,
                desc = request.desc
            )
            responseObserver.onNext(produceStatusMessage(statusMessage))
            responseObserver.onCompleted()
        } catch (ex: GrpcException) {
            responseObserver.onError(ex)
        }
    }


    private fun produceStatusMessage(message: StatusMessage): StatusProduceResponse {
        producer.sendMessage(message)
        return StatusProduceResponse.newBuilder()
            .setMessage("Status Event successfully emitted with key: ${message.key}")
            .build()
    }
}