package hello.kafka.grpc.exception

class GrpcException(private val error: GrpcErrorReason): Exception(error.description){}