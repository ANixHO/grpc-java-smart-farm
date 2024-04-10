package com.grpcproject.smartfarm.EquipControlService;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: EquipControlService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class equipControlServiceGrpc {

  private equipControlServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.grpcproject.smartfarm.equipControlService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq,
      com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> getEquipPowerControlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "equipPowerControl",
      requestType = com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq.class,
      responseType = com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq,
      com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> getEquipPowerControlMethod() {
    io.grpc.MethodDescriptor<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq, com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> getEquipPowerControlMethod;
    if ((getEquipPowerControlMethod = equipControlServiceGrpc.getEquipPowerControlMethod) == null) {
      synchronized (equipControlServiceGrpc.class) {
        if ((getEquipPowerControlMethod = equipControlServiceGrpc.getEquipPowerControlMethod) == null) {
          equipControlServiceGrpc.getEquipPowerControlMethod = getEquipPowerControlMethod =
              io.grpc.MethodDescriptor.<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq, com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "equipPowerControl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes.getDefaultInstance()))
              .setSchemaDescriptor(new equipControlServiceMethodDescriptorSupplier("equipPowerControl"))
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
    if ((getEquipStatusMethod = equipControlServiceGrpc.getEquipStatusMethod) == null) {
      synchronized (equipControlServiceGrpc.class) {
        if ((getEquipStatusMethod = equipControlServiceGrpc.getEquipStatusMethod) == null) {
          equipControlServiceGrpc.getEquipStatusMethod = getEquipStatusMethod =
              io.grpc.MethodDescriptor.<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq, com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "equipStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes.getDefaultInstance()))
              .setSchemaDescriptor(new equipControlServiceMethodDescriptorSupplier("equipStatus"))
              .build();
        }
      }
    }
    return getEquipStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static equipControlServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<equipControlServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<equipControlServiceStub>() {
        @java.lang.Override
        public equipControlServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new equipControlServiceStub(channel, callOptions);
        }
      };
    return equipControlServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static equipControlServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<equipControlServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<equipControlServiceBlockingStub>() {
        @java.lang.Override
        public equipControlServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new equipControlServiceBlockingStub(channel, callOptions);
        }
      };
    return equipControlServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static equipControlServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<equipControlServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<equipControlServiceFutureStub>() {
        @java.lang.Override
        public equipControlServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new equipControlServiceFutureStub(channel, callOptions);
        }
      };
    return equipControlServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq> equipPowerControl(
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getEquipPowerControlMethod(), responseObserver);
    }

    /**
     */
    default void equipStatus(com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusReq request,
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipStatusRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEquipStatusMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service equipControlService.
   */
  public static abstract class equipControlServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return equipControlServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service equipControlService.
   */
  public static final class equipControlServiceStub
      extends io.grpc.stub.AbstractAsyncStub<equipControlServiceStub> {
    private equipControlServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected equipControlServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new equipControlServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerReq> equipPowerControl(
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getEquipPowerControlMethod(), getCallOptions()), responseObserver);
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
   * A stub to allow clients to do synchronous rpc calls to service equipControlService.
   */
  public static final class equipControlServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<equipControlServiceBlockingStub> {
    private equipControlServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected equipControlServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new equipControlServiceBlockingStub(channel, callOptions);
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
   * A stub to allow clients to do ListenableFuture-style rpc calls to service equipControlService.
   */
  public static final class equipControlServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<equipControlServiceFutureStub> {
    private equipControlServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected equipControlServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new equipControlServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_EQUIP_STATUS = 0;
  private static final int METHODID_EQUIP_POWER_CONTROL = 1;

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
        case METHODID_EQUIP_POWER_CONTROL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.equipPowerControl(
              (io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.EquipPowerRes>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getEquipPowerControlMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
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

  private static abstract class equipControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    equipControlServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("equipControlService");
    }
  }

  private static final class equipControlServiceFileDescriptorSupplier
      extends equipControlServiceBaseDescriptorSupplier {
    equipControlServiceFileDescriptorSupplier() {}
  }

  private static final class equipControlServiceMethodDescriptorSupplier
      extends equipControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    equipControlServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (equipControlServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new equipControlServiceFileDescriptorSupplier())
              .addMethod(getEquipPowerControlMethod())
              .addMethod(getEquipStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
