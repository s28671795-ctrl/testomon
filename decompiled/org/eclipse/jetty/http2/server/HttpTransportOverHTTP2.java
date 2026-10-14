/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.server.HTTP2ServerConnection;
import org.eclipse.jetty.http2.server.HttpChannelOverHTTP2;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpTransport;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Promise;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpTransportOverHTTP2
implements HttpTransport {
    private static final Logger LOG = LoggerFactory.getLogger(HttpTransportOverHTTP2.class);
    private final AtomicBoolean commit = new AtomicBoolean();
    private final TransportCallback transportCallback = new TransportCallback();
    private final Connector connector;
    private final HTTP2ServerConnection connection;
    private IStream stream;
    private MetaData.Response metaData;

    public HttpTransportOverHTTP2(Connector connector, HTTP2ServerConnection connection) {
        this.connector = connector;
        this.connection = connection;
    }

    public IStream getStream() {
        return this.stream;
    }

    public void setStream(IStream stream) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} setStream {}", (Object)this, (Object)stream.getId());
        }
        this.stream = stream;
    }

    public void recycle() {
        this.stream = null;
        this.commit.set(false);
    }

    @Override
    public void send(MetaData.Request request, MetaData.Response response, ByteBuffer content, boolean lastContent, Callback callback) {
        if (response != null) {
            this.sendHeaders(request, response, content, lastContent, callback);
        } else {
            this.sendContent(request, content, lastContent, callback);
        }
    }

    public void sendHeaders(MetaData.Request request, MetaData.Response response, ByteBuffer content, boolean lastContent, Callback callback) {
        HeadersFrame headersFrame;
        this.metaData = response;
        DataFrame dataFrame = null;
        HeadersFrame trailersFrame = null;
        boolean isHeadRequest = HttpMethod.HEAD.is(request.getMethod());
        boolean hasContent = BufferUtil.hasContent(content) && !isHeadRequest;
        int status = response.getStatus();
        if (HttpStatus.isInterim(status)) {
            if (hasContent) {
                callback.failed(new IllegalStateException("Interim response cannot have content"));
                return;
            }
            headersFrame = new HeadersFrame(this.stream.getId(), this.metaData, null, false);
        } else if (this.commit.compareAndSet(false, true)) {
            if (lastContent) {
                long realContentLength = BufferUtil.length(content);
                long contentLength = response.getContentLength();
                if (contentLength < 0L) {
                    this.metaData = new MetaData.Response(response.getHttpVersion(), response.getStatus(), response.getReason(), response.getFields(), realContentLength, response.getTrailerSupplier());
                } else if (hasContent && contentLength != realContentLength) {
                    callback.failed(new BadMessageException(500, String.format("Incorrect Content-Length %d!=%d", contentLength, realContentLength)));
                    return;
                }
            }
            if (hasContent) {
                headersFrame = new HeadersFrame(this.stream.getId(), this.metaData, null, false);
                if (lastContent) {
                    HttpFields trailers = this.retrieveTrailers();
                    if (trailers == null) {
                        dataFrame = new DataFrame(this.stream.getId(), content, true);
                    } else {
                        dataFrame = new DataFrame(this.stream.getId(), content, false);
                        trailersFrame = new HeadersFrame(this.stream.getId(), new MetaData(HttpVersion.HTTP_2, trailers), null, true);
                    }
                } else {
                    dataFrame = new DataFrame(this.stream.getId(), content, false);
                }
            } else if (lastContent) {
                if (this.isTunnel(request, this.metaData)) {
                    headersFrame = new HeadersFrame(this.stream.getId(), this.metaData, null, false);
                } else {
                    HttpFields trailers = this.retrieveTrailers();
                    if (trailers == null) {
                        headersFrame = new HeadersFrame(this.stream.getId(), this.metaData, null, true);
                    } else {
                        headersFrame = new HeadersFrame(this.stream.getId(), this.metaData, null, false);
                        trailersFrame = new HeadersFrame(this.stream.getId(), new MetaData(HttpVersion.HTTP_2, trailers), null, true);
                    }
                }
            } else {
                headersFrame = new HeadersFrame(this.stream.getId(), this.metaData, null, false);
            }
        } else {
            callback.failed(new IllegalStateException("committed"));
            return;
        }
        HeadersFrame hf = headersFrame;
        DataFrame df = dataFrame;
        HeadersFrame tf = trailersFrame;
        this.transportCallback.send(callback, true, c -> {
            if (LOG.isDebugEnabled()) {
                LOG.debug("HTTP2 Response #{}/{}:{}{} {}{}{}", new Object[]{this.stream.getId(), Integer.toHexString(this.stream.getSession().hashCode()), System.lineSeparator(), HttpVersion.HTTP_2, this.metaData.getStatus(), System.lineSeparator(), this.metaData.getFields()});
            }
            this.stream.send(new IStream.FrameList(hf, df, tf), (Callback)c);
        });
    }

    public void sendContent(MetaData.Request request, ByteBuffer content, boolean lastContent, Callback callback) {
        boolean hasContent;
        boolean isHeadRequest = HttpMethod.HEAD.is(request.getMethod());
        boolean bl = hasContent = BufferUtil.hasContent(content) && !isHeadRequest;
        if (hasContent || lastContent && !this.isTunnel(request, this.metaData)) {
            if (lastContent) {
                HttpFields trailers = this.retrieveTrailers();
                if (trailers == null) {
                    this.transportCallback.send(callback, false, c -> this.sendDataFrame(content, true, true, (Callback)c));
                } else {
                    SendTrailers sendTrailers = new SendTrailers(callback, trailers);
                    if (hasContent) {
                        this.transportCallback.send(sendTrailers, false, c -> this.sendDataFrame(content, true, false, (Callback)c));
                    } else {
                        sendTrailers.succeeded();
                    }
                }
            } else {
                this.transportCallback.send(callback, false, c -> this.sendDataFrame(content, false, false, (Callback)c));
            }
        } else {
            callback.succeeded();
        }
    }

    private HttpFields retrieveTrailers() {
        Supplier<HttpFields> supplier = this.metaData.getTrailerSupplier();
        if (supplier == null) {
            return null;
        }
        HttpFields trailers = supplier.get();
        if (trailers == null) {
            return null;
        }
        return trailers.size() == 0 ? null : trailers;
    }

    private boolean isTunnel(MetaData.Request request, MetaData.Response response) {
        return MetaData.isTunnel(request.getMethod(), response.getStatus());
    }

    @Override
    public boolean isPushSupported() {
        return this.stream.getSession().isPushEnabled();
    }

    @Override
    public void push(final MetaData.Request request) {
        if (!this.stream.getSession().isPushEnabled()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("HTTP/2 Push disabled for {}", (Object)request);
            }
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP/2 Push {}", (Object)request);
        }
        this.stream.push(new PushPromiseFrame(this.stream.getId(), request), new Promise<Stream>(){

            @Override
            public void succeeded(Stream pushStream) {
                HttpTransportOverHTTP2.this.connection.push(HttpTransportOverHTTP2.this.connector, (IStream)pushStream, request);
            }

            @Override
            public void failed(Throwable x) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Could not push {}", (Object)request, (Object)x);
                }
            }
        }, new Stream.Listener.Adapter());
    }

    private void sendDataFrame(ByteBuffer content, boolean lastContent, boolean endStream, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 Response #{}/{}: {} content bytes{}", this.stream.getId(), Integer.toHexString(this.stream.getSession().hashCode()), content.remaining(), lastContent ? " (last chunk)" : "");
        }
        DataFrame frame = new DataFrame(this.stream.getId(), content, endStream);
        this.stream.data(frame, callback);
    }

    private void sendTrailersFrame(MetaData metaData, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 Response #{}/{}: trailers", (Object)this.stream.getId(), (Object)Integer.toHexString(this.stream.getSession().hashCode()));
        }
        HeadersFrame frame = new HeadersFrame(this.stream.getId(), metaData, null, true);
        this.stream.headers(frame, callback);
    }

    public void onStreamFailure(Throwable failure) {
        this.transportCallback.abort(failure);
    }

    public boolean onStreamTimeout(Throwable failure) {
        return this.transportCallback.idleTimeout(failure);
    }

    boolean prepareUpgrade() {
        HttpChannelOverHTTP2 channel = (HttpChannelOverHTTP2)this.stream.getAttachment();
        Request request = channel.getRequest();
        if (request.getHttpInput().hasContent()) {
            return channel.sendErrorOrAbort("Unexpected content in CONNECT request");
        }
        Connection connection = (Connection)request.getAttribute(UPGRADE_CONNECTION_ATTRIBUTE);
        if (connection == null) {
            return channel.sendErrorOrAbort("No UPGRADE_CONNECTION_ATTRIBUTE available");
        }
        EndPoint endPoint = connection.getEndPoint();
        endPoint.upgrade(connection);
        this.stream.setAttachment(endPoint);
        this.stream.demand(1L);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Upgrading to {}", (Object)connection);
        }
        return false;
    }

    @Override
    public void onCompleted() {
        Object attachment = this.stream.getAttachment();
        if (attachment instanceof HttpChannelOverHTTP2) {
            if (!this.stream.isClosed()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("HTTP2 Response #{}: unconsumed request content, resetting stream", (Object)this.stream.getId());
                }
                this.stream.reset(new ResetFrame(this.stream.getId(), ErrorCode.CANCEL_STREAM_ERROR.code), Callback.NOOP);
            }
            HttpChannelOverHTTP2 channel = (HttpChannelOverHTTP2)attachment;
            channel.consumeInput();
        }
    }

    @Override
    public void abort(Throwable failure) {
        IStream stream = this.stream;
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 Response #{}/{} aborted", (Object)(stream == null ? -1 : stream.getId()), stream == null ? Integer.valueOf(-1) : Integer.toHexString(stream.getSession().hashCode()));
        }
        if (stream != null) {
            stream.reset(new ResetFrame(stream.getId(), ErrorCode.CANCEL_STREAM_ERROR.code), Callback.NOOP);
        }
    }

    private class TransportCallback
    implements Callback {
        private final AutoLock _lock = new AutoLock();
        private State _state = State.IDLE;
        private Callback _callback;
        private boolean _commit;
        private Throwable _failure;

        private TransportCallback() {
        }

        private void reset(Throwable failure) {
            assert (this._lock.isHeldByCurrentThread());
            this._state = failure != null ? State.FAILED : State.IDLE;
            this._callback = null;
            this._commit = false;
            this._failure = failure;
        }

        private void send(Callback callback, boolean commit, Consumer<Callback> sendFrame) {
            Throwable failure = this.sending(callback, commit);
            if (failure == null) {
                sendFrame.accept(this);
            } else {
                callback.failed(failure);
            }
        }

        private void abort(Throwable failure) {
            this.failed(failure);
        }

        private Throwable sending(Callback callback, boolean commit) {
            try (AutoLock l = this._lock.lock();){
                switch (this._state) {
                    case IDLE: {
                        this._state = State.SENDING;
                        this._callback = callback;
                        this._commit = commit;
                        Throwable throwable = null;
                        return throwable;
                    }
                    case FAILED: {
                        Throwable throwable = this._failure;
                        return throwable;
                    }
                }
                IllegalStateException illegalStateException = new IllegalStateException("Invalid transport state: " + String.valueOf((Object)this._state));
                return illegalStateException;
            }
        }

        @Override
        public void succeeded() {
            boolean commit;
            Callback callback;
            try (AutoLock l = this._lock.lock();){
                if (this._state != State.SENDING) {
                    return;
                }
                callback = this._callback;
                commit = this._commit;
                this.reset(null);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("HTTP2 Response #{}/{} {} success", HttpTransportOverHTTP2.this.stream.getId(), Integer.toHexString(HttpTransportOverHTTP2.this.stream.getSession().hashCode()), commit ? "commit" : "flush");
            }
            callback.succeeded();
        }

        @Override
        public void failed(Throwable failure) {
            boolean commit;
            Callback callback;
            try (AutoLock l = this._lock.lock();){
                if (this._state != State.SENDING) {
                    this.reset(failure);
                    return;
                }
                callback = this._callback;
                commit = this._commit;
                this.reset(failure);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("HTTP2 Response #{}/{} {} failure", HttpTransportOverHTTP2.this.stream.getId(), Integer.toHexString(HttpTransportOverHTTP2.this.stream.getSession().hashCode()), commit ? "commit" : "flush", failure);
            }
            callback.failed(failure);
        }

        private boolean idleTimeout(Throwable failure) {
            boolean timeout2;
            Callback callback = null;
            try (AutoLock l = this._lock.lock();){
                if (this._state == State.SENDING) {
                    callback = this._callback;
                    this.reset(failure);
                }
            }
            boolean bl = timeout2 = callback != null;
            if (LOG.isDebugEnabled()) {
                LOG.debug("HTTP2 Response #{}/{} idle timeout {}", HttpTransportOverHTTP2.this.stream.getId(), Integer.toHexString(HttpTransportOverHTTP2.this.stream.getSession().hashCode()), timeout2 ? "expired" : "ignored", failure);
            }
            if (timeout2) {
                callback.failed(failure);
            }
            return timeout2;
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            Callback callback;
            try (AutoLock l = this._lock.lock();){
                callback = this._callback;
            }
            return callback != null ? callback.getInvocationType() : Callback.super.getInvocationType();
        }
    }

    private class SendTrailers
    extends Callback.Nested {
        private final HttpFields trailers;

        private SendTrailers(Callback callback, HttpFields trailers) {
            super(callback);
            this.trailers = trailers;
        }

        @Override
        public void succeeded() {
            HttpTransportOverHTTP2.this.transportCallback.send(this.getCallback(), false, c -> HttpTransportOverHTTP2.this.sendTrailersFrame(new MetaData(HttpVersion.HTTP_2, this.trailers), (Callback)c));
        }
    }

    private static enum State {
        IDLE,
        SENDING,
        FAILED;

    }
}

