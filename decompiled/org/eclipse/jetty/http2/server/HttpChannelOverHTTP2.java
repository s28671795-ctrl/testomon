/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.http2.HTTP2Channel;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.server.HttpTransportOverHTTP2;
import org.eclipse.jetty.http2.server.ServerHTTP2StreamEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.WriteFlusher;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpChannelOverHTTP2
extends HttpChannel
implements Closeable,
WriteFlusher.Listener,
HTTP2Channel.Server {
    private static final Logger LOG = LoggerFactory.getLogger(HttpChannelOverHTTP2.class);
    private static final HttpField SERVER_VERSION = new PreEncodedHttpField(HttpHeader.SERVER, HttpConfiguration.SERVER_VERSION);
    private static final HttpField POWERED_BY = new PreEncodedHttpField(HttpHeader.X_POWERED_BY, HttpConfiguration.SERVER_VERSION);
    private boolean _expect100Continue;
    private boolean _delayedUntilContent;
    private boolean _useOutputDirectByteBuffers;
    private final ContentDemander _contentDemander = new ContentDemander();
    private static final HttpInput.Content EOF = new HttpInput.EofContent();
    private static final HttpInput.Content DEMANDING_NEEDED = new DemandingContent(true);
    private static final HttpInput.Content DEMANDING_NOT_NEEDED = new DemandingContent(false);

    public HttpChannelOverHTTP2(Connector connector, HttpConfiguration configuration, EndPoint endPoint, HttpTransportOverHTTP2 transport) {
        super(connector, configuration, endPoint, transport);
    }

    protected IStream getStream() {
        return this.getHttpTransport().getStream();
    }

    @Override
    public boolean isUseOutputDirectByteBuffers() {
        return this._useOutputDirectByteBuffers;
    }

    public void setUseOutputDirectByteBuffers(boolean useOutputDirectByteBuffers) {
        this._useOutputDirectByteBuffers = useOutputDirectByteBuffers;
    }

    @Override
    public boolean isExpecting100Continue() {
        return this._expect100Continue;
    }

    @Override
    public void setIdleTimeout(long timeoutMs) {
        this.getStream().setIdleTimeout(timeoutMs);
    }

    @Override
    public long getIdleTimeout() {
        return this.getStream().getIdleTimeout();
    }

    @Override
    public void onFlushed(long bytes) throws IOException {
        this.getResponse().getHttpOutput().onFlushed(bytes);
    }

    public Runnable onRequest(HeadersFrame frame) {
        try {
            MetaData.Request request = (MetaData.Request)frame.getMetaData();
            HttpFields fields = request.getFields();
            this._expect100Continue = fields.contains(HttpHeader.EXPECT, HttpHeaderValue.CONTINUE.asString());
            HttpFields.Mutable response = this.getResponse().getHttpFields();
            if (this.getHttpConfiguration().getSendServerVersion()) {
                response.add(SERVER_VERSION);
            }
            if (this.getHttpConfiguration().getSendXPoweredBy()) {
                response.add(POWERED_BY);
            }
            this.onRequest(request);
            boolean endStream = frame.isEndStream();
            if (endStream) {
                this.onContentComplete();
                this.onRequestComplete();
            }
            boolean connect2 = request instanceof MetaData.ConnectRequest;
            boolean bl = this._delayedUntilContent = this.getHttpConfiguration().isDelayDispatchUntilContent() && !endStream && !this._expect100Continue && !connect2;
            if (connect2) {
                if (request.getProtocol() == null) {
                    this._contentDemander.demand(false);
                }
            } else if (this._delayedUntilContent) {
                this._contentDemander.demand(false);
            }
            if (LOG.isDebugEnabled()) {
                IStream stream = this.getStream();
                LOG.debug("HTTP2 Request #{}/{}, delayed={}:{}{} {} {}{}{}", new Object[]{stream.getId(), Integer.toHexString(stream.getSession().hashCode()), this._delayedUntilContent, System.lineSeparator(), request.getMethod(), request.getURI(), request.getHttpVersion(), System.lineSeparator(), fields});
            }
            return this._delayedUntilContent ? null : this;
        }
        catch (BadMessageException x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("onRequest", x);
            }
            return () -> this.onBadMessage(x);
        }
        catch (Throwable x) {
            return () -> this.onBadMessage(new BadMessageException(500, null, x));
        }
    }

    public Runnable onPushRequest(MetaData.Request request) {
        try {
            this.onRequest(request);
            this.getRequest().setAttribute("org.eclipse.jetty.pushed", Boolean.TRUE);
            this.onContentComplete();
            this.onRequestComplete();
            if (LOG.isDebugEnabled()) {
                IStream stream = this.getStream();
                LOG.debug("HTTP2 PUSH Request #{}/{}:{}{} {} {}{}{}", new Object[]{stream.getId(), Integer.toHexString(stream.getSession().hashCode()), System.lineSeparator(), request.getMethod(), request.getURI(), request.getHttpVersion(), System.lineSeparator(), request.getFields()});
            }
            return this;
        }
        catch (BadMessageException x) {
            return () -> this.onBadMessage(x);
        }
        catch (Throwable x) {
            return () -> this.onBadMessage(new BadMessageException(500, null, x));
        }
    }

    @Override
    public HttpTransportOverHTTP2 getHttpTransport() {
        return (HttpTransportOverHTTP2)super.getHttpTransport();
    }

    @Override
    public void recycle() {
        super.recycle();
        this.getHttpTransport().recycle();
        this._expect100Continue = false;
        this._delayedUntilContent = false;
        this._contentDemander.recycle();
    }

    @Override
    protected void commit(MetaData.Response info) {
        super.commit(info);
        if (LOG.isDebugEnabled()) {
            IStream stream = this.getStream();
            LOG.debug("HTTP2 Commit Response #{}/{}:{}{} {} {}{}{}", new Object[]{stream.getId(), Integer.toHexString(stream.getSession().hashCode()), System.lineSeparator(), info.getHttpVersion(), info.getStatus(), info.getReason(), System.lineSeparator(), info.getFields()});
        }
    }

    @Override
    public Runnable onData(final DataFrame frame, final Callback callback) {
        ByteBuffer buffer = frame.getData();
        int length = buffer.remaining();
        HttpInput.Content content = new HttpInput.Content(buffer){

            @Override
            public boolean isEof() {
                return frame.isEndStream();
            }

            @Override
            public void succeeded() {
                callback.succeeded();
            }

            @Override
            public void failed(Throwable x) {
                callback.failed(x);
            }

            @Override
            public Invocable.InvocationType getInvocationType() {
                return callback.getInvocationType();
            }
        };
        boolean needed = this._contentDemander.onContent(content);
        boolean handle2 = this.onContent(content);
        boolean endStream = frame.isEndStream();
        if (endStream) {
            boolean handleContent = this.onContentComplete();
            boolean handleRequest = this.onRequestComplete();
            handle2 |= handleContent | handleRequest;
        }
        boolean woken = needed && this.getRequest().getHttpInput().onContentProducible();
        handle2 |= woken;
        if (LOG.isDebugEnabled()) {
            IStream stream = this.getStream();
            LOG.debug("HTTP2 Request #{}/{}: {} bytes of {} content, woken: {}, needed: {}, handle: {}", stream.getId(), Integer.toHexString(stream.getSession().hashCode()), length, endStream ? "last" : "some", woken, needed, handle2);
        }
        boolean wasDelayed = this._delayedUntilContent;
        this._delayedUntilContent = false;
        return handle2 || wasDelayed ? this : null;
    }

    @Override
    public boolean needContent() {
        boolean hasContent = this._contentDemander.demand(true);
        if (LOG.isDebugEnabled()) {
            LOG.debug("needContent has content? {}", (Object)hasContent);
        }
        return hasContent;
    }

    @Override
    public HttpInput.Content produceContent() {
        HttpInput.Content content = null;
        if (this._contentDemander.demand(false)) {
            content = this._contentDemander.poll();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("produceContent produced {}", (Object)content);
        }
        return content;
    }

    @Override
    public boolean failAllContent(Throwable failure) {
        IStream stream;
        if (LOG.isDebugEnabled()) {
            LOG.debug("failing all content with {} {}", (Object)failure, (Object)this);
        }
        boolean atEof = (stream = this.getStream()) == null || stream.failAllData(failure);
        atEof |= this._contentDemander.failContent(failure);
        if (LOG.isDebugEnabled()) {
            LOG.debug("failed all content, reached EOF? {}", (Object)atEof);
        }
        return atEof;
    }

    @Override
    public boolean failed(Throwable x) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("failed " + String.valueOf(x));
        }
        this._contentDemander.onContent(new HttpInput.ErrorContent(x));
        return this.getRequest().getHttpInput().onContentProducible();
    }

    @Override
    protected boolean eof() {
        this._contentDemander.eof();
        return false;
    }

    @Override
    public Runnable onTrailer(HeadersFrame frame) {
        HttpFields trailers = frame.getMetaData().getFields();
        if (trailers.size() > 0) {
            this.onTrailers(trailers);
        }
        if (LOG.isDebugEnabled()) {
            IStream stream = this.getStream();
            LOG.debug("HTTP2 Request #{}/{}, trailers:{}{}", stream.getId(), Integer.toHexString(stream.getSession().hashCode()), System.lineSeparator(), trailers);
        }
        boolean handle2 = this.onRequestComplete();
        boolean woken = this.getRequest().getHttpInput().onContentProducible();
        boolean wasDelayed = this._delayedUntilContent;
        this._delayedUntilContent = false;
        return (handle2 |= woken) || wasDelayed ? this : null;
    }

    @Override
    public boolean isIdle() {
        return this.getState().isIdle();
    }

    @Override
    public boolean onTimeout(Throwable failure, Consumer<Runnable> consumer) {
        boolean delayed = this._delayedUntilContent;
        this._delayedUntilContent = false;
        boolean reset = this.isIdle();
        if (reset) {
            this.consumeInput();
        }
        this.getHttpTransport().onStreamTimeout(failure);
        failure.addSuppressed(new Throwable("HttpInput idle timeout"));
        this._contentDemander.onTimeout(failure);
        boolean needed = this.getRequest().getHttpInput().onContentProducible();
        if (needed || delayed) {
            consumer.accept(this::handleWithContext);
            reset = false;
        }
        return reset;
    }

    @Override
    public Runnable onFailure(Throwable failure, Callback callback) {
        this.getHttpTransport().onStreamFailure(failure);
        boolean handle2 = this.failed(failure);
        this.consumeInput();
        return new FailureTask(failure, callback, handle2);
    }

    protected void consumeInput() {
        this.getRequest().getHttpInput().consumeAll();
    }

    private void handleWithContext() {
        ContextHandler context = this.getState().getContextHandler();
        if (context != null) {
            context.handle(this.getRequest(), this);
        } else {
            this.handle();
        }
    }

    @Override
    public void continue100(int available) throws IOException {
        if (this.isExpecting100Continue()) {
            this._expect100Continue = false;
            if (available == 0) {
                if (this.getResponse().isCommitted()) {
                    throw new IOException("Committed before 100 Continues");
                }
                boolean committed = this.sendResponse(HttpGenerator.CONTINUE_100_INFO, null, false);
                if (!committed) {
                    throw new IOException("Concurrent commit while trying to send 100-Continue");
                }
            }
        }
    }

    @Override
    public boolean isTunnellingSupported() {
        return true;
    }

    @Override
    public EndPoint getTunnellingEndPoint() {
        return new ServerHTTP2StreamEndPoint(this.getStream());
    }

    @Override
    public void close() {
        this.abort(new IOException("Unexpected close"));
    }

    @Override
    public String toString() {
        IStream stream = this.getStream();
        long streamId = stream == null ? -1L : (long)stream.getId();
        return String.format("%s#%d", super.toString(), streamId);
    }

    private class ContentDemander {
        private final AtomicReference<HttpInput.Content> _content = new AtomicReference();

        private ContentDemander() {
        }

        public void recycle() {
            HttpInput.Content c;
            if (LOG.isDebugEnabled()) {
                LOG.debug("recycle {}", (Object)this);
            }
            if ((c = (HttpInput.Content)this._content.getAndSet(null)) != null && !c.isSpecial()) {
                throw new AssertionError((Object)("unconsumed content: " + String.valueOf(c)));
            }
        }

        public HttpInput.Content poll() {
            HttpInput.Content c;
            do {
                c = this._content.get();
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("poll, content = {}", (Object)c);
            } while (c != null && !c.isSpecial() && !this._content.compareAndSet(c, c.isEof() ? EOF : null));
            if (LOG.isDebugEnabled()) {
                LOG.debug("returning current content");
            }
            return c;
        }

        public boolean demand(boolean needed) {
            boolean hasContent;
            HttpInput.Content c;
            while (true) {
                c = this._content.get();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("demand({}), content = {}", (Object)needed, (Object)c);
                }
                if (c instanceof DemandingContent) {
                    if (needed && !((DemandingContent)c).needed && !this._content.compareAndSet(c, DEMANDING_NEEDED)) {
                        if (!LOG.isDebugEnabled()) continue;
                        LOG.debug("already demanding but switched needed flag to true");
                        continue;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("already demanding, returning false");
                    }
                    return false;
                }
                if (c != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("content available, returning true");
                    }
                    return true;
                }
                if (this._content.compareAndSet(null, needed ? DEMANDING_NEEDED : DEMANDING_NOT_NEEDED)) break;
            }
            IStream stream = HttpChannelOverHTTP2.this.getStream();
            if (stream == null) {
                this._content.set(null);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("no content available, switched to demanding but stream is now null");
                }
                return false;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("no content available, demanding stream {}", (Object)stream);
            }
            stream.demand(1L);
            c = this._content.get();
            boolean bl = hasContent = !(c instanceof DemandingContent) && c != null;
            if (LOG.isDebugEnabled()) {
                LOG.debug("has content now? {}", (Object)hasContent);
            }
            return hasContent;
        }

        public boolean onContent(HttpInput.Content content) {
            HttpInput.Content c;
            block12: {
                while (true) {
                    c = this._content.get();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("content delivered by stream: {}, current content: {}", (Object)content, (Object)c);
                    }
                    if (c instanceof DemandingContent) {
                        if (!this._content.compareAndSet(c, content)) continue;
                        boolean needed = ((DemandingContent)c).needed;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("replacing demand content with {} succeeded; returning {}", (Object)content, (Object)needed);
                        }
                        return needed;
                    }
                    if (c == null) {
                        if (!content.isSpecial()) {
                            content.failed(new IllegalStateException("Non special content without demand : " + String.valueOf(content)));
                            return false;
                        }
                        if (!this._content.compareAndSet(null, content)) continue;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("replacing null content with {} succeeded", (Object)content);
                        }
                        return false;
                    }
                    if (c.isEof() && content.isEof() && content.isEmpty()) {
                        content.succeeded();
                        return true;
                    }
                    if (content.getError() == null) break block12;
                    if (c.getError() != null) {
                        if (c.getError() != content.getError()) {
                            c.getError().addSuppressed(content.getError());
                        }
                        return true;
                    }
                    if (this._content.compareAndSet(c, content)) break;
                }
                c.failed(content.getError());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("replacing current content with {} succeeded", (Object)content);
                }
                return true;
            }
            if (c.getError() != null && content.remaining() == 0) {
                content.succeeded();
                return true;
            }
            content.failed(new IllegalStateException("Cannot overwrite exiting content " + String.valueOf(c) + " with " + String.valueOf(content)));
            return false;
        }

        public boolean onTimeout(Throwable failure) {
            HttpInput.Content c;
            do {
                c = this._content.get();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("onTimeout with current content: {} and failure = {}", (Object)c, (Object)failure);
                }
                if (c instanceof DemandingContent) continue;
                return false;
            } while (!this._content.compareAndSet(c, new HttpInput.ErrorContent(failure)));
            if (LOG.isDebugEnabled()) {
                LOG.debug("replacing current content with error succeeded");
            }
            return true;
        }

        public void eof() {
            HttpInput.WrappingContent content;
            while (true) {
                HttpInput.Content c = this._content.get();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("eof with current content: {}", (Object)c);
                }
                if (c instanceof DemandingContent) {
                    if (!this._content.compareAndSet(c, EOF)) continue;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("replacing current content with special EOF succeeded");
                    }
                    return;
                }
                if (c == null) {
                    if (!this._content.compareAndSet(null, EOF)) continue;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("replacing null content with special EOF succeeded");
                    }
                    return;
                }
                if (c.isEof()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("current content already is EOF");
                    }
                    return;
                }
                if (c.remaining() == 0) {
                    if (!this._content.compareAndSet(c, EOF)) continue;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("replacing current content with special EOF succeeded");
                    }
                    return;
                }
                content = new HttpInput.WrappingContent(c, true);
                if (this._content.compareAndSet(c, content)) break;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("replacing current content with {} succeeded", (Object)content);
            }
        }

        public boolean failContent(Throwable failure) {
            HttpInput.Content c;
            do {
                c = this._content.get();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("failing current content {} with {} {}", c, failure, this);
                }
                if (c == null) {
                    return false;
                }
                if (!c.isSpecial()) continue;
                return c.isEof();
            } while (!this._content.compareAndSet(c, null));
            c.failed(failure);
            if (LOG.isDebugEnabled()) {
                LOG.debug("replacing current content with null succeeded");
            }
            return false;
        }

        public String toString() {
            return this.getClass().getSimpleName() + "@" + this.hashCode() + " _content=" + String.valueOf(this._content);
        }
    }

    private class FailureTask
    implements Runnable {
        private final Throwable failure;
        private final Callback callback;
        private final boolean handle;

        public FailureTask(Throwable failure, Callback callback, boolean handle2) {
            this.failure = failure;
            this.callback = callback;
            this.handle = handle2;
        }

        @Override
        public void run() {
            try {
                if (this.handle) {
                    HttpChannelOverHTTP2.this.handleWithContext();
                } else if (HttpChannelOverHTTP2.this.getHttpConfiguration().isNotifyRemoteAsyncErrors()) {
                    HttpChannelOverHTTP2.this.getState().asyncError(this.failure);
                }
                this.callback.succeeded();
            }
            catch (Throwable x) {
                this.callback.failed(x);
            }
        }

        public String toString() {
            return String.format("%s@%x[%s]", this.getClass().getName(), this.hashCode(), this.failure);
        }
    }

    private static final class DemandingContent
    extends HttpInput.SpecialContent {
        private final boolean needed;

        private DemandingContent(boolean needed) {
            this.needed = needed;
        }
    }
}

