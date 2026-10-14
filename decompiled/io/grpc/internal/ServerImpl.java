/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.BinaryLog;
import io.grpc.CompressorRegistry;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Deadline;
import io.grpc.Decompressor;
import io.grpc.DecompressorRegistry;
import io.grpc.HandlerRegistry;
import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.InternalServer;
import io.grpc.InternalServerInterceptors;
import io.grpc.InternalStatus;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerCall;
import io.grpc.ServerCallExecutorSupplier;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServerTransportFilter;
import io.grpc.Status;
import io.grpc.internal.CallTracer;
import io.grpc.internal.ContextRunnable;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.SerializeReentrantCallsDirectExecutor;
import io.grpc.internal.SerializingExecutor;
import io.grpc.internal.ServerCallImpl;
import io.grpc.internal.ServerCallInfoImpl;
import io.grpc.internal.ServerImplBuilder;
import io.grpc.internal.ServerListener;
import io.grpc.internal.ServerStream;
import io.grpc.internal.ServerStreamListener;
import io.grpc.internal.ServerTransport;
import io.grpc.internal.ServerTransportListener;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.StreamListener;
import io.perfmark.Link;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

public final class ServerImpl
extends Server
implements InternalInstrumented<InternalChannelz.ServerStats> {
    private static final Logger log = Logger.getLogger(ServerImpl.class.getName());
    private static final ServerStreamListener NOOP_LISTENER = new NoopListener();
    private final InternalLogId logId;
    private final ObjectPool<? extends Executor> executorPool;
    private Executor executor;
    private final HandlerRegistry registry;
    private final HandlerRegistry fallbackRegistry;
    private final List<ServerTransportFilter> transportFilters;
    private final ServerInterceptor[] interceptors;
    private final long handshakeTimeoutMillis;
    @GuardedBy(value="lock")
    private boolean started;
    @GuardedBy(value="lock")
    private boolean shutdown;
    @GuardedBy(value="lock")
    private Status shutdownNowStatus;
    @GuardedBy(value="lock")
    private boolean serverShutdownCallbackInvoked;
    @GuardedBy(value="lock")
    private boolean terminated;
    private final io.grpc.internal.InternalServer transportServer;
    private final Object lock = new Object();
    @GuardedBy(value="lock")
    private boolean transportServersTerminated;
    @GuardedBy(value="lock")
    private final Set<ServerTransport> transports = new HashSet<ServerTransport>();
    private final Context rootContext;
    private final DecompressorRegistry decompressorRegistry;
    private final CompressorRegistry compressorRegistry;
    private final BinaryLog binlog;
    private final InternalChannelz channelz;
    private final CallTracer serverCallTracer;
    private final Deadline.Ticker ticker;
    private final ServerCallExecutorSupplier executorSupplier;

    ServerImpl(ServerImplBuilder builder, io.grpc.internal.InternalServer transportServer, Context rootContext) {
        this.executorPool = Preconditions.checkNotNull(builder.executorPool, "executorPool");
        this.registry = Preconditions.checkNotNull(builder.registryBuilder.build(), "registryBuilder");
        this.fallbackRegistry = Preconditions.checkNotNull(builder.fallbackRegistry, "fallbackRegistry");
        this.transportServer = Preconditions.checkNotNull(transportServer, "transportServer");
        this.logId = InternalLogId.allocate("Server", String.valueOf(this.getListenSocketsIgnoringLifecycle()));
        this.rootContext = Preconditions.checkNotNull(rootContext, "rootContext").fork();
        this.decompressorRegistry = builder.decompressorRegistry;
        this.compressorRegistry = builder.compressorRegistry;
        this.transportFilters = Collections.unmodifiableList(new ArrayList<ServerTransportFilter>(builder.transportFilters));
        this.interceptors = builder.interceptors.toArray(new ServerInterceptor[builder.interceptors.size()]);
        this.handshakeTimeoutMillis = builder.handshakeTimeoutMillis;
        this.binlog = builder.binlog;
        this.channelz = builder.channelz;
        this.serverCallTracer = builder.callTracerFactory.create();
        this.ticker = Preconditions.checkNotNull(builder.ticker, "ticker");
        this.channelz.addServer(this);
        this.executorSupplier = builder.executorSupplier;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ServerImpl start() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            Preconditions.checkState(!this.started, "Already started");
            Preconditions.checkState(!this.shutdown, "Shutting down");
            ServerListenerImpl listener = new ServerListenerImpl();
            this.transportServer.start(listener);
            this.executor = Preconditions.checkNotNull(this.executorPool.getObject(), "executor");
            this.started = true;
            return this;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getPort() {
        Object object = this.lock;
        synchronized (object) {
            Preconditions.checkState(this.started, "Not started");
            Preconditions.checkState(!this.terminated, "Already terminated");
            for (SocketAddress socketAddress : this.transportServer.getListenSocketAddresses()) {
                if (!(socketAddress instanceof InetSocketAddress)) continue;
                return ((InetSocketAddress)socketAddress).getPort();
            }
            return -1;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<SocketAddress> getListenSockets() {
        Object object = this.lock;
        synchronized (object) {
            Preconditions.checkState(this.started, "Not started");
            Preconditions.checkState(!this.terminated, "Already terminated");
            return this.getListenSocketsIgnoringLifecycle();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<SocketAddress> getListenSocketsIgnoringLifecycle() {
        Object object = this.lock;
        synchronized (object) {
            return Collections.unmodifiableList(this.transportServer.getListenSocketAddresses());
        }
    }

    @Override
    public List<ServerServiceDefinition> getServices() {
        List<ServerServiceDefinition> fallbackServices = this.fallbackRegistry.getServices();
        if (fallbackServices.isEmpty()) {
            return this.registry.getServices();
        }
        List<ServerServiceDefinition> registryServices = this.registry.getServices();
        int servicesCount = registryServices.size() + fallbackServices.size();
        ArrayList<ServerServiceDefinition> services = new ArrayList<ServerServiceDefinition>(servicesCount);
        services.addAll(registryServices);
        services.addAll(fallbackServices);
        return Collections.unmodifiableList(services);
    }

    @Override
    public List<ServerServiceDefinition> getImmutableServices() {
        return this.registry.getServices();
    }

    @Override
    public List<ServerServiceDefinition> getMutableServices() {
        return Collections.unmodifiableList(this.fallbackRegistry.getServices());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ServerImpl shutdown() {
        boolean shutdownTransportServers;
        Object object = this.lock;
        synchronized (object) {
            if (this.shutdown) {
                return this;
            }
            this.shutdown = true;
            shutdownTransportServers = this.started;
            if (!shutdownTransportServers) {
                this.transportServersTerminated = true;
                this.checkForTermination();
            }
        }
        if (shutdownTransportServers) {
            this.transportServer.shutdown();
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ServerImpl shutdownNow() {
        boolean savedServerShutdownCallbackInvoked;
        ArrayList<ServerTransport> transportsCopy;
        this.shutdown();
        Status nowStatus = Status.UNAVAILABLE.withDescription("Server shutdownNow invoked");
        Iterator iterator2 = this.lock;
        synchronized (iterator2) {
            if (this.shutdownNowStatus != null) {
                return this;
            }
            this.shutdownNowStatus = nowStatus;
            transportsCopy = new ArrayList<ServerTransport>(this.transports);
            savedServerShutdownCallbackInvoked = this.serverShutdownCallbackInvoked;
        }
        if (savedServerShutdownCallbackInvoked) {
            for (ServerTransport transport : transportsCopy) {
                transport.shutdownNow(nowStatus);
            }
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isShutdown() {
        Object object = this.lock;
        synchronized (object) {
            return this.shutdown;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean awaitTermination(long timeout2, TimeUnit unit) throws InterruptedException {
        Object object = this.lock;
        synchronized (object) {
            long timeoutNanos = unit.toNanos(timeout2);
            long endTimeNanos = System.nanoTime() + timeoutNanos;
            while (!this.terminated && (timeoutNanos = endTimeNanos - System.nanoTime()) > 0L) {
                TimeUnit.NANOSECONDS.timedWait(this.lock, timeoutNanos);
            }
            return this.terminated;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void awaitTermination() throws InterruptedException {
        Object object = this.lock;
        synchronized (object) {
            while (!this.terminated) {
                this.lock.wait();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isTerminated() {
        Object object = this.lock;
        synchronized (object) {
            return this.terminated;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void transportClosed(ServerTransport transport) {
        Object object = this.lock;
        synchronized (object) {
            if (!this.transports.remove(transport)) {
                throw new AssertionError((Object)"Transport already removed");
            }
            this.channelz.removeServerSocket(this, transport);
            this.checkForTermination();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkForTermination() {
        Object object = this.lock;
        synchronized (object) {
            if (this.shutdown && this.transports.isEmpty() && this.transportServersTerminated) {
                if (this.terminated) {
                    throw new AssertionError((Object)"Server already terminated");
                }
                this.terminated = true;
                this.channelz.removeServer(this);
                if (this.executor != null) {
                    this.executor = this.executorPool.returnObject(this.executor);
                }
                this.lock.notifyAll();
            }
        }
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    @Override
    public ListenableFuture<InternalChannelz.ServerStats> getStats() {
        InternalChannelz.ServerStats.Builder builder = new InternalChannelz.ServerStats.Builder();
        List<InternalInstrumented<InternalChannelz.SocketStats>> stats = this.transportServer.getListenSocketStatsList();
        if (stats != null) {
            builder.addListenSockets(stats);
        }
        this.serverCallTracer.updateBuilder(builder);
        SettableFuture<InternalChannelz.ServerStats> ret = SettableFuture.create();
        ret.set(builder.build());
        return ret;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add("transportServer", this.transportServer).toString();
    }

    @VisibleForTesting
    static final class ContextCloser
    implements Runnable {
        private final Context.CancellableContext context;
        private final Throwable cause;

        ContextCloser(Context.CancellableContext context, Throwable cause) {
            this.context = context;
            this.cause = cause;
        }

        @Override
        public void run() {
            this.context.cancel(this.cause);
        }
    }

    @VisibleForTesting
    static final class JumpToApplicationThreadServerStreamListener
    implements ServerStreamListener {
        private final Executor callExecutor;
        private final Executor cancelExecutor;
        private final Context.CancellableContext context;
        private final ServerStream stream;
        private final Tag tag;
        private ServerStreamListener listener;

        public JumpToApplicationThreadServerStreamListener(Executor executor2, Executor cancelExecutor, ServerStream stream, Context.CancellableContext context, Tag tag) {
            this.callExecutor = executor2;
            this.cancelExecutor = cancelExecutor;
            this.stream = stream;
            this.context = context;
            this.tag = tag;
        }

        private ServerStreamListener getListener() {
            if (this.listener == null) {
                throw new IllegalStateException("listener unset");
            }
            return this.listener;
        }

        @VisibleForTesting
        void setListener(ServerStreamListener listener) {
            Preconditions.checkNotNull(listener, "listener must not be null");
            Preconditions.checkState(this.listener == null, "Listener already set");
            this.listener = listener;
        }

        private void internalClose(Throwable t2) {
            String description = "Application error processing RPC";
            this.stream.close(Status.UNKNOWN.withDescription(description).withCause(t2), new Metadata());
        }

        @Override
        public void messagesAvailable(final StreamListener.MessageProducer producer) {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.messagesAvailable");){
                PerfMark.attachTag(this.tag);
                final Link link = PerfMark.linkOut();
                final class MessagesAvailable
                extends ContextRunnable {
                    MessagesAvailable() {
                        super(JumpToApplicationThreadServerStreamListener.this.context);
                    }

                    @Override
                    public void runInContext() {
                        try (TaskCloseable ignore = PerfMark.traceTask("ServerCallListener(app).messagesAvailable");){
                            PerfMark.attachTag(JumpToApplicationThreadServerStreamListener.this.tag);
                            PerfMark.linkIn(link);
                            JumpToApplicationThreadServerStreamListener.this.getListener().messagesAvailable(producer);
                        }
                        catch (Throwable t2) {
                            JumpToApplicationThreadServerStreamListener.this.internalClose(t2);
                            throw t2;
                        }
                    }
                }
                this.callExecutor.execute(new MessagesAvailable());
            }
        }

        @Override
        public void halfClosed() {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.halfClosed");){
                PerfMark.attachTag(this.tag);
                final Link link = PerfMark.linkOut();
                final class HalfClosed
                extends ContextRunnable {
                    HalfClosed() {
                        super(JumpToApplicationThreadServerStreamListener.this.context);
                    }

                    @Override
                    public void runInContext() {
                        try (TaskCloseable ignore = PerfMark.traceTask("ServerCallListener(app).halfClosed");){
                            PerfMark.attachTag(JumpToApplicationThreadServerStreamListener.this.tag);
                            PerfMark.linkIn(link);
                            JumpToApplicationThreadServerStreamListener.this.getListener().halfClosed();
                        }
                        catch (Throwable t2) {
                            JumpToApplicationThreadServerStreamListener.this.internalClose(t2);
                            throw t2;
                        }
                    }
                }
                this.callExecutor.execute(new HalfClosed());
            }
        }

        @Override
        public void closed(Status status) {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.closed");){
                PerfMark.attachTag(this.tag);
                this.closedInternal(status);
            }
        }

        private void closedInternal(final Status status) {
            if (!status.isOk()) {
                Throwable cancelCause = status.getCause();
                if (cancelCause == null) {
                    cancelCause = InternalStatus.asRuntimeException(Status.CANCELLED.withDescription("RPC cancelled"), null, false);
                }
                this.cancelExecutor.execute(new ContextCloser(this.context, cancelCause));
            }
            final Link link = PerfMark.linkOut();
            final class Closed
            extends ContextRunnable {
                Closed() {
                    super(JumpToApplicationThreadServerStreamListener.this.context);
                }

                @Override
                public void runInContext() {
                    try (TaskCloseable ignore = PerfMark.traceTask("ServerCallListener(app).closed");){
                        PerfMark.attachTag(JumpToApplicationThreadServerStreamListener.this.tag);
                        PerfMark.linkIn(link);
                        JumpToApplicationThreadServerStreamListener.this.getListener().closed(status);
                    }
                }
            }
            this.callExecutor.execute(new Closed());
        }

        @Override
        public void onReady() {
            try (TaskCloseable ignore = PerfMark.traceTask("ServerStreamListener.onReady");){
                PerfMark.attachTag(this.tag);
                final Link link = PerfMark.linkOut();
                final class OnReady
                extends ContextRunnable {
                    OnReady() {
                        super(JumpToApplicationThreadServerStreamListener.this.context);
                    }

                    @Override
                    public void runInContext() {
                        try (TaskCloseable ignore = PerfMark.traceTask("ServerCallListener(app).onReady");){
                            PerfMark.attachTag(JumpToApplicationThreadServerStreamListener.this.tag);
                            PerfMark.linkIn(link);
                            JumpToApplicationThreadServerStreamListener.this.getListener().onReady();
                        }
                        catch (Throwable t2) {
                            JumpToApplicationThreadServerStreamListener.this.internalClose(t2);
                            throw t2;
                        }
                    }
                }
                this.callExecutor.execute(new OnReady());
            }
        }
    }

    private static final class NoopListener
    implements ServerStreamListener {
        private NoopListener() {
        }

        @Override
        public void messagesAvailable(StreamListener.MessageProducer producer) {
            InputStream message;
            while ((message = producer.next()) != null) {
                try {
                    message.close();
                }
                catch (IOException e) {
                    while ((message = producer.next()) != null) {
                        try {
                            message.close();
                        }
                        catch (IOException ioException) {
                            log.log(Level.WARNING, "Exception closing stream", ioException);
                        }
                    }
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void halfClosed() {
        }

        @Override
        public void closed(Status status) {
        }

        @Override
        public void onReady() {
        }
    }

    private final class ServerTransportListenerImpl
    implements ServerTransportListener {
        private final ServerTransport transport;
        private Future<?> handshakeTimeoutFuture;
        private Attributes attributes;

        ServerTransportListenerImpl(ServerTransport transport) {
            this.transport = transport;
        }

        public void init() {
            class TransportShutdownNow
            implements Runnable {
                TransportShutdownNow() {
                }

                @Override
                public void run() {
                    ServerTransportListenerImpl.this.transport.shutdownNow(Status.CANCELLED.withDescription("Handshake timeout exceeded"));
                }
            }
            this.handshakeTimeoutFuture = ServerImpl.this.handshakeTimeoutMillis != Long.MAX_VALUE ? this.transport.getScheduledExecutorService().schedule(new TransportShutdownNow(), ServerImpl.this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS) : new FutureTask<Object>(new Runnable(){

                @Override
                public void run() {
                }
            }, null);
            ServerImpl.this.channelz.addServerSocket(ServerImpl.this, this.transport);
        }

        @Override
        public Attributes transportReady(Attributes attributes) {
            this.handshakeTimeoutFuture.cancel(false);
            this.handshakeTimeoutFuture = null;
            for (ServerTransportFilter filter2 : ServerImpl.this.transportFilters) {
                attributes = Preconditions.checkNotNull(filter2.transportReady(attributes), "Filter %s returned null", (Object)filter2);
            }
            this.attributes = attributes;
            return attributes;
        }

        @Override
        public void transportTerminated() {
            if (this.handshakeTimeoutFuture != null) {
                this.handshakeTimeoutFuture.cancel(false);
                this.handshakeTimeoutFuture = null;
            }
            for (ServerTransportFilter filter2 : ServerImpl.this.transportFilters) {
                filter2.transportTerminated(this.attributes);
            }
            ServerImpl.this.transportClosed(this.transport);
        }

        @Override
        public void streamCreated(ServerStream stream, String methodName, Metadata headers) {
            Tag tag = PerfMark.createTag(methodName, stream.streamId());
            try (TaskCloseable ignore = PerfMark.traceTask("ServerTransportListener.streamCreated");){
                PerfMark.attachTag(tag);
                this.streamCreatedInternal(stream, methodName, headers, tag);
            }
        }

        private void streamCreatedInternal(final ServerStream stream, final String methodName, final Metadata headers, final Tag tag) {
            Executor wrappedExecutor;
            if (ServerImpl.this.executorSupplier != null || ServerImpl.this.executor != MoreExecutors.directExecutor()) {
                wrappedExecutor = new SerializingExecutor(ServerImpl.this.executor);
            } else {
                wrappedExecutor = new SerializeReentrantCallsDirectExecutor();
                stream.optimizeForDirectExecutor();
            }
            if (headers.containsKey(GrpcUtil.MESSAGE_ENCODING_KEY)) {
                String encoding = headers.get(GrpcUtil.MESSAGE_ENCODING_KEY);
                Decompressor decompressor = ServerImpl.this.decompressorRegistry.lookupDecompressor(encoding);
                if (decompressor == null) {
                    stream.setListener(NOOP_LISTENER);
                    stream.close(Status.UNIMPLEMENTED.withDescription(String.format("Can't find decompressor for %s", encoding)), new Metadata());
                    return;
                }
                stream.setDecompressor(decompressor);
            }
            final StatsTraceContext statsTraceCtx = Preconditions.checkNotNull(stream.statsTraceContext(), "statsTraceCtx not present from stream");
            final Context.CancellableContext context = this.createContext(headers, statsTraceCtx);
            final Link link = PerfMark.linkOut();
            final JumpToApplicationThreadServerStreamListener jumpListener = new JumpToApplicationThreadServerStreamListener(wrappedExecutor, ServerImpl.this.executor, stream, context, tag);
            stream.setListener(jumpListener);
            final SettableFuture future = SettableFuture.create();
            final class MethodLookup
            extends ContextRunnable {
                MethodLookup() {
                    super(cancellableContext);
                }

                @Override
                public void runInContext() {
                    try (TaskCloseable ignore = PerfMark.traceTask("ServerTransportListener$MethodLookup.startCall");){
                        PerfMark.attachTag(tag);
                        PerfMark.linkIn(link);
                        this.runInternal();
                    }
                }

                private void runInternal() {
                    try {
                        ServerMethodDefinition<?, ?> method2 = ServerImpl.this.registry.lookupMethod(methodName);
                        if (method2 == null) {
                            method2 = ServerImpl.this.fallbackRegistry.lookupMethod(methodName, stream.getAuthority());
                        }
                        if (method2 == null) {
                            Status status = Status.UNIMPLEMENTED.withDescription("Method not found: " + methodName);
                            jumpListener.setListener(NOOP_LISTENER);
                            stream.close(status, new Metadata());
                            context.cancel(null);
                            future.cancel(false);
                            return;
                        }
                        ServerMethodDefinition wrapMethod = ServerTransportListenerImpl.this.wrapMethod(stream, method2, statsTraceCtx);
                        ServerCallParameters callParams = this.maySwitchExecutor(wrapMethod, stream, headers, context, tag);
                        future.set(callParams);
                    }
                    catch (Throwable t2) {
                        jumpListener.setListener(NOOP_LISTENER);
                        stream.close(Status.fromThrowable(t2), new Metadata());
                        context.cancel(null);
                        future.cancel(false);
                        throw t2;
                    }
                }

                private <ReqT, RespT> ServerCallParameters<ReqT, RespT> maySwitchExecutor(ServerMethodDefinition<ReqT, RespT> methodDef, ServerStream stream2, Metadata headers2, Context.CancellableContext context2, Tag tag2) {
                    Executor switchingExecutor;
                    ServerCallImpl<ReqT, RespT> call = new ServerCallImpl<ReqT, RespT>(stream2, methodDef.getMethodDescriptor(), headers2, context2, ServerImpl.this.decompressorRegistry, ServerImpl.this.compressorRegistry, ServerImpl.this.serverCallTracer, tag2);
                    if (ServerImpl.this.executorSupplier != null && (switchingExecutor = ServerImpl.this.executorSupplier.getExecutor(call, headers2)) != null) {
                        ((SerializingExecutor)wrappedExecutor).setExecutor(switchingExecutor);
                    }
                    return new ServerCallParameters<ReqT, RespT>(call, methodDef.getServerCallHandler());
                }
            }
            wrappedExecutor.execute(new MethodLookup());
            final class HandleServerCall
            extends ContextRunnable {
                HandleServerCall() {
                    super(cancellableContext);
                }

                @Override
                public void runInContext() {
                    try (TaskCloseable ignore = PerfMark.traceTask("ServerTransportListener$HandleServerCall.startCall");){
                        PerfMark.linkIn(link);
                        PerfMark.attachTag(tag);
                        this.runInternal();
                    }
                }

                private void runInternal() {
                    ServerStreamListener listener = NOOP_LISTENER;
                    if (future.isCancelled()) {
                        return;
                    }
                    try {
                        listener = ServerTransportListenerImpl.this.startWrappedCall(methodName, (ServerCallParameters)Futures.getDone(future), headers);
                    }
                    catch (Throwable ex) {
                        stream.close(Status.fromThrowable(ex), new Metadata());
                        context.cancel(null);
                        throw new IllegalStateException(ex);
                    }
                    finally {
                        jumpListener.setListener(listener);
                    }
                    final class ServerStreamCancellationListener
                    implements Context.CancellationListener {
                        ServerStreamCancellationListener() {
                        }

                        @Override
                        public void cancelled(Context context) {
                            Status status = Contexts.statusFromCancelled(context);
                            if (Status.DEADLINE_EXCEEDED.getCode().equals((Object)status.getCode())) {
                                stream.cancel(status);
                            }
                        }
                    }
                    context.addListener(new ServerStreamCancellationListener(), MoreExecutors.directExecutor());
                }
            }
            wrappedExecutor.execute(new HandleServerCall());
        }

        private Context.CancellableContext createContext(Metadata headers, StatsTraceContext statsTraceCtx) {
            Long timeoutNanos = headers.get(GrpcUtil.TIMEOUT_KEY);
            Context baseContext = statsTraceCtx.serverFilterContext(ServerImpl.this.rootContext).withValue(InternalServer.SERVER_CONTEXT_KEY, ServerImpl.this);
            if (timeoutNanos == null) {
                return baseContext.withCancellation();
            }
            Context.CancellableContext context = baseContext.withDeadline(Deadline.after(timeoutNanos, TimeUnit.NANOSECONDS, ServerImpl.this.ticker), this.transport.getScheduledExecutorService());
            return context;
        }

        private <ReqT, RespT> ServerMethodDefinition<?, ?> wrapMethod(ServerStream stream, ServerMethodDefinition<ReqT, RespT> methodDef, StatsTraceContext statsTraceCtx) {
            statsTraceCtx.serverCallStarted(new ServerCallInfoImpl<ReqT, RespT>(methodDef.getMethodDescriptor(), stream.getAttributes(), stream.getAuthority()));
            ServerCallHandler<ReqT, RespT> handler = methodDef.getServerCallHandler();
            for (ServerInterceptor interceptor : ServerImpl.this.interceptors) {
                handler = InternalServerInterceptors.interceptCallHandlerCreate(interceptor, handler);
            }
            ServerMethodDefinition<ReqT, RespT> interceptedDef = methodDef.withServerCallHandler(handler);
            ServerMethodDefinition<ReqT, RespT> wMethodDef = ServerImpl.this.binlog == null ? interceptedDef : ServerImpl.this.binlog.wrapMethodDefinition(interceptedDef);
            return wMethodDef;
        }

        private <WReqT, WRespT> ServerStreamListener startWrappedCall(String fullMethodName, ServerCallParameters<WReqT, WRespT> params, Metadata headers) {
            ServerCall.Listener callListener = params.callHandler.startCall(params.call, headers);
            if (callListener == null) {
                throw new NullPointerException("startCall() returned a null listener for method " + fullMethodName);
            }
            return params.call.newServerStreamListener(callListener);
        }

        private final class ServerCallParameters<ReqT, RespT> {
            ServerCallImpl<ReqT, RespT> call;
            ServerCallHandler<ReqT, RespT> callHandler;

            public ServerCallParameters(ServerCallImpl<ReqT, RespT> call, ServerCallHandler<ReqT, RespT> callHandler) {
                this.call = call;
                this.callHandler = callHandler;
            }
        }
    }

    private final class ServerListenerImpl
    implements ServerListener {
        private ServerListenerImpl() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ServerTransportListener transportCreated(ServerTransport transport) {
            Object object = ServerImpl.this.lock;
            synchronized (object) {
                ServerImpl.this.transports.add(transport);
            }
            ServerTransportListenerImpl stli = new ServerTransportListenerImpl(transport);
            stli.init();
            return stli;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void serverShutdown() {
            Status shutdownNowStatusCopy;
            ArrayList copiedTransports;
            Object object = ServerImpl.this.lock;
            synchronized (object) {
                if (ServerImpl.this.serverShutdownCallbackInvoked) {
                    return;
                }
                copiedTransports = new ArrayList(ServerImpl.this.transports);
                shutdownNowStatusCopy = ServerImpl.this.shutdownNowStatus;
                ServerImpl.this.serverShutdownCallbackInvoked = true;
            }
            for (ServerTransport transport : copiedTransports) {
                if (shutdownNowStatusCopy == null) {
                    transport.shutdown();
                    continue;
                }
                transport.shutdownNow(shutdownNowStatusCopy);
            }
            object = ServerImpl.this.lock;
            synchronized (object) {
                ServerImpl.this.transportServersTerminated = true;
                ServerImpl.this.checkForTermination();
            }
        }
    }
}

