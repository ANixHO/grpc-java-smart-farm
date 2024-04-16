package com.grpcproject.smartfarm.EquipControlService;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: EquipControlService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EquipControlServiceGrpc {

  private EquipControlServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.grpcproject.smartfarm.EquipControlService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq,
      com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> getEquipPowerControlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "equipPowerControl",
      requestType = com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq.class,
      responseType = com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq,
      com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> getEquipPowerControlMethod() {
    io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq, com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> getEquipPowerControlMethod;
    if ((getEquipPowerControlMethod = EquipControlServiceGrpc.getEquipPowerControlMethod) == null) {
      synchronized (EquipControlServiceGrpc.class) {
        if ((getEquipPowerControlMethod = EquipControlServiceGrpc.getEquipPowerControlMethod) == null) {
          EquipControlServiceGrpc.getEquipPowerControlMethod = getEquipPowerControlMethod =
              io.grpc.MethodDescriptor.<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq, com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "equipPowerControl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes.getDefaultInstance()))
              .setSchemaDescriptor(new EquipControlServiceMethodDescriptorSupplier("equipPowerControl"))
              .build();
        }
      }
    }
    return getEquipPowerControlMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq,
      com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes> getEquipStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "equipStatus",
      requestType = com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq.class,
      responseType = com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq,
      com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes> getEquipStatusMethod() {
    io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq, com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes> getEquipStatusMethod;
    if ((getEquipStatusMethod = EquipControlServiceGrpc.getEquipStatusMethod) == null) {
      synchronized (EquipControlServiceGrpc.class) {
        if ((getEquipStatusMethod = EquipControlServiceGrpc.getEquipStatusMethod) == null) {
          EquipControlServiceGrpc.getEquipStatusMethod = getEquipStatusMethod =
              io.grpc.MethodDescriptor.<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq, com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "equipStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes.getDefaultInstance()))
              .setSchemaDescriptor(new EquipControlServiceMethodDescriptorSupplier("equipStatus"))
              .build();
        }
      }
    }
    return getEquipStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EquipControlServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EquipControlServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EquipControlServiceStub>() {
        @java.lang.Override
        public EquipControlServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EquipControlServiceStub(channel, callOptions);
        }
      };
    return EquipControlServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EquipControlServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EquipControlServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EquipControlServiceBlockingStub>() {
        @java.lang.Override
        public EquipControlServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EquipControlServiceBlockingStub(channel, callOptions);
        }
      };
    return EquipControlServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EquipControlServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EquipControlServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EquipControlServiceFutureStub>() {
        @java.lang.Override
        public EquipControlServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EquipControlServiceFutureStub(channel, callOptions);
        }
      };
    return EquipControlServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void equipPowerControl(com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq request,
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEquipPowerControlMethod(), responseObserver);
    }

    /**
     */
    default void equipStatus(com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq request,
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEquipStatusMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EquipControlService.
   */
  public static abstract class EquipControlServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EquipControlServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EquipControlService.
   */
  public static final class EquipControlServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EquipControlServiceStub> {
    private EquipControlServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EquipControlServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EquipControlServiceStub(channel, callOptions);
    }

    /**
     */
    public void equipPowerControl(com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq request,
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEquipPowerControlMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void equipStatus(com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq request,
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getEquipStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EquipControlService.
   */
  public static final class EquipControlServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EquipControlServiceBlockingStub> {
    private EquipControlServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EquipControlServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EquipControlServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes equipPowerControl(com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEquipPowerControlMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes> equipStatus(
        com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getEquipStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EquipControlService.
   */
  public static final class EquipControlServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EquipControlServiceFutureStub> {
    private EquipControlServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EquipControlServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EquipControlServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> equipPowerControl(
        com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEquipPowerControlMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_EQUIP_POWER_CONTROL = 0;
  private static final int METHODID_EQUIP_STATUS = 1;

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
        case METHODID_EQUIP_POWER_CONTROL:
          serviceImpl.equipPowerControl((com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq) request,
              (io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes>) responseObserver);
          break;
        case METHODID_EQUIP_STATUS:
          serviceImpl.equipStatus((com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq) request,
              (io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getEquipPowerControlMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq,
              com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes>(
                service, METHODID_EQUIP_POWER_CONTROL)))
        .addMethod(
          getEquipStatusMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq,
              com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes>(
                service, METHODID_EQUIP_STATUS)))
        .build();
  }

  private static abstract class EquipControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EquipControlServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EquipControlService");
    }
  }

  private static final class EquipControlServiceFileDescriptorSupplier
      extends EquipControlServiceBaseDescriptorSupplier {
    EquipControlServiceFileDescriptorSupplier() {}
  }

  private static final class EquipControlServiceMethodDescriptorSupplier
      extends EquipControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    EquipControlServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (EquipControlServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EquipControlServiceFileDescriptorSupplier())
              .addMethod(getEquipPowerControlMethod())
              .addMethod(getEquipStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
