package hello.kafka.grpc.exception

import io.grpc.Status

enum class GrpcErrorReason(
    val errorCode: String,
    val description: String,
    val grpcStatus: Status
) {
    FAILURE_TO_PRODUCE_STATUS_EVENT("001", "Failure to produce status event.", Status.ABORTED),
    FAILURE_UNKNOWN("999", "Unknown failure.", Status.UNKNOWN)
}