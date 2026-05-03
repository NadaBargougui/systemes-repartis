package com.distributed.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.0)",
    comments = "Source: tasks.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TaskServiceGrpc {

  private TaskServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "tasks.TaskService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.distributed.grpc.TaskRequest,
      com.distributed.grpc.TaskResponse> getSubmitTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitTask",
      requestType = com.distributed.grpc.TaskRequest.class,
      responseType = com.distributed.grpc.TaskResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.distributed.grpc.TaskRequest,
      com.distributed.grpc.TaskResponse> getSubmitTaskMethod() {
    io.grpc.MethodDescriptor<com.distributed.grpc.TaskRequest, com.distributed.grpc.TaskResponse> getSubmitTaskMethod;
    if ((getSubmitTaskMethod = TaskServiceGrpc.getSubmitTaskMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getSubmitTaskMethod = TaskServiceGrpc.getSubmitTaskMethod) == null) {
          TaskServiceGrpc.getSubmitTaskMethod = getSubmitTaskMethod =
              io.grpc.MethodDescriptor.<com.distributed.grpc.TaskRequest, com.distributed.grpc.TaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.TaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.TaskResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("SubmitTask"))
              .build();
        }
      }
    }
    return getSubmitTaskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.distributed.grpc.Empty,
      com.distributed.grpc.LeaderResponse> getGetLeaderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLeader",
      requestType = com.distributed.grpc.Empty.class,
      responseType = com.distributed.grpc.LeaderResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.distributed.grpc.Empty,
      com.distributed.grpc.LeaderResponse> getGetLeaderMethod() {
    io.grpc.MethodDescriptor<com.distributed.grpc.Empty, com.distributed.grpc.LeaderResponse> getGetLeaderMethod;
    if ((getGetLeaderMethod = TaskServiceGrpc.getGetLeaderMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getGetLeaderMethod = TaskServiceGrpc.getGetLeaderMethod) == null) {
          TaskServiceGrpc.getGetLeaderMethod = getGetLeaderMethod =
              io.grpc.MethodDescriptor.<com.distributed.grpc.Empty, com.distributed.grpc.LeaderResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLeader"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.LeaderResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("GetLeader"))
              .build();
        }
      }
    }
    return getGetLeaderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.distributed.grpc.ElectionMessage,
      com.distributed.grpc.ElectionResponse> getElectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Elect",
      requestType = com.distributed.grpc.ElectionMessage.class,
      responseType = com.distributed.grpc.ElectionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.distributed.grpc.ElectionMessage,
      com.distributed.grpc.ElectionResponse> getElectMethod() {
    io.grpc.MethodDescriptor<com.distributed.grpc.ElectionMessage, com.distributed.grpc.ElectionResponse> getElectMethod;
    if ((getElectMethod = TaskServiceGrpc.getElectMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getElectMethod = TaskServiceGrpc.getElectMethod) == null) {
          TaskServiceGrpc.getElectMethod = getElectMethod =
              io.grpc.MethodDescriptor.<com.distributed.grpc.ElectionMessage, com.distributed.grpc.ElectionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Elect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.ElectionMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.ElectionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("Elect"))
              .build();
        }
      }
    }
    return getElectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.distributed.grpc.LeaderResponse,
      com.distributed.grpc.Empty> getAnnounceLeaderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AnnounceLeader",
      requestType = com.distributed.grpc.LeaderResponse.class,
      responseType = com.distributed.grpc.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.distributed.grpc.LeaderResponse,
      com.distributed.grpc.Empty> getAnnounceLeaderMethod() {
    io.grpc.MethodDescriptor<com.distributed.grpc.LeaderResponse, com.distributed.grpc.Empty> getAnnounceLeaderMethod;
    if ((getAnnounceLeaderMethod = TaskServiceGrpc.getAnnounceLeaderMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getAnnounceLeaderMethod = TaskServiceGrpc.getAnnounceLeaderMethod) == null) {
          TaskServiceGrpc.getAnnounceLeaderMethod = getAnnounceLeaderMethod =
              io.grpc.MethodDescriptor.<com.distributed.grpc.LeaderResponse, com.distributed.grpc.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AnnounceLeader"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.LeaderResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("AnnounceLeader"))
              .build();
        }
      }
    }
    return getAnnounceLeaderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.distributed.grpc.Empty,
      com.distributed.grpc.Empty> getHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Heartbeat",
      requestType = com.distributed.grpc.Empty.class,
      responseType = com.distributed.grpc.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.distributed.grpc.Empty,
      com.distributed.grpc.Empty> getHeartbeatMethod() {
    io.grpc.MethodDescriptor<com.distributed.grpc.Empty, com.distributed.grpc.Empty> getHeartbeatMethod;
    if ((getHeartbeatMethod = TaskServiceGrpc.getHeartbeatMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getHeartbeatMethod = TaskServiceGrpc.getHeartbeatMethod) == null) {
          TaskServiceGrpc.getHeartbeatMethod = getHeartbeatMethod =
              io.grpc.MethodDescriptor.<com.distributed.grpc.Empty, com.distributed.grpc.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Heartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("Heartbeat"))
              .build();
        }
      }
    }
    return getHeartbeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.distributed.grpc.LockRequest,
      com.distributed.grpc.LockResponse> getAcquireLockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AcquireLock",
      requestType = com.distributed.grpc.LockRequest.class,
      responseType = com.distributed.grpc.LockResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.distributed.grpc.LockRequest,
      com.distributed.grpc.LockResponse> getAcquireLockMethod() {
    io.grpc.MethodDescriptor<com.distributed.grpc.LockRequest, com.distributed.grpc.LockResponse> getAcquireLockMethod;
    if ((getAcquireLockMethod = TaskServiceGrpc.getAcquireLockMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getAcquireLockMethod = TaskServiceGrpc.getAcquireLockMethod) == null) {
          TaskServiceGrpc.getAcquireLockMethod = getAcquireLockMethod =
              io.grpc.MethodDescriptor.<com.distributed.grpc.LockRequest, com.distributed.grpc.LockResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AcquireLock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.LockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.LockResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("AcquireLock"))
              .build();
        }
      }
    }
    return getAcquireLockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.distributed.grpc.LockRequest,
      com.distributed.grpc.Empty> getReleaseLockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReleaseLock",
      requestType = com.distributed.grpc.LockRequest.class,
      responseType = com.distributed.grpc.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.distributed.grpc.LockRequest,
      com.distributed.grpc.Empty> getReleaseLockMethod() {
    io.grpc.MethodDescriptor<com.distributed.grpc.LockRequest, com.distributed.grpc.Empty> getReleaseLockMethod;
    if ((getReleaseLockMethod = TaskServiceGrpc.getReleaseLockMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getReleaseLockMethod = TaskServiceGrpc.getReleaseLockMethod) == null) {
          TaskServiceGrpc.getReleaseLockMethod = getReleaseLockMethod =
              io.grpc.MethodDescriptor.<com.distributed.grpc.LockRequest, com.distributed.grpc.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReleaseLock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.LockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.distributed.grpc.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("ReleaseLock"))
              .build();
        }
      }
    }
    return getReleaseLockMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TaskServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskServiceStub>() {
        @java.lang.Override
        public TaskServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskServiceStub(channel, callOptions);
        }
      };
    return TaskServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TaskServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskServiceBlockingStub>() {
        @java.lang.Override
        public TaskServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskServiceBlockingStub(channel, callOptions);
        }
      };
    return TaskServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TaskServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskServiceFutureStub>() {
        @java.lang.Override
        public TaskServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskServiceFutureStub(channel, callOptions);
        }
      };
    return TaskServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void submitTask(com.distributed.grpc.TaskRequest request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.TaskResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitTaskMethod(), responseObserver);
    }

    /**
     */
    default void getLeader(com.distributed.grpc.Empty request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.LeaderResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLeaderMethod(), responseObserver);
    }

    /**
     */
    default void elect(com.distributed.grpc.ElectionMessage request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.ElectionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getElectMethod(), responseObserver);
    }

    /**
     */
    default void announceLeader(com.distributed.grpc.LeaderResponse request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAnnounceLeaderMethod(), responseObserver);
    }

    /**
     */
    default void heartbeat(com.distributed.grpc.Empty request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHeartbeatMethod(), responseObserver);
    }

    /**
     */
    default void acquireLock(com.distributed.grpc.LockRequest request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.LockResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAcquireLockMethod(), responseObserver);
    }

    /**
     */
    default void releaseLock(com.distributed.grpc.LockRequest request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReleaseLockMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service TaskService.
   */
  public static abstract class TaskServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return TaskServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service TaskService.
   */
  public static final class TaskServiceStub
      extends io.grpc.stub.AbstractAsyncStub<TaskServiceStub> {
    private TaskServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskServiceStub(channel, callOptions);
    }

    /**
     */
    public void submitTask(com.distributed.grpc.TaskRequest request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.TaskResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitTaskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLeader(com.distributed.grpc.Empty request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.LeaderResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLeaderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void elect(com.distributed.grpc.ElectionMessage request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.ElectionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getElectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void announceLeader(com.distributed.grpc.LeaderResponse request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAnnounceLeaderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void heartbeat(com.distributed.grpc.Empty request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void acquireLock(com.distributed.grpc.LockRequest request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.LockResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAcquireLockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void releaseLock(com.distributed.grpc.LockRequest request,
        io.grpc.stub.StreamObserver<com.distributed.grpc.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReleaseLockMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service TaskService.
   */
  public static final class TaskServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TaskServiceBlockingStub> {
    private TaskServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.distributed.grpc.TaskResponse submitTask(com.distributed.grpc.TaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitTaskMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.distributed.grpc.LeaderResponse getLeader(com.distributed.grpc.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLeaderMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.distributed.grpc.ElectionResponse elect(com.distributed.grpc.ElectionMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getElectMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.distributed.grpc.Empty announceLeader(com.distributed.grpc.LeaderResponse request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAnnounceLeaderMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.distributed.grpc.Empty heartbeat(com.distributed.grpc.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHeartbeatMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.distributed.grpc.LockResponse acquireLock(com.distributed.grpc.LockRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAcquireLockMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.distributed.grpc.Empty releaseLock(com.distributed.grpc.LockRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReleaseLockMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service TaskService.
   */
  public static final class TaskServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<TaskServiceFutureStub> {
    private TaskServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.distributed.grpc.TaskResponse> submitTask(
        com.distributed.grpc.TaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitTaskMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.distributed.grpc.LeaderResponse> getLeader(
        com.distributed.grpc.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLeaderMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.distributed.grpc.ElectionResponse> elect(
        com.distributed.grpc.ElectionMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getElectMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.distributed.grpc.Empty> announceLeader(
        com.distributed.grpc.LeaderResponse request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAnnounceLeaderMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.distributed.grpc.Empty> heartbeat(
        com.distributed.grpc.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.distributed.grpc.LockResponse> acquireLock(
        com.distributed.grpc.LockRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAcquireLockMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.distributed.grpc.Empty> releaseLock(
        com.distributed.grpc.LockRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReleaseLockMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBMIT_TASK = 0;
  private static final int METHODID_GET_LEADER = 1;
  private static final int METHODID_ELECT = 2;
  private static final int METHODID_ANNOUNCE_LEADER = 3;
  private static final int METHODID_HEARTBEAT = 4;
  private static final int METHODID_ACQUIRE_LOCK = 5;
  private static final int METHODID_RELEASE_LOCK = 6;

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
        case METHODID_SUBMIT_TASK:
          serviceImpl.submitTask((com.distributed.grpc.TaskRequest) request,
              (io.grpc.stub.StreamObserver<com.distributed.grpc.TaskResponse>) responseObserver);
          break;
        case METHODID_GET_LEADER:
          serviceImpl.getLeader((com.distributed.grpc.Empty) request,
              (io.grpc.stub.StreamObserver<com.distributed.grpc.LeaderResponse>) responseObserver);
          break;
        case METHODID_ELECT:
          serviceImpl.elect((com.distributed.grpc.ElectionMessage) request,
              (io.grpc.stub.StreamObserver<com.distributed.grpc.ElectionResponse>) responseObserver);
          break;
        case METHODID_ANNOUNCE_LEADER:
          serviceImpl.announceLeader((com.distributed.grpc.LeaderResponse) request,
              (io.grpc.stub.StreamObserver<com.distributed.grpc.Empty>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((com.distributed.grpc.Empty) request,
              (io.grpc.stub.StreamObserver<com.distributed.grpc.Empty>) responseObserver);
          break;
        case METHODID_ACQUIRE_LOCK:
          serviceImpl.acquireLock((com.distributed.grpc.LockRequest) request,
              (io.grpc.stub.StreamObserver<com.distributed.grpc.LockResponse>) responseObserver);
          break;
        case METHODID_RELEASE_LOCK:
          serviceImpl.releaseLock((com.distributed.grpc.LockRequest) request,
              (io.grpc.stub.StreamObserver<com.distributed.grpc.Empty>) responseObserver);
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
          getSubmitTaskMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.distributed.grpc.TaskRequest,
              com.distributed.grpc.TaskResponse>(
                service, METHODID_SUBMIT_TASK)))
        .addMethod(
          getGetLeaderMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.distributed.grpc.Empty,
              com.distributed.grpc.LeaderResponse>(
                service, METHODID_GET_LEADER)))
        .addMethod(
          getElectMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.distributed.grpc.ElectionMessage,
              com.distributed.grpc.ElectionResponse>(
                service, METHODID_ELECT)))
        .addMethod(
          getAnnounceLeaderMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.distributed.grpc.LeaderResponse,
              com.distributed.grpc.Empty>(
                service, METHODID_ANNOUNCE_LEADER)))
        .addMethod(
          getHeartbeatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.distributed.grpc.Empty,
              com.distributed.grpc.Empty>(
                service, METHODID_HEARTBEAT)))
        .addMethod(
          getAcquireLockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.distributed.grpc.LockRequest,
              com.distributed.grpc.LockResponse>(
                service, METHODID_ACQUIRE_LOCK)))
        .addMethod(
          getReleaseLockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.distributed.grpc.LockRequest,
              com.distributed.grpc.Empty>(
                service, METHODID_RELEASE_LOCK)))
        .build();
  }

  private static abstract class TaskServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TaskServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.distributed.grpc.TasksProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TaskService");
    }
  }

  private static final class TaskServiceFileDescriptorSupplier
      extends TaskServiceBaseDescriptorSupplier {
    TaskServiceFileDescriptorSupplier() {}
  }

  private static final class TaskServiceMethodDescriptorSupplier
      extends TaskServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    TaskServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (TaskServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TaskServiceFileDescriptorSupplier())
              .addMethod(getSubmitTaskMethod())
              .addMethod(getGetLeaderMethod())
              .addMethod(getElectMethod())
              .addMethod(getAnnounceLeaderMethod())
              .addMethod(getHeartbeatMethod())
              .addMethod(getAcquireLockMethod())
              .addMethod(getReleaseLockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
