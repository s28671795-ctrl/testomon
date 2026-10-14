/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritePendingException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.LongAdder;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpCompliance;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.RetainableByteBuffer;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.io.WriteFlusher;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpChannelOverHttp;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.server.HttpTransport;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnection
extends AbstractConnection
implements Runnable,
HttpTransport,
WriteFlusher.Listener,
Connection.UpgradeFrom,
Connection.UpgradeTo {
    private static final Logger LOG = LoggerFactory.getLogger(HttpConnection.class);
    public static final HttpField CONNECTION_CLOSE = new PreEncodedHttpField(HttpHeader.CONNECTION, HttpHeaderValue.CLOSE.asString());
    private static final ThreadLocal<HttpConnection> __currentConnection = new ThreadLocal();
    private final HttpConfiguration _config;
    private final Connector _connector;
    private final ByteBufferPool _bufferPool;
    private final RetainableByteBufferPool _retainableByteBufferPool;
    private final HttpInput _input;
    private final HttpGenerator _generator;
    private final HttpChannelOverHttp _channel;
    private final HttpParser _parser;
    private volatile RetainableByteBuffer _retainableByteBuffer;
    private final AsyncReadCallback _asyncReadCallback = new AsyncReadCallback();
    private final SendCallback _sendCallback = new SendCallback();
    private final boolean _recordHttpComplianceViolations;
    private final LongAdder bytesIn = new LongAdder();
    private final LongAdder bytesOut = new LongAdder();
    private boolean _useInputDirectByteBuffers;
    private boolean _useOutputDirectByteBuffers;

    public static HttpConnection getCurrentConnection() {
        return __currentConnection.get();
    }

    protected static HttpConnection setCurrentConnection(HttpConnection connection) {
        HttpConnection last = __currentConnection.get();
        __currentConnection.set(connection);
        return last;
    }

    public HttpConnection(HttpConfiguration config, Connector connector, EndPoint endPoint, boolean recordComplianceViolations) {
        super(endPoint, connector.getExecutor());
        this._config = config;
        this._connector = connector;
        this._bufferPool = this._connector.getByteBufferPool();
        this._retainableByteBufferPool = this._bufferPool.asRetainableByteBufferPool();
        this._generator = this.newHttpGenerator();
        this._channel = this.newHttpChannel();
        this._input = this._channel.getRequest().getHttpInput();
        this._parser = this.newHttpParser(config.getHttpCompliance());
        this._recordHttpComplianceViolations = recordComplianceViolations;
        if (LOG.isDebugEnabled()) {
            LOG.debug("New HTTP Connection {}", (Object)this);
        }
    }

    public long getBeginNanoTime() {
        return this._parser.getBeginNanoTime();
    }

    public HttpConfiguration getHttpConfiguration() {
        return this._config;
    }

    public boolean isRecordHttpComplianceViolations() {
        return this._recordHttpComplianceViolations;
    }

    protected HttpGenerator newHttpGenerator() {
        return new HttpGenerator(this._config.getSendServerVersion(), this._config.getSendXPoweredBy());
    }

    protected HttpChannelOverHttp newHttpChannel() {
        return new HttpChannelOverHttp(this, this._connector, this._config, this.getEndPoint(), this);
    }

    protected HttpParser newHttpParser(HttpCompliance compliance) {
        HttpParser parser = new HttpParser(this.newRequestHandler(), this.getHttpConfiguration().getRequestHeaderSize(), compliance);
        parser.setHeaderCacheSize(this.getHttpConfiguration().getHeaderCacheSize());
        parser.setHeaderCacheCaseSensitive(this.getHttpConfiguration().isHeaderCacheCaseSensitive());
        return parser;
    }

    protected HttpParser.RequestHandler newRequestHandler() {
        return this._channel;
    }

    public Server getServer() {
        return this._connector.getServer();
    }

    public Connector getConnector() {
        return this._connector;
    }

    public HttpChannel getHttpChannel() {
        return this._channel;
    }

    public HttpParser getParser() {
        return this._parser;
    }

    public HttpGenerator getGenerator() {
        return this._generator;
    }

    @Override
    public long getMessagesIn() {
        return this.getHttpChannel().getRequests();
    }

    @Override
    public long getMessagesOut() {
        return this.getHttpChannel().getRequests();
    }

    public boolean isUseInputDirectByteBuffers() {
        return this._useInputDirectByteBuffers;
    }

    public void setUseInputDirectByteBuffers(boolean useInputDirectByteBuffers) {
        this._useInputDirectByteBuffers = useInputDirectByteBuffers;
    }

    public boolean isUseOutputDirectByteBuffers() {
        return this._useOutputDirectByteBuffers;
    }

    public void setUseOutputDirectByteBuffers(boolean useOutputDirectByteBuffers) {
        this._useOutputDirectByteBuffers = useOutputDirectByteBuffers;
    }

    @Override
    public ByteBuffer onUpgradeFrom() {
        if (!this.isRequestBufferEmpty()) {
            ByteBuffer unconsumed = ByteBuffer.allocateDirect(this._retainableByteBuffer.remaining());
            unconsumed.put(this._retainableByteBuffer.getBuffer());
            unconsumed.flip();
            this.releaseRequestBuffer();
            return unconsumed;
        }
        return null;
    }

    @Override
    public void onUpgradeTo(ByteBuffer buffer) {
        BufferUtil.append(this.getRequestBuffer(), buffer);
    }

    @Override
    public void onFlushed(long bytes) throws IOException {
        this._channel.getResponse().getHttpOutput().onFlushed(bytes);
    }

    void releaseRequestBuffer() {
        if (this._retainableByteBuffer != null && !this._retainableByteBuffer.hasRemaining()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("releaseRequestBuffer {}", (Object)this);
            }
            if (this._retainableByteBuffer.release()) {
                this._retainableByteBuffer = null;
            } else {
                throw new IllegalStateException("unreleased buffer " + this._retainableByteBuffer);
            }
        }
    }

    private ByteBuffer getRequestBuffer() {
        if (this._retainableByteBuffer == null) {
            this._retainableByteBuffer = this._retainableByteBufferPool.acquire(this.getInputBufferSize(), this.isUseInputDirectByteBuffers());
        }
        return this._retainableByteBuffer.getBuffer();
    }

    public boolean isRequestBufferEmpty() {
        return this._retainableByteBuffer == null || this._retainableByteBuffer.isEmpty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onFillable() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} onFillable enter {} {}", this, this._channel.getState(), this._retainableByteBuffer);
        }
        HttpConnection last = HttpConnection.setCurrentConnection(this);
        try {
            while (this.getEndPoint().isOpen()) {
                int filled = this.fillRequestBuffer();
                if (filled < 0 && this.getEndPoint().isOutputShutdown()) {
                    this.close();
                }
                boolean handle2 = this.parseRequestBuffer();
                if (this.getEndPoint().getConnection() != this) break;
                if (handle2) {
                    boolean suspended;
                    boolean bl = suspended = !this._channel.handle();
                    if (!suspended && this.getEndPoint().getConnection() == this) continue;
                    break;
                }
                if (filled == 0) {
                    this.fillInterested();
                    break;
                }
                if (filled >= 0) continue;
                if (!this._channel.getState().isIdle()) break;
                this.getEndPoint().shutdownOutput();
                break;
            }
        }
        catch (Throwable x) {
            try {
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("{} caught exception {}", this, this._channel.getState(), x);
                    }
                    if (this._retainableByteBuffer != null) {
                        this._retainableByteBuffer.clear();
                        this.releaseRequestBuffer();
                    }
                }
                finally {
                    this.getEndPoint().close(x);
                }
            }
            catch (Throwable throwable) {
                HttpConnection.setCurrentConnection(last);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} onFillable exit {} {}", this, this._channel.getState(), this._retainableByteBuffer);
                }
                throw throwable;
            }
            HttpConnection.setCurrentConnection(last);
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} onFillable exit {} {}", this, this._channel.getState(), this._retainableByteBuffer);
            }
        }
        HttpConnection.setCurrentConnection(last);
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} onFillable exit {} {}", this, this._channel.getState(), this._retainableByteBuffer);
        }
    }

    void parseAndFillForContent() {
        if (this._parser.isTerminated()) {
            throw new IllegalStateException("Parser is terminated: " + this._parser);
        }
        while (!(!this._parser.inContentState() || this.parseRequestBuffer() || this._parser.inContentState() && this.fillRequestBuffer() <= 0)) {
        }
    }

    private int fillRequestBuffer() {
        if (this._retainableByteBuffer != null && this._retainableByteBuffer.isRetained()) {
            throw new IllegalStateException("fill with unconsumed content on " + this);
        }
        if (this.isRequestBufferEmpty()) {
            ByteBuffer requestBuffer = this.getRequestBuffer();
            try {
                int filled = this.getEndPoint().fill(requestBuffer);
                if (filled == 0) {
                    filled = this.getEndPoint().fill(requestBuffer);
                }
                if (filled > 0) {
                    this.bytesIn.add(filled);
                } else if (filled < 0) {
                    this._parser.atEOF();
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} filled {} {}", this, filled, this._retainableByteBuffer);
                }
                return filled;
            }
            catch (IOException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unable to fill from endpoint {}", (Object)this.getEndPoint(), (Object)e);
                }
                this._parser.atEOF();
                return -1;
            }
        }
        return 0;
    }

    private boolean parseRequestBuffer() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} parse {}", (Object)this, (Object)this._retainableByteBuffer);
        }
        boolean handle2 = this._parser.parseNext(this._retainableByteBuffer == null ? BufferUtil.EMPTY_BUFFER : this._retainableByteBuffer.getBuffer());
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} parsed {} {}", this, handle2, this._parser);
        }
        if (this._retainableByteBuffer != null && !this._retainableByteBuffer.isRetained()) {
            this.releaseRequestBuffer();
        }
        return handle2;
    }

    private boolean upgrade() {
        Connection connection = (Connection)this._channel.getRequest().getAttribute(UPGRADE_CONNECTION_ATTRIBUTE);
        if (connection == null) {
            return false;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Upgrade from {} to {}", (Object)this, (Object)connection);
        }
        this._channel.getState().upgrade();
        this.getEndPoint().upgrade(connection);
        this._channel.recycle();
        this._parser.reset();
        this._generator.reset();
        if (this._retainableByteBuffer != null) {
            if (!this._retainableByteBuffer.isRetained()) {
                this.releaseRequestBuffer();
            } else {
                LOG.warn("{} lingering content references?!?!", (Object)this);
                this._retainableByteBuffer = null;
            }
        }
        return true;
    }

    @Override
    public void onCompleted() {
        if (this.isFillInterested()) {
            LOG.warn("Pending read in onCompleted {} {}", (Object)this, (Object)this.getEndPoint());
            this._channel.abort(new IOException("Pending read in onCompleted"));
        } else if (this.upgrade()) {
            return;
        }
        boolean complete = this._input.consumeAll();
        if (this._channel.isExpecting100Continue()) {
            this._parser.close();
        } else if (this._generator.isPersistent() && !complete) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("unconsumed input {} {}", (Object)this, (Object)this._parser);
            }
            this._channel.abort(new IOException("unconsumed input"));
        }
        this._channel.recycle();
        if (!this._parser.isClosed()) {
            if (this._generator.isPersistent()) {
                this._parser.reset();
            } else {
                this._parser.close();
            }
        }
        this._generator.reset();
        if (HttpConnection.getCurrentConnection() != this) {
            if (this._parser.isStart()) {
                if (this.isRequestBufferEmpty()) {
                    this.fillInterested();
                } else if (this.getConnector().isRunning()) {
                    try {
                        this.getExecutor().execute(this);
                    }
                    catch (RejectedExecutionException e) {
                        if (this.getConnector().isRunning()) {
                            LOG.warn("Failed dispatch of {}", (Object)this, (Object)e);
                        } else {
                            LOG.trace("IGNORED", e);
                        }
                        this.getEndPoint().close();
                    }
                } else {
                    this.getEndPoint().close();
                }
            } else if (this.getEndPoint().isOpen()) {
                this.fillInterested();
            }
        }
    }

    @Override
    protected boolean onReadTimeout(Throwable timeout2) {
        return this._channel.onIdleTimeout(timeout2);
    }

    @Override
    protected void onFillInterestedFailed(Throwable cause) {
        this._parser.close();
        super.onFillInterestedFailed(cause);
    }

    @Override
    public void onOpen() {
        super.onOpen();
        if (this.isRequestBufferEmpty()) {
            this.fillInterested();
        } else {
            this.getExecutor().execute(this);
        }
    }

    @Override
    public void onClose(Throwable cause) {
        if (cause == null) {
            this._sendCallback.close();
        } else {
            this._sendCallback.failed(cause);
        }
        super.onClose(cause);
    }

    @Override
    public void run() {
        this.onFillable();
    }

    @Override
    public void send(MetaData.Request request, MetaData.Response response, ByteBuffer content, boolean lastContent, Callback callback) {
        if (response == null) {
            if (!lastContent && BufferUtil.isEmpty(content)) {
                callback.succeeded();
                return;
            }
        } else if (this._channel.isExpecting100Continue()) {
            this._generator.setPersistent(false);
        }
        if (this._sendCallback.reset(request, response, content, lastContent, callback)) {
            this._sendCallback.iterate();
        }
    }

    HttpInput.Content newContent(ByteBuffer c) {
        return new Content(c);
    }

    @Override
    public void abort(Throwable failure) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("abort {} {}", (Object)this, (Object)failure);
        }
        this.getEndPoint().close();
    }

    @Override
    public boolean isPushSupported() {
        return false;
    }

    @Override
    public void push(MetaData.Request request) {
        LOG.debug("ignore push in {}", (Object)this);
    }

    public void asyncReadFillInterested() {
        this.getEndPoint().tryFillInterested(this._asyncReadCallback);
    }

    @Override
    public long getBytesIn() {
        return this.bytesIn.longValue();
    }

    @Override
    public long getBytesOut() {
        return this.bytesOut.longValue();
    }

    @Override
    public String toConnectionString() {
        return String.format("%s@%x[p=%s,g=%s]=>%s", this.getClass().getSimpleName(), this.hashCode(), this._parser, this._generator, this._channel);
    }

    private class AsyncReadCallback
    implements Callback {
        private AsyncReadCallback() {
        }

        @Override
        public void succeeded() {
            if (HttpConnection.this._channel.getRequest().getHttpInput().onContentProducible()) {
                HttpConnection.this._channel.handle();
            }
        }

        @Override
        public void failed(Throwable x) {
            if (HttpConnection.this._channel.failed(x)) {
                HttpConnection.this._channel.handle();
            }
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return HttpConnection.this._channel.getRequest().getHttpInput().isAsync() ? Invocable.InvocationType.BLOCKING : Invocable.InvocationType.NON_BLOCKING;
        }
    }

    private class SendCallback
    extends IteratingCallback {
        private MetaData.Response _info;
        private boolean _head;
        private ByteBuffer _content;
        private boolean _lastContent;
        private Callback _callback;
        private ByteBuffer _header;
        private ByteBuffer _chunk;
        private boolean _shutdownOut;

        private SendCallback() {
            super(true);
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return this._callback.getInvocationType();
        }

        private boolean reset(MetaData.Request request, MetaData.Response info, ByteBuffer content, boolean last, Callback callback) {
            if (this.reset()) {
                this._info = info;
                this._head = request != null && HttpMethod.HEAD.is(request.getMethod());
                this._content = content;
                this._lastContent = last;
                this._callback = callback;
                this._header = null;
                this._shutdownOut = false;
                if (HttpConnection.this.getConnector().isShutdown()) {
                    HttpConnection.this._generator.setPersistent(false);
                }
                return true;
            }
            if (this.isClosed()) {
                callback.failed(new EofException());
            } else {
                callback.failed(new WritePendingException());
            }
            return false;
        }

        @Override
        public IteratingCallback.Action process() throws Exception {
            HttpGenerator.Result result;
            if (this._callback == null) {
                throw new IllegalStateException();
            }
            boolean useDirectByteBuffers = HttpConnection.this.isUseOutputDirectByteBuffers();
            block20: while (true) {
                result = HttpConnection.this._generator.generateResponse(this._info, this._head, this._header, this._chunk, this._content, this._lastContent);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("generate: {} for {} ({},{},{})@{}", new Object[]{result, this, BufferUtil.toSummaryString(this._header), BufferUtil.toSummaryString(this._content), this._lastContent, HttpConnection.this._generator.getState()});
                }
                switch (result) {
                    case NEED_INFO: {
                        throw new EofException("request lifecycle violation");
                    }
                    case NEED_HEADER: {
                        this._header = HttpConnection.this._bufferPool.acquire(Math.min(HttpConnection.this._config.getResponseHeaderSize(), HttpConnection.this._config.getOutputBufferSize()), useDirectByteBuffers);
                        continue block20;
                    }
                    case HEADER_OVERFLOW: {
                        if (this._header.capacity() >= HttpConnection.this._config.getResponseHeaderSize()) {
                            throw new BadMessageException(500, "Response header too large");
                        }
                        this.releaseHeader();
                        this._header = HttpConnection.this._bufferPool.acquire(HttpConnection.this._config.getResponseHeaderSize(), useDirectByteBuffers);
                        continue block20;
                    }
                    case NEED_CHUNK: {
                        this._chunk = HttpConnection.this._bufferPool.acquire(12, useDirectByteBuffers);
                        continue block20;
                    }
                    case NEED_CHUNK_TRAILER: {
                        this.releaseChunk();
                        this._chunk = HttpConnection.this._bufferPool.acquire(HttpConnection.this._config.getResponseHeaderSize(), useDirectByteBuffers);
                        continue block20;
                    }
                    case FLUSH: {
                        if (this._head || HttpConnection.this._generator.isNoContent()) {
                            BufferUtil.clear(this._chunk);
                            BufferUtil.clear(this._content);
                        }
                        int gatherWrite = 0;
                        long bytes = 0L;
                        if (BufferUtil.hasContent(this._header)) {
                            gatherWrite = (byte)(gatherWrite + 4);
                            bytes += (long)this._header.remaining();
                        }
                        if (BufferUtil.hasContent(this._chunk)) {
                            gatherWrite = (byte)(gatherWrite + 2);
                            bytes += (long)this._chunk.remaining();
                        }
                        if (BufferUtil.hasContent(this._content)) {
                            gatherWrite = (byte)(gatherWrite + 1);
                            bytes += (long)this._content.remaining();
                        }
                        HttpConnection.this.bytesOut.add(bytes);
                        switch (gatherWrite) {
                            case 7: {
                                HttpConnection.this.getEndPoint().write(this, this._header, this._chunk, this._content);
                                break;
                            }
                            case 6: {
                                HttpConnection.this.getEndPoint().write(this, this._header, this._chunk);
                                break;
                            }
                            case 5: {
                                HttpConnection.this.getEndPoint().write(this, this._header, this._content);
                                break;
                            }
                            case 4: {
                                HttpConnection.this.getEndPoint().write(this, this._header);
                                break;
                            }
                            case 3: {
                                HttpConnection.this.getEndPoint().write(this, this._chunk, this._content);
                                break;
                            }
                            case 2: {
                                HttpConnection.this.getEndPoint().write(this, this._chunk);
                                break;
                            }
                            case 1: {
                                HttpConnection.this.getEndPoint().write(this, this._content);
                                break;
                            }
                            default: {
                                this.succeeded();
                            }
                        }
                        return IteratingCallback.Action.SCHEDULED;
                    }
                    case SHUTDOWN_OUT: {
                        this._shutdownOut = true;
                        continue block20;
                    }
                    case DONE: {
                        if (HttpConnection.this.getConnector().isShutdown() && HttpConnection.this._generator.isEnd() && HttpConnection.this._generator.isPersistent()) {
                            this._shutdownOut = true;
                        }
                        return IteratingCallback.Action.SUCCEEDED;
                    }
                    case CONTINUE: {
                        continue block20;
                    }
                }
                break;
            }
            throw new IllegalStateException("generateResponse=" + result);
        }

        private Callback release() {
            Callback complete = this._callback;
            this._callback = null;
            this._info = null;
            this._content = null;
            this.releaseHeader();
            this.releaseChunk();
            return complete;
        }

        private void releaseHeader() {
            if (this._header != null) {
                HttpConnection.this._bufferPool.release(this._header);
            }
            this._header = null;
        }

        private void releaseChunk() {
            if (this._chunk != null) {
                HttpConnection.this._bufferPool.release(this._chunk);
            }
            this._chunk = null;
        }

        @Override
        protected void onCompleteSuccess() {
            boolean upgrading = HttpConnection.this._channel.getRequest().getAttribute(HttpTransport.UPGRADE_CONNECTION_ATTRIBUTE) != null;
            this.release().succeeded();
            if (this._shutdownOut && !upgrading) {
                HttpConnection.this.getEndPoint().shutdownOutput();
            }
        }

        @Override
        public void onCompleteFailure(Throwable x) {
            HttpConnection.this.failedCallback(this.release(), x);
            if (this._shutdownOut) {
                HttpConnection.this.getEndPoint().shutdownOutput();
            }
        }

        @Override
        public String toString() {
            return String.format("%s[i=%s,cb=%s]", super.toString(), this._info, this._callback);
        }
    }

    private class Content
    extends HttpInput.Content {
        public Content(ByteBuffer content) {
            super(content);
            HttpConnection.this._retainableByteBuffer.retain();
        }

        @Override
        public void succeeded() {
            HttpConnection.this._retainableByteBuffer.release();
        }

        @Override
        public void failed(Throwable x) {
            this.succeeded();
        }
    }
}

