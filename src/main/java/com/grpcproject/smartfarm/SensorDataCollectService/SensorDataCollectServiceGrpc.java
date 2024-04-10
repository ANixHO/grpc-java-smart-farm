package com.grpcproject.smartfarm.SensorDataCollectService;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: SensorDataCollectService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SensorDataCollectServiceGrpc {

  private SensorDataCollectServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.grpcproject.smartfarm.SensorDataCollectService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq,
      com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes> getSaveSensorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveSensorData",
      requestType = com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq.class,
      responseType = com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq,
      com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes> getSaveSensorDataMethod() {
    io.grpc.MethodDescriptor<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq, com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes> getSaveSensorDataMethod;
    if ((getSaveSensorDataMethod = SensorDataCollectServiceGrpc.getSaveSensorDataMethod) == null) {
      synchronized (SensorDataCollectServiceGrpc.class) {
        if ((getSaveSensorDataMethod = SensorDataCollectServiceGrpc.getSaveSensorDataMethod) == null) {
          SensorDataCollectServiceGrpc.getSaveSensorDataMethod = getSaveSensorDataMethod =
              io.grpc.MethodDescriptor.<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq, com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "saveSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes.getDefaultInstance()))
              .setSchemaDescriptor(new SensorDataCollectServiceMethodDescriptorSupplier("saveSensorData"))
              .build();
        }
      }
    }
    return getSaveSensorDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SensorDataCollectServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SensorDataCollectServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SensorDataCollectServiceStub>() {
        @java.lang.Override
        public SensorDataCollectServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SensorDataCollectServiceStub(channel, callOptions);
        }
      };
    return SensorDataCollectServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SensorDataCollectServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SensorDataCollectServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SensorDataCollectServiceBlockingStub>() {
        @java.lang.Override
        public SensorDataCollectServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SensorDataCollectServiceBlockingStub(channel, callOptions);
        }
      };
    return SensorDataCollectServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SensorDataCollectServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SensorDataCollectServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SensorDataCollectServiceFutureStub>() {
        @java.lang.Override
        public SensorDataCollectServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SensorDataCollectServiceFutureStub(channel, callOptions);
        }
      };
    return SensorDataCollectServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq> saveSensorData(
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSaveSensorDataMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SensorDataCollectService.
   */
  public static abstract class SensorDataCollectServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SensorDataCollectServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SensorDataCollectService.
   */
  public static final class SensorDataCollectServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SensorDataCollectServiceStub> {
    private SensorDataCollectServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SensorDataCollectServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SensorDataCollectServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq> saveSensorData(
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSaveSensorDataMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SensorDataCollectService.
   */
  public static final class SensorDataCollectServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SensorDataCollectServiceBlockingStub> {
    private SensorDataCollectServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SensorDataCollectServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SensorDataCollectServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SensorDataCollectService.
   */
  public static final class SensorDataCollectServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SensorDataCollectServiceFutureStub> {
    private SensorDataCollectServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SensorDataCollectServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SensorDataCollectServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SAVE_SENSOR_DATA = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_SENSOR_DATA:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.saveSensorData(
              (io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSaveSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.SaveSensorDataReq,
              com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.saveSensorDataRes>(
                service, METHODID_SAVE_SENSOR_DATA)))
        .build();
  }

  private static abstract class SensorDataCollectServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SensorDataCollectServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SensorDataCollectService");
    }
  }

  private static final class SensorDataCollectServiceFileDescriptorSupplier
      extends SensorDataCollectServiceBaseDescriptorSupplier {
    SensorDataCollectServiceFileDescriptorSupplier() {}
  }

  private static final class SensorDataCollectServiceMethodDescriptorSupplier
      extends SensorDataCollectServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SensorDataCollectServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SensorDataCollectServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SensorDataCollectServiceFileDescriptorSupplier())
              .addMethod(getSaveSensorDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
