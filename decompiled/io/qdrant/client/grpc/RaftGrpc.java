/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Empty;
import io.grpc.BindableService;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import io.grpc.protobuf.ProtoFileDescriptorSupplier;
import io.grpc.protobuf.ProtoMethodDescriptorSupplier;
import io.grpc.protobuf.ProtoServiceDescriptorSupplier;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.AbstractAsyncStub;
import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.AbstractFutureStub;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.annotations.GrpcGenerated;
import io.grpc.stub.annotations.RpcMethod;
import io.qdrant.client.grpc.RaftService;

@GrpcGenerated
public final class RaftGrpc {
    public static final String SERVICE_NAME = "qdrant.Raft";
    private static volatile MethodDescriptor<RaftService.RaftMessage, Empty> getSendMethod;
    private static volatile MethodDescriptor<RaftService.PeerId, RaftService.Uri> getWhoIsMethod;
    private static volatile MethodDescriptor<RaftService.AddPeerToKnownMessage, RaftService.AllPeers> getAddPeerToKnownMethod;
    private static volatile MethodDescriptor<RaftService.PeerId, Empty> getAddPeerAsParticipantMethod;
    private static final int METHODID_SEND = 0;
    private static final int METHODID_WHO_IS = 1;
    private static final int METHODID_ADD_PEER_TO_KNOWN = 2;
    private static final int METHODID_ADD_PEER_AS_PARTICIPANT = 3;
    private static volatile ServiceDescriptor serviceDescriptor;

    private RaftGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Raft/Send", requestType=RaftService.RaftMessage.class, responseType=Empty.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<RaftService.RaftMessage, Empty> getSendMethod() {
        MethodDescriptor<RaftService.RaftMessage, Empty> getSendMethod = RaftGrpc.getSendMethod;
        if (getSendMethod != null) return getSendMethod;
        Class<RaftGrpc> clazz = RaftGrpc.class;
        synchronized (RaftGrpc.class) {
            getSendMethod = RaftGrpc.getSendMethod;
            if (getSendMethod != null) return getSendMethod;
            RaftGrpc.getSendMethod = getSendMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Send")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(RaftService.RaftMessage.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Empty.getDefaultInstance())).setSchemaDescriptor(new RaftMethodDescriptorSupplier("Send")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getSendMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Raft/WhoIs", requestType=RaftService.PeerId.class, responseType=RaftService.Uri.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<RaftService.PeerId, RaftService.Uri> getWhoIsMethod() {
        MethodDescriptor<RaftService.PeerId, RaftService.Uri> getWhoIsMethod = RaftGrpc.getWhoIsMethod;
        if (getWhoIsMethod != null) return getWhoIsMethod;
        Class<RaftGrpc> clazz = RaftGrpc.class;
        synchronized (RaftGrpc.class) {
            getWhoIsMethod = RaftGrpc.getWhoIsMethod;
            if (getWhoIsMethod != null) return getWhoIsMethod;
            RaftGrpc.getWhoIsMethod = getWhoIsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "WhoIs")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(RaftService.PeerId.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(RaftService.Uri.getDefaultInstance())).setSchemaDescriptor(new RaftMethodDescriptorSupplier("WhoIs")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getWhoIsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Raft/AddPeerToKnown", requestType=RaftService.AddPeerToKnownMessage.class, responseType=RaftService.AllPeers.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<RaftService.AddPeerToKnownMessage, RaftService.AllPeers> getAddPeerToKnownMethod() {
        MethodDescriptor<RaftService.AddPeerToKnownMessage, RaftService.AllPeers> getAddPeerToKnownMethod = RaftGrpc.getAddPeerToKnownMethod;
        if (getAddPeerToKnownMethod != null) return getAddPeerToKnownMethod;
        Class<RaftGrpc> clazz = RaftGrpc.class;
        synchronized (RaftGrpc.class) {
            getAddPeerToKnownMethod = RaftGrpc.getAddPeerToKnownMethod;
            if (getAddPeerToKnownMethod != null) return getAddPeerToKnownMethod;
            RaftGrpc.getAddPeerToKnownMethod = getAddPeerToKnownMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "AddPeerToKnown")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(RaftService.AddPeerToKnownMessage.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(RaftService.AllPeers.getDefaultInstance())).setSchemaDescriptor(new RaftMethodDescriptorSupplier("AddPeerToKnown")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getAddPeerToKnownMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Raft/AddPeerAsParticipant", requestType=RaftService.PeerId.class, responseType=Empty.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<RaftService.PeerId, Empty> getAddPeerAsParticipantMethod() {
        MethodDescriptor<RaftService.PeerId, Empty> getAddPeerAsParticipantMethod = RaftGrpc.getAddPeerAsParticipantMethod;
        if (getAddPeerAsParticipantMethod != null) return getAddPeerAsParticipantMethod;
        Class<RaftGrpc> clazz = RaftGrpc.class;
        synchronized (RaftGrpc.class) {
            getAddPeerAsParticipantMethod = RaftGrpc.getAddPeerAsParticipantMethod;
            if (getAddPeerAsParticipantMethod != null) return getAddPeerAsParticipantMethod;
            RaftGrpc.getAddPeerAsParticipantMethod = getAddPeerAsParticipantMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "AddPeerAsParticipant")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(RaftService.PeerId.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Empty.getDefaultInstance())).setSchemaDescriptor(new RaftMethodDescriptorSupplier("AddPeerAsParticipant")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getAddPeerAsParticipantMethod;
        }
    }

    public static RaftStub newStub(Channel channel) {
        AbstractStub.StubFactory<RaftStub> factory2 = new AbstractStub.StubFactory<RaftStub>(){

            @Override
            public RaftStub newStub(Channel channel, CallOptions callOptions) {
                return new RaftStub(channel, callOptions);
            }
        };
        return RaftStub.newStub(factory2, channel);
    }

    public static RaftBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<RaftBlockingStub> factory2 = new AbstractStub.StubFactory<RaftBlockingStub>(){

            @Override
            public RaftBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new RaftBlockingStub(channel, callOptions);
            }
        };
        return RaftBlockingStub.newStub(factory2, channel);
    }

