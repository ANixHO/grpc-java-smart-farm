package com.grpcproject.smartfarm.UserMonitorAndControlService;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: UserMonitorAndControlService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserMonitorAndControlServiceGrpc {

  private UserMonitorAndControlServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.grpcproject.smartfarm.UserMonitorAndControlService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq,
      com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes> getRefreshSensorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "refreshSensorData",
      requestType = com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq.class,
      responseType = com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq,
      com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes> getRefreshSensorDataMethod() {
    io.grpc.MethodDescriptor<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq, com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes> getRefreshSensorDataMethod;
    if ((getRefreshSensorDataMethod = UserMonitorAndControlServiceGrpc.getRefreshSensorDataMethod) == null) {
      synchronized (UserMonitorAndControlServiceGrpc.class) {
        if ((getRefreshSensorDataMethod = UserMonitorAndControlServiceGrpc.getRefreshSensorDataMethod) == null) {
          UserMonitorAndControlServiceGrpc.getRefreshSensorDataMethod = getRefreshSensorDataMethod =
              io.grpc.MethodDescriptor.<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq, com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "refreshSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes.getDefaultInstance()))
              .setSchemaDescriptor(new UserMonitorAndControlServiceMethodDescriptorSupplier("refreshSensorData"))
              .build();
        }
      }
    }
    return getRefreshSensorDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq,
      com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes> getRunEquipmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "runEquipment",
      requestType = com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq.class,
      responseType = com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq,
      com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes> getRunEquipmentMethod() {
    io.grpc.MethodDescriptor<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq, com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes> getRunEquipmentMethod;
    if ((getRunEquipmentMethod = UserMonitorAndControlServiceGrpc.getRunEquipmentMethod) == null) {
      synchronized (UserMonitorAndControlServiceGrpc.class) {
        if ((getRunEquipmentMethod = UserMonitorAndControlServiceGrpc.getRunEquipmentMethod) == null) {
          UserMonitorAndControlServiceGrpc.getRunEquipmentMethod = getRunEquipmentMethod =
              io.grpc.MethodDescriptor.<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq, com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "runEquipment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes.getDefaultInstance()))
              .setSchemaDescriptor(new UserMonitorAndControlServiceMethodDescriptorSupplier("runEquipment"))
              .build();
        }
      }
    }
    return getRunEquipmentMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserMonitorAndControlServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserMonitorAndControlServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserMonitorAndControlServiceStub>() {
        @java.lang.Override
        public UserMonitorAndControlServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserMonitorAndControlServiceStub(channel, callOptions);
        }
      };
    return UserMonitorAndControlServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserMonitorAndControlServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserMonitorAndControlServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserMonitorAndControlServiceBlockingStub>() {
        @java.lang.Override
        public UserMonitorAndControlServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserMonitorAndControlServiceBlockingStub(channel, callOptions);
        }
      };
    return UserMonitorAndControlServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserMonitorAndControlServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserMonitorAndControlServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserMonitorAndControlServiceFutureStub>() {
        @java.lang.Override
        public UserMonitorAndControlServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserMonitorAndControlServiceFutureStub(channel, callOptions);
        }
      };
    return UserMonitorAndControlServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void refreshSensorData(com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq request,
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRefreshSensorDataMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq> runEquipment(
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getRunEquipmentMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service UserMonitorAndControlService.
   */
  public static abstract class UserMonitorAndControlServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return UserMonitorAndControlServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service UserMonitorAndControlService.
   */
  public static final class UserMonitorAndControlServiceStub
      extends io.grpc.stub.AbstractAsyncStub<UserMonitorAndControlServiceStub> {
    private UserMonitorAndControlServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserMonitorAndControlServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserMonitorAndControlServiceStub(channel, callOptions);
    }

    /**
     */
    public void refreshSensorData(com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq request,
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getRefreshSensorDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq> runEquipment(
        io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getRunEquipmentMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service UserMonitorAndControlService.
   */
  public static final class UserMonitorAndControlServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<UserMonitorAndControlServiceBlockingStub> {
    private UserMonitorAndControlServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserMonitorAndControlServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserMonitorAndControlServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes> refreshSensorData(
        com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getRefreshSensorDataMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service UserMonitorAndControlService.
   */
  public static final class UserMonitorAndControlServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<UserMonitorAndControlServiceFutureStub> {
    private UserMonitorAndControlServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserMonitorAndControlServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserMonitorAndControlServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_REFRESH_SENSOR_DATA = 0;
  private static final int METHODID_RUN_EQUIPMENT = 1;

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
        case METHODID_REFRESH_SENSOR_DATA:
          serviceImpl.refreshSensorData((com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq) request,
              (io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes>) responseObserver);
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
        case METHODID_RUN_EQUIPMENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.runEquipment(
              (io.grpc.stub.StreamObserver<com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRefreshSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataReq,
              com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RefreshSensorDataRes>(
                service, METHODID_REFRESH_SENSOR_DATA)))
        .addMethod(
          getRunEquipmentMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.RunEquipReq,
              com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.EquipRunningStatusRes>(
                service, METHODID_RUN_EQUIPMENT)))
        .build();
  }

  private static abstract class UserMonitorAndControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserMonitorAndControlServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.grpcproject.smartfarm.UserMonitorAndControlService.UserMonitorAndControlServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserMonitorAndControlService");
    }
  }

  private static final class UserMonitorAndControlServiceFileDescriptorSupplier
      extends UserMonitorAndControlServiceBaseDescriptorSupplier {
    UserMonitorAndControlServiceFileDescriptorSupplier() {}
  }

  private static final class UserMonitorAndControlServiceMethodDescriptorSupplier
      extends UserMonitorAndControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    UserMonitorAndControlServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (UserMonitorAndControlServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserMonitorAndControlServiceFileDescriptorSupplier())
              .addMethod(getRefreshSensorDataMethod())
              .addMethod(getRunEquipmentMethod())
              .build();
        }
      }
    }
    return result;
  }
}
