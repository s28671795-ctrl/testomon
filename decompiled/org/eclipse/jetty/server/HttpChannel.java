/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.QuietException;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannelState;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.HttpTransport;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.RequestLogCollection;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.HostPort;
import org.eclipse.jetty.util.SharedBlockingCallback;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.thread.Invocable;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpChannel
implements Runnable,
HttpOutput.Interceptor {
    public static Listener NOOP_LISTENER = new Listener(){};
    private static final Logger LOG = LoggerFactory.getLogger(HttpChannel.class);
    private final AtomicLong _requests = new AtomicLong();
    private final Connector _connector;
    private final Executor _executor;
    private final HttpConfiguration _configuration;
    private final EndPoint _endPoint;
    private final HttpTransport _transport;
    private final HttpChannelState _state;
    private final Request _request;
    private final Response _response;
    private final Listener _combinedListener;
    private final Dispatchable _requestDispatcher;
    private final Dispatchable _asyncDispatcher;
    @Deprecated
    private final List<Listener> _transientListeners = new ArrayList<Listener>();
    private MetaData.Response _committedMetaData;
    private RequestLog _requestLog;
    private long _oldIdleTimeout;
    private long _written;

    public HttpChannel(Connector connector, HttpConfiguration configuration, EndPoint endPoint, HttpTransport transport) {
        this._connector = connector;
        this._configuration = Objects.requireNonNull(configuration);
        this._endPoint = endPoint;
        this._transport = transport;
        this._state = new HttpChannelState(this);
        this._request = new Request(this, this.newHttpInput(this._state));
        this._response = new Response(this, this.newHttpOutput());
        this._executor = connector.getServer().getThreadPool();
        this._requestLog = connector.getServer().getRequestLog();
        this._combinedListener = connector instanceof AbstractConnector ? ((AbstractConnector)connector).getHttpChannelListeners() : NOOP_LISTENER;
        this._requestDispatcher = new RequestDispatchable();
        this._asyncDispatcher = new AsyncDispatchable();
        if (LOG.isDebugEnabled()) {
            LOG.debug("new {} -> {},{},{}", this, this._endPoint, this._endPoint == null ? null : this._endPoint.getConnection(), this._state);
        }
    }

    public boolean isSendError() {
        return this._state.isSendError();
    }

    protected String formatAddrOrHost(String addr) {
        return HostPort.normalizeHost(addr);
    }

    private HttpInput newHttpInput(HttpChannelState state) {
        return new HttpInput(state);
    }

    public abstract boolean needContent();

    public abstract HttpInput.Content produceContent();

    public abstract boolean failAllContent(Throwable var1);

    public abstract boolean failed(Throwable var1);

    protected abstract boolean eof();

    protected HttpOutput newHttpOutput() {
        return new HttpOutput(this);
    }

    public HttpChannelState getState() {
        return this._state;
    }

    @Deprecated
    public boolean addListener(Listener listener) {
        return this._transientListeners.add(listener);
    }

    @Deprecated
    public boolean removeListener(Listener listener) {
        return this._transientListeners.remove(listener);
    }

    @Deprecated
    public List<Listener> getTransientListeners() {
        return this._transientListeners;
    }

    public long getBytesWritten() {
        return this._written;
    }

    public long getRequests() {
        return this._requests.get();
    }

    public Connector getConnector() {
        return this._connector;
    }

    public HttpTransport getHttpTransport() {
        return this._transport;
    }

    public RequestLog getRequestLog() {
        return this._requestLog;
    }

    public void setRequestLog(RequestLog requestLog) {
        this._requestLog = requestLog;
    }

    public void addRequestLog(RequestLog requestLog) {
        if (this._requestLog == null) {
            this._requestLog = requestLog;
        } else if (this._requestLog instanceof RequestLogCollection) {
            ((RequestLogCollection)this._requestLog).add(requestLog);
        } else {
            this._requestLog = new RequestLogCollection(this._requestLog, requestLog);
        }
    }

    public MetaData.Response getCommittedMetaData() {
        return this._committedMetaData;
    }

    public long getIdleTimeout() {
        return this._endPoint.getIdleTimeout();
    }

    public void setIdleTimeout(long timeoutMs) {
        this._endPoint.setIdleTimeout(timeoutMs);
    }

    public ByteBufferPool getByteBufferPool() {
        return this._connector.getByteBufferPool();
    }

    public HttpConfiguration getHttpConfiguration() {
        return this._configuration;
    }

    public Server getServer() {
        return this._connector.getServer();
    }

    public Request getRequest() {
        return this._request;
    }

    public Response getResponse() {
        return this._response;
    }

    public Connection getConnection() {
        return this._endPoint.getConnection();
    }

    public EndPoint getEndPoint() {
        return this._endPoint;
    }

    public String getLocalName() {
        SocketAddress localAddress;
        HttpConfiguration httpConfiguration = this.getHttpConfiguration();
        if (httpConfiguration != null && (localAddress = httpConfiguration.getLocalAddress()) instanceof InetSocketAddress) {
            return ((InetSocketAddress)localAddress).getHostName();
        }
        InetSocketAddress local = this.getLocalAddress();
        if (local != null) {
            return local.getHostString();
        }
        return null;
    }

    public int getLocalPort() {
        SocketAddress localAddress;
        HttpConfiguration httpConfiguration = this.getHttpConfiguration();
        if (httpConfiguration != null && (localAddress = httpConfiguration.getLocalAddress()) instanceof InetSocketAddress) {
            return ((InetSocketAddress)localAddress).getPort();
        }
        InetSocketAddress local = this.getLocalAddress();
        return local == null ? 0 : local.getPort();
    }

    public InetSocketAddress getLocalAddress() {
        SocketAddress localAddress;
        HttpConfiguration httpConfiguration = this.getHttpConfiguration();
        if (httpConfiguration != null && (localAddress = httpConfiguration.getLocalAddress()) instanceof InetSocketAddress) {
            return (InetSocketAddress)localAddress;
        }
        SocketAddress local = this._endPoint.getLocalSocketAddress();
        if (local instanceof InetSocketAddress) {
            return (InetSocketAddress)local;
        }
        return null;
    }

    public InetSocketAddress getRemoteAddress() {
        SocketAddress remote = this._endPoint.getRemoteSocketAddress();
        if (remote instanceof InetSocketAddress) {
            return (InetSocketAddress)remote;
        }
        return null;
    }

    public HostPort getServerAuthority() {
        HttpConfiguration httpConfiguration = this.getHttpConfiguration();
        if (httpConfiguration != null) {
            return httpConfiguration.getServerAuthority();
        }
        return null;
    }

    public void continue100(int available) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void recycle() {
        this._request.recycle();
        this._response.recycle();
        this._committedMetaData = null;
        this._requestLog = this._connector == null ? null : this._connector.getServer().getRequestLog();
        this._written = 0L;
        this._oldIdleTimeout = 0L;
        this._transientListeners.clear();
    }

    @Override
    public void run() {
        this.handle();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean handle() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("handle {} {} ", (Object)this._request.getHttpURI(), (Object)this);
        }
        HttpChannelState.Action action = this._state.handling();
        block22: while (!this.getServer().isStopped()) {
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("action {} {}", (Object)action, (Object)this);
                }
                switch (action) {
                    case TERMINATED: {
                        this.onCompleted();
                        break block22;
                    }
                    case WAIT: {
                        break block22;
                    }
                    case DISPATCH: {
                        if (!this._request.hasMetaData()) {
                            throw new IllegalStateException("state=" + this._state);
                        }
                        this.dispatch(DispatcherType.REQUEST, this._requestDispatcher);
                        break;
                    }
                    case ASYNC_DISPATCH: {
                        this.dispatch(DispatcherType.ASYNC, this._asyncDispatcher);
                        break;
                    }
                    case ASYNC_TIMEOUT: {
                        this._state.onTimeout();
                        break;
                    }
                    case SEND_ERROR: {
                        try {
                            this._response.resetContent();
                            Integer code = (Integer)this._request.getAttribute("jakarta.servlet.error.status_code");
                            if (code == null) {
                                code = 500;
                            }
                            this._response.setStatus(code);
                            this.ensureConsumeAllOrNotPersistent();
                            ContextHandler.Context context = (ContextHandler.Context)this._request.getAttribute("org.eclipse.jetty.server.error_context");
                            ErrorHandler errorHandler = ErrorHandler.getErrorHandler(this.getServer(), context == null ? null : context.getContextHandler());
                            if (HttpStatus.hasNoBody(this._response.getStatus()) || errorHandler == null || !errorHandler.errorPageForMethod(this._request.getMethod())) {
                                this.sendResponseAndComplete();
                            } else {
                                this.dispatch(DispatcherType.ERROR, new ErrorDispatchable(errorHandler));
                            }
                            break;
                        }
                        catch (Throwable x) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Could not perform ERROR dispatch, aborting", x);
                            }
                            if (this._state.isResponseCommitted()) {
                                this.abort(x);
                            } else {
                                try {
                                    this._response.resetContent();
                                    this.sendResponseAndComplete();
                                }
                                catch (Throwable t2) {
                                    if (x != t2) {
                                        x.addSuppressed(t2);
                                    }
                                    this.abort(x);
                                }
                            }
                            break;
                        }
                        finally {
                            this._request.removeAttribute("org.eclipse.jetty.server.error_context");
                        }
                    }
                    case ASYNC_ERROR: {
                        throw this._state.getAsyncContextEvent().getThrowable();
                    }
                    case READ_CALLBACK: {
                        ContextHandler handler = this._state.getContextHandler();
                        if (handler != null) {
                            handler.handle(this._request, this._request.getHttpInput());
                            break;
                        }
                        this._request.getHttpInput().run();
                        break;
                    }
                    case WRITE_CALLBACK: {
                        ContextHandler handler = this._state.getContextHandler();
                        if (handler != null) {
                            handler.handle(this._request, this._response.getHttpOutput());
                            break;
                        }
                        this._response.getHttpOutput().run();
                        break;
                    }
                    case COMPLETE: {
                        if (!this._response.isCommitted()) {
                            if (!this._request.isHandled() && !this._response.getHttpOutput().isClosed()) {
                                this._response.sendError(404);
                                break;
                            }
                            if (this._response.getStatus() >= 200) {
                                this.ensureConsumeAllOrNotPersistent();
                            }
                        }
                        if (!this._request.isHead() && this._response.getStatus() != 304 && !this._response.isContentComplete(this._response.getHttpOutput().getWritten()) && this.sendErrorOrAbort("Insufficient content written") || this.checkAndPrepareUpgrade()) break;
                        this._response.completeOutput(Callback.from(Invocable.InvocationType.NON_BLOCKING, () -> this._state.completed(null), this._state::completed));
                        break;
                    }
                    default: {
                        throw new IllegalStateException(this.toString());
                    }
                }
            }
            catch (Throwable failure) {
                if ("org.eclipse.jetty.continuation.ContinuationThrowable".equals(failure.getClass().getName())) {
                    LOG.trace("IGNORED", failure);
                }
                this.handleException(failure);
            }
            action = this._state.unhandle();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("!handle {} {}", (Object)action, (Object)this);
        }
        boolean suspended = action == HttpChannelState.Action.WAIT;
        return !suspended;
    }

    public void ensureConsumeAllOrNotPersistent() {
        switch (this._request.getHttpVersion()) {
            case HTTP_1_0: {
                if (this._request.getHttpInput().consumeAll()) {
                    return;
                }
                this._response.getHttpFields().computeField(HttpHeader.CONNECTION, (h2, fields) -> {
                    if (fields == null || fields.isEmpty()) {
                        return null;
                    }
                    String v = fields.stream().flatMap(field2 -> Stream.of(field2.getValues()).filter(s2 -> !HttpHeaderValue.KEEP_ALIVE.is((String)s2))).collect(Collectors.joining(", "));
                    if (StringUtil.isEmpty(v)) {
                        return null;
                    }
                    return new HttpField(HttpHeader.CONNECTION, v);
                });
                break;
            }
            case HTTP_1_1: {
                if (this._request.getHttpInput().consumeAll()) {
                    return;
                }
                this._response.getHttpFields().computeField(HttpHeader.CONNECTION, (h2, fields) -> {
                    if (fields == null || fields.isEmpty()) {
                        return HttpConnection.CONNECTION_CLOSE;
                    }
                    if (fields.stream().anyMatch(f -> f.contains(HttpHeaderValue.CLOSE.asString()))) {
                        HttpField f2;
                        if (fields.size() == 1 && HttpConnection.CONNECTION_CLOSE.equals(f2 = (HttpField)fields.get(0))) {
                            return f2;
                        }
                        return new HttpField(HttpHeader.CONNECTION, fields.stream().flatMap(field2 -> Stream.of(field2.getValues()).filter(s2 -> !HttpHeaderValue.KEEP_ALIVE.is((String)s2))).collect(Collectors.joining(", ")));
                    }
                    return new HttpField(HttpHeader.CONNECTION, Stream.concat(fields.stream().flatMap(field2 -> Stream.of(field2.getValues()).filter(s2 -> !HttpHeaderValue.KEEP_ALIVE.is((String)s2))), Stream.of(HttpHeaderValue.CLOSE.asString())).collect(Collectors.joining(", ")));
                });
                break;
            }
        }
    }

    public boolean sendErrorOrAbort(String message) {
        try {
            if (this.isCommitted()) {
                this.abort(new IOException(message));
                return false;
            }
            this._response.sendError(500, message);
            return true;
        }
        catch (Throwable x) {
            LOG.trace("IGNORED", x);
            this.abort(x);
            return false;
        }
    }

    private void dispatch(DispatcherType type, Dispatchable dispatchable) throws IOException, ServletException {
        try {
            this._request.setHandled(false);
            this._response.reopen();
            this._request.setDispatcherType(type);
            this._combinedListener.onBeforeDispatch(this._request);
            dispatchable.dispatch();
        }
        catch (Throwable x) {
            this._combinedListener.onDispatchFailure(this._request, x);
            throw x;
        }
        finally {
            this._combinedListener.onAfterDispatch(this._request);
            this._request.setDispatcherType(null);
        }
    }

    protected void handleException(Throwable failure) {
        Throwable quiet = this.unwrap(failure, QuietException.class);
        Throwable noStack = this.unwrap(failure, BadMessageException.class, IOException.class, TimeoutException.class);
        if (quiet != null || !this.getServer().isRunning()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(this._request.getRequestURI(), failure);
            }
        } else if (noStack != null) {
            if (LOG.isDebugEnabled()) {
                LOG.warn("handleException {}", (Object)this._request.getRequestURI(), (Object)failure);
            } else {
                LOG.warn("handleException {} {}", (Object)this._request.getRequestURI(), (Object)noStack.toString());
            }
        } else {
            LOG.warn(this._request.getRequestURI(), failure);
        }
        if (this.isCommitted()) {
            this.abort(failure);
        } else {
            try {
                this._state.onError(failure);
            }
            catch (IllegalStateException e) {
                this.abort(failure);
            }
        }
    }

    protected Throwable unwrap(Throwable failure, Class<?> ... targets) {
        while (failure != null) {
            for (Class<?> x : targets) {
                if (!x.isInstance(failure)) continue;
                return failure;
            }
            failure = failure.getCause();
        }
        return null;
    }

    public void sendResponseAndComplete() {
        try {
            this._request.setHandled(true);
            this._state.completing();
            this.sendResponse(null, this._response.getHttpOutput().getBuffer(), true, Callback.from(() -> this._state.completed(null), this._state::completed));
        }
        catch (Throwable x) {
            this.abort(x);
        }
    }

    public boolean isExpecting100Continue() {
        return false;
    }

    public boolean isExpecting102Processing() {
        return false;
    }

    public String toString() {
        long timeStamp = this._request.getTimeStamp();
        return String.format("%s@%x{s=%s,r=%s,c=%b/%b,a=%s,uri=%s,age=%d}", new Object[]{this.getClass().getSimpleName(), this.hashCode(), this._state, this._requests, this.isRequestCompleted(), this.isResponseCompleted(), this._state.getState(), this._request.getHttpURI(), timeStamp == 0L ? 0L : System.currentTimeMillis() - timeStamp});
    }

    public void onRequest(MetaData.Request request) {
        this._requests.incrementAndGet();
        this._request.setTimeStamp(System.currentTimeMillis());
        HttpFields.Mutable fields = this._response.getHttpFields();
        if (this._configuration.getSendDateHeader() && !fields.contains(HttpHeader.DATE)) {
            fields.put(this._connector.getServer().getDateField());
        }
        long idleTO = this._configuration.getIdleTimeout();
        this._oldIdleTimeout = this.getIdleTimeout();
        if (idleTO >= 0L && this._oldIdleTimeout != idleTO) {
            this.setIdleTimeout(idleTO);
        }
        this._request.setMetaData(request);
        this._combinedListener.onRequestBegin(this._request);
        if (LOG.isDebugEnabled()) {
            LOG.debug("REQUEST for {} on {}{}{} {} {}{}{}", new Object[]{request.getURIString(), this, System.lineSeparator(), request.getMethod(), request.getURIString(), request.getHttpVersion(), System.lineSeparator(), request.getFields()});
        }
    }

    public boolean onContent(HttpInput.Content content) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onContent {} {}", (Object)this, (Object)content);
        }
        this._combinedListener.onRequestContent(this._request, content.getByteBuffer());
        return false;
    }

    public boolean onContentComplete() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onContentComplete {}", (Object)this);
        }
        this._combinedListener.onRequestContentEnd(this._request);
        return false;
    }

    public void onTrailers(HttpFields trailers) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onTrailers {} {}", (Object)this, (Object)trailers);
        }
        this._request.setTrailerHttpFields(trailers);
        this._combinedListener.onRequestTrailers(this._request);
    }

    public boolean onRequestComplete() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onRequestComplete {}", (Object)this);
        }
        boolean result = this.eof();
        this._combinedListener.onRequestEnd(this._request);
        return result;
    }

    protected boolean checkAndPrepareUpgrade() {
        return false;
    }

    public void onCompleted() {
        long idleTO;
        if (LOG.isDebugEnabled()) {
            LOG.debug("onCompleted for {} written={}", (Object)this.getRequest().getRequestURI(), (Object)this.getBytesWritten());
        }
        if ((idleTO = this._configuration.getIdleTimeout()) >= 0L && this.getIdleTimeout() != this._oldIdleTimeout) {
            this.setIdleTimeout(this._oldIdleTimeout);
        }
        this._request.onCompleted();
        this._combinedListener.onComplete(this._request);
        this._transport.onCompleted();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onBadMessage(BadMessageException failure) {
        HttpChannelState.Action action;
        int status = failure.getCode();
        String reason = failure.getReason();
        if (status < 400 || status > 599) {
            failure = new BadMessageException(400, reason, failure);
        }
        this._combinedListener.onRequestFailure(this._request, failure);
        try {
            action = this._state.handling();
        }
        catch (Throwable e) {
            this.abort(e);
            throw failure;
        }
        try {
            if (action == HttpChannelState.Action.DISPATCH) {
                ByteBuffer content = null;
                HttpFields.Mutable fields = HttpFields.build();
                ErrorHandler handler = this.getServer().getBean(ErrorHandler.class);
                if (handler != null) {
                    content = handler.badMessageError(status, reason, fields);
                }
                this.sendResponse(new MetaData.Response(HttpVersion.HTTP_1_1, status, null, fields, BufferUtil.length(content)), content, true);
            }
        }
        catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unable to send bad message response", e);
            }
        }
        finally {
            try {
                this.onCompleted();
            }
            catch (Throwable e) {
                LOG.debug("Unable to complete bad message", e);
                this.abort(e);
            }
        }
    }

    protected boolean sendResponse(MetaData.Response response, ByteBuffer content, boolean complete, Callback callback) {
        boolean committing = this._state.commitResponse();
        if (LOG.isDebugEnabled()) {
            LOG.debug("sendResponse info={} content={} complete={} committing={} callback={}", response, BufferUtil.toDetailString(content), complete, committing, callback);
        }
        if (committing) {
            this._combinedListener.onResponseBegin(this._request);
            if (response == null) {
                response = this._response.newResponseMetaData();
            }
            this.commit(response);
            this._request.onResponseCommit();
            int status = response.getStatus();
            SendCallback committed = HttpStatus.isInformational(status) ? new Send1XXCallback(callback) : new SendCallback(callback, content, true, complete);
            this._transport.send(this._request.getMetaData(), response, content, complete, committed);
        } else if (response == null) {
            this._transport.send(this._request.getMetaData(), null, content, complete, new SendCallback(callback, content, false, complete));
        } else {
            callback.failed(new IllegalStateException("committed"));
        }
        return committing;
    }

    public boolean sendResponse(MetaData.Response info, ByteBuffer content, boolean complete) throws IOException {
        SharedBlockingCallback.Blocker blocker = this._response.getHttpOutput().acquireWriteBlockingCallback();
        try {
            boolean committing = this.sendResponse(info, content, complete, blocker);
            blocker.block();
            boolean bl = committing;
            if (blocker != null) {
                blocker.close();
            }
            return bl;
        }
        catch (Throwable throwable) {
            try {
                if (blocker != null) {
                    try {
                        blocker.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            catch (Throwable failure) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unable to send response", failure);
                }
                this.abort(failure);
                throw failure;
            }
        }
    }

    protected void commit(MetaData.Response info) {
        this._committedMetaData = info;
        if (LOG.isDebugEnabled()) {
            LOG.debug("COMMIT for {} on {}{}{} {} {}{}{}", new Object[]{this.getRequest().getRequestURI(), this, System.lineSeparator(), info.getStatus(), info.getReason(), info.getHttpVersion(), System.lineSeparator(), info.getFields()});
        }
    }

    public boolean isCommitted() {
        return this._state.isResponseCommitted();
    }

    public boolean isRequestCompleted() {
        return this._state.isCompleted();
    }

    public boolean isResponseCompleted() {
        return this._state.isResponseCompleted();
    }

    public boolean isPersistent() {
        return this._endPoint.isOpen();
    }

    @Override
    public void write(ByteBuffer content, boolean complete, Callback callback) {
        this.sendResponse(null, content, complete, callback);
    }

    @Override
    public void resetBuffer() {
        if (this.isCommitted()) {
            throw new IllegalStateException("Committed");
        }
    }

    @Override
    public HttpOutput.Interceptor getNextInterceptor() {
        return null;
    }

    protected void execute(Runnable task) {
        this._executor.execute(task);
    }

    public Scheduler getScheduler() {
        return this._connector.getScheduler();
    }

    public boolean isUseInputDirectByteBuffers() {
        return this.getHttpConfiguration().isUseInputDirectByteBuffers();
    }

    public boolean isUseOutputDirectByteBuffers() {
        return this.getHttpConfiguration().isUseOutputDirectByteBuffers();
    }

    public void abort(Throwable failure) {
        if (this._state.abortResponse()) {
            this._combinedListener.onResponseFailure(this._request, failure);
            this._transport.abort(failure);
        }
    }

    public boolean isTunnellingSupported() {
        return false;
    }

    public EndPoint getTunnellingEndPoint() {
        throw new UnsupportedOperationException("Tunnelling not supported");
    }

    private void notifyEvent1(Function<Listener, Consumer<Request>> function, Request request) {
        for (Listener listener : this._transientListeners) {
            try {
                function.apply(listener).accept(request);
            }
            catch (Throwable x) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Failure invoking listener {}", (Object)listener, (Object)x);
            }
        }
    }

    private void notifyEvent2(Function<Listener, BiConsumer<Request, ByteBuffer>> function, Request request, ByteBuffer content) {
        for (Listener listener : this._transientListeners) {
            ByteBuffer view = content.slice();
            try {
                function.apply(listener).accept(request, view);
            }
            catch (Throwable x) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Failure invoking listener {}", (Object)listener, (Object)x);
            }
        }
    }

    private void notifyEvent2(Function<Listener, BiConsumer<Request, Throwable>> function, Request request, Throwable failure) {
        for (Listener listener : this._transientListeners) {
            try {
                function.apply(listener).accept(request, failure);
            }
            catch (Throwable x) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Failure invoking listener {}", (Object)listener, (Object)x);
            }
        }
    }

    public static interface Listener
    extends EventListener {
        default public void onRequestBegin(Request request) {
        }

        default public void onBeforeDispatch(Request request) {
        }

        default public void onDispatchFailure(Request request, Throwable failure) {
        }

        default public void onAfterDispatch(Request request) {
        }

        default public void onRequestContent(Request request, ByteBuffer content) {
        }

        default public void onRequestContentEnd(Request request) {
        }

        default public void onRequestTrailers(Request request) {
        }

        default public void onRequestEnd(Request request) {
        }

        default public void onRequestFailure(Request request, Throwable failure) {
        }

        default public void onResponseBegin(Request request) {
        }

        default public void onResponseCommit(Request request) {
        }

        default public void onResponseContent(Request request, ByteBuffer content) {
        }

        default public void onResponseEnd(Request request) {
        }

        default public void onResponseFailure(Request request, Throwable failure) {
        }

        default public void onComplete(Request request) {
        }
    }

    private class RequestDispatchable
    implements Dispatchable {
        private RequestDispatchable() {
        }

        @Override
        public void dispatch() throws IOException, ServletException {
            for (HttpConfiguration.Customizer customizer : HttpChannel.this._configuration.getCustomizers()) {
                customizer.customize(HttpChannel.this.getConnector(), HttpChannel.this._configuration, HttpChannel.this._request);
                if (!HttpChannel.this._request.isHandled()) continue;
                return;
            }
            HttpChannel.this.getServer().handle(HttpChannel.this);
        }
    }

    static interface Dispatchable {
        public void dispatch() throws IOException, ServletException;
    }

    private class AsyncDispatchable
    implements Dispatchable {
        private AsyncDispatchable() {
        }

        @Override
        public void dispatch() throws IOException, ServletException {
            HttpChannel.this.getServer().handleAsync(HttpChannel.this);
        }
    }

    private class ErrorDispatchable
    implements Dispatchable {
        private final ErrorHandler _errorHandler;

        public ErrorDispatchable(ErrorHandler errorHandler) {
            this._errorHandler = errorHandler;
        }

        @Override
        public void dispatch() throws IOException, ServletException {
            this._errorHandler.handle(null, HttpChannel.this._request, HttpChannel.this._request, HttpChannel.this._response);
            HttpChannel.this._request.setHandled(true);
        }
    }

    private class Send1XXCallback
    extends SendCallback {
        private Send1XXCallback(Callback callback) {
            super(callback, null, false, false);
        }

        @Override
        public void succeeded() {
            if (HttpChannel.this._state.partialResponse()) {
                super.succeeded();
            } else {
                super.failed(new IllegalStateException());
            }
        }
    }

    private class SendCallback
    extends Callback.Nested {
        private final ByteBuffer _content;
        private final int _length;
        private final boolean _commit;
        private final boolean _complete;

        private SendCallback(Callback callback, ByteBuffer content, boolean commit, boolean complete) {
            super(callback);
            this._content = content == null ? BufferUtil.EMPTY_BUFFER : content.slice();
            this._length = this._content.remaining();
            this._commit = commit;
            this._complete = complete;
        }

        @Override
        public void succeeded() {
            HttpChannel.this._written += (long)this._length;
            if (this._commit) {
                HttpChannel.this._combinedListener.onResponseCommit(HttpChannel.this._request);
            }
            if (this._length > 0) {
                HttpChannel.this._combinedListener.onResponseContent(HttpChannel.this._request, this._content);
            }
            if (this._complete && HttpChannel.this._state.completeResponse()) {
                HttpChannel.this._combinedListener.onResponseEnd(HttpChannel.this._request);
            }
            super.succeeded();
        }

        @Override
        public void failed(final Throwable x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Commit failed", x);
            }
            if (x instanceof BadMessageException) {
                HttpChannel.this._transport.send(HttpChannel.this._request.getMetaData(), HttpGenerator.RESPONSE_500_INFO, null, true, new Callback.Nested(this){

                    @Override
                    public void succeeded() {
                        HttpChannel.this._response.getHttpOutput().completed(null);
                        super.failed(x);
                    }

                    @Override
                    public void failed(Throwable th) {
                        HttpChannel.this.abort(x);
                        super.failed(x);
                    }
                });
            } else {
                HttpChannel.this.abort(x);
                super.failed(x);
            }
        }
    }

    @Deprecated
    public static class TransientListeners
    implements Listener {
        @Override
        public void onRequestBegin(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onRequestBegin, request);
        }

        @Override
        public void onBeforeDispatch(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onBeforeDispatch, request);
        }

        @Override
        public void onDispatchFailure(Request request, Throwable failure) {
            request.getHttpChannel().notifyEvent2(listener -> listener::onDispatchFailure, request, failure);
        }

        @Override
        public void onAfterDispatch(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onAfterDispatch, request);
        }

        @Override
        public void onRequestContent(Request request, ByteBuffer content) {
            request.getHttpChannel().notifyEvent2(listener -> listener::onRequestContent, request, content);
        }

        @Override
        public void onRequestContentEnd(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onRequestContentEnd, request);
        }

        @Override
        public void onRequestTrailers(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onRequestTrailers, request);
        }

        @Override
        public void onRequestEnd(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onRequestEnd, request);
        }

        @Override
        public void onRequestFailure(Request request, Throwable failure) {
            request.getHttpChannel().notifyEvent2(listener -> listener::onRequestFailure, request, failure);
        }

        @Override
        public void onResponseBegin(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onResponseBegin, request);
        }

        @Override
        public void onResponseCommit(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onResponseCommit, request);
        }

        @Override
        public void onResponseContent(Request request, ByteBuffer content) {
            request.getHttpChannel().notifyEvent2(listener -> listener::onResponseContent, request, content);
        }

        @Override
        public void onResponseEnd(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onResponseEnd, request);
        }

        @Override
        public void onResponseFailure(Request request, Throwable failure) {
            request.getHttpChannel().notifyEvent2(listener -> listener::onResponseFailure, request, failure);
        }

        @Override
        public void onComplete(Request request) {
            request.getHttpChannel().notifyEvent1(listener -> listener::onComplete, request);
        }
    }
}