    public static RaftFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<RaftFutureStub> factory2 = new AbstractStub.StubFactory<RaftFutureStub>(){

            @Override
            public RaftFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new RaftFutureStub(channel, callOptions);
            }
        };
        return RaftFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(RaftGrpc.getServiceDescriptor()).addMethod(RaftGrpc.getSendMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(RaftGrpc.getWhoIsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).addMethod(RaftGrpc.getAddPeerToKnownMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 2))).addMethod(RaftGrpc.getAddPeerAsParticipantMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 3))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<RaftGrpc> clazz = RaftGrpc.class;
        synchronized (RaftGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new RaftFileDescriptorSupplier()).addMethod(RaftGrpc.getSendMethod()).addMethod(RaftGrpc.getWhoIsMethod()).addMethod(RaftGrpc.getAddPeerToKnownMethod()).addMethod(RaftGrpc.getAddPeerAsParticipantMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class RaftMethodDescriptorSupplier
    extends RaftBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        RaftMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class RaftStub
    extends AbstractAsyncStub<RaftStub> {
        private RaftStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected RaftStub build(Channel channel, CallOptions callOptions) {
            return new RaftStub(channel, callOptions);
        }

        public void send(RaftService.RaftMessage request, StreamObserver<Empty> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(RaftGrpc.getSendMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void whoIs(RaftService.PeerId request, StreamObserver<RaftService.Uri> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(RaftGrpc.getWhoIsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void addPeerToKnown(RaftService.AddPeerToKnownMessage request, StreamObserver<RaftService.AllPeers> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(RaftGrpc.getAddPeerToKnownMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void addPeerAsParticipant(RaftService.PeerId request, StreamObserver<Empty> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(RaftGrpc.getAddPeerAsParticipantMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class RaftBlockingStub
    extends AbstractBlockingStub<RaftBlockingStub> {
        private RaftBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected RaftBlockingStub build(Channel channel, CallOptions callOptions) {
            return new RaftBlockingStub(channel, callOptions);
        }

        public Empty send(RaftService.RaftMessage request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), RaftGrpc.getSendMethod(), this.getCallOptions(), request);
        }

        public RaftService.Uri whoIs(RaftService.PeerId request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), RaftGrpc.getWhoIsMethod(), this.getCallOptions(), request);
        }

        public RaftService.AllPeers addPeerToKnown(RaftService.AddPeerToKnownMessage request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), RaftGrpc.getAddPeerToKnownMethod(), this.getCallOptions(), request);
        }

        public Empty addPeerAsParticipant(RaftService.PeerId request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), RaftGrpc.getAddPeerAsParticipantMethod(), this.getCallOptions(), request);
        }
    }

    public static final class RaftFutureStub
    extends AbstractFutureStub<RaftFutureStub> {
        private RaftFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected RaftFutureStub build(Channel channel, CallOptions callOptions) {
            return new RaftFutureStub(channel, callOptions);
        }

        public ListenableFuture<Empty> send(RaftService.RaftMessage request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(RaftGrpc.getSendMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<RaftService.Uri> whoIs(RaftService.PeerId request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(RaftGrpc.getWhoIsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<RaftService.AllPeers> addPeerToKnown(RaftService.AddPeerToKnownMessage request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(RaftGrpc.getAddPeerToKnownMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Empty> addPeerAsParticipant(RaftService.PeerId request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(RaftGrpc.getAddPeerAsParticipantMethod(), this.getCallOptions()), request);
        }
    }

    private static final class MethodHandlers<Req, Resp>
    implements ServerCalls.UnaryMethod<Req, Resp>,
    ServerCalls.ServerStreamingMethod<Req, Resp>,
    ServerCalls.ClientStreamingMethod<Req, Resp>,
    ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final AsyncService serviceImpl;
        private final int methodId;

        MethodHandlers(AsyncService serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        public void invoke(Req request, StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                case 0: {
                    this.serviceImpl.send((RaftService.RaftMessage)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.whoIs((RaftService.PeerId)request, responseObserver);
                    break;
                }
                case 2: {
                    this.serviceImpl.addPeerToKnown((RaftService.AddPeerToKnownMessage)request, responseObserver);
                    break;
                }
                case 3: {
                    this.serviceImpl.addPeerAsParticipant((RaftService.PeerId)request, responseObserver);
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }

        @Override
        public StreamObserver<Req> invoke(StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                default: 
            }
            throw new AssertionError();
        }
    }

    public static interface AsyncService {
        default public void send(RaftService.RaftMessage request, StreamObserver<Empty> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(RaftGrpc.getSendMethod(), responseObserver);
        }

        default public void whoIs(RaftService.PeerId request, StreamObserver<RaftService.Uri> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(RaftGrpc.getWhoIsMethod(), responseObserver);
        }

        default public void addPeerToKnown(RaftService.AddPeerToKnownMessage request, StreamObserver<RaftService.AllPeers> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(RaftGrpc.getAddPeerToKnownMethod(), responseObserver);
        }

        default public void addPeerAsParticipant(RaftService.PeerId request, StreamObserver<Empty> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(RaftGrpc.getAddPeerAsParticipantMethod(), responseObserver);
        }
    }

    private static final class RaftFileDescriptorSupplier
    extends RaftBaseDescriptorSupplier {
        RaftFileDescriptorSupplier() {
        }
    }

    private static abstract class RaftBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        RaftBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return RaftService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("Raft");
        }
    }

    public static abstract class RaftImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return RaftGrpc.bindService(this);
        }
    }
}

