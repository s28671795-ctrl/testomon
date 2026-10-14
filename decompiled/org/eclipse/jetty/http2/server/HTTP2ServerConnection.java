/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Executor;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.HTTP2Channel;
import org.eclipse.jetty.http2.HTTP2Connection;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PrefaceFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.http2.parser.ServerParser;
import org.eclipse.jetty.http2.parser.SettingsBodyParser;
import org.eclipse.jetty.http2.server.HttpChannelOverHTTP2;
import org.eclipse.jetty.http2.server.HttpTransportOverHTTP2;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.thread.AutoLock;

public class HTTP2ServerConnection
extends HTTP2Connection {
    private final AutoLock lock = new AutoLock();
    private final Queue<HttpChannelOverHTTP2> channels = new ArrayDeque<HttpChannelOverHTTP2>();
    private final List<Frame> upgradeFrames = new ArrayList<Frame>();
    private final ServerSessionListener listener;
    private final HttpConfiguration httpConfig;
    private boolean recycleHttpChannels = true;

    public static boolean isSupportedProtocol(String protocol) {
        switch (protocol) {
            case "h2": 
            case "h2-17": 
            case "h2-16": 
            case "h2-15": 
            case "h2-14": 
            case "h2c": 
            case "h2c-17": 
            case "h2c-16": 
            case "h2c-15": 
            case "h2c-14": {
                return true;
            }
        }
        return false;
    }

    public HTTP2ServerConnection(RetainableByteBufferPool retainableByteBufferPool, Executor executor2, EndPoint endPoint, HttpConfiguration httpConfig, ServerParser parser, ISession session, int inputBufferSize, ServerSessionListener listener) {
        super(retainableByteBufferPool, executor2, endPoint, parser, session, inputBufferSize);
        this.listener = listener;
        this.httpConfig = httpConfig;
    }

    @Override
    protected ServerParser getParser() {
        return (ServerParser)super.getParser();
    }

    public boolean isRecycleHttpChannels() {
        return this.recycleHttpChannels;
    }

    public void setRecycleHttpChannels(boolean recycleHttpChannels) {
        this.recycleHttpChannels = recycleHttpChannels;
    }

    @Override
    public void onOpen() {
        ISession session = this.getSession();
        this.notifyAccept(session);
        for (Frame frame : this.upgradeFrames) {
            session.onFrame(frame);
        }
        super.onOpen();
        this.produce();
    }

    private void notifyAccept(ISession session) {
        try {
            this.listener.onAccept(session);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    public void onNewStream(Connector connector, IStream stream, HeadersFrame frame) {
        HttpChannelOverHTTP2 channel;
        Runnable task;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing {} on {}", (Object)frame, (Object)stream);
        }
        if ((task = (channel = this.provideHttpChannel(connector, stream)).onRequest(frame)) != null) {
            this.offerTask(task, false);
        }
    }

    public void onData(IStream stream, DataFrame frame, Callback callback) {
        HTTP2Channel.Server channel;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing {} on {}", (Object)frame, (Object)stream);
        }
        if ((channel = (HTTP2Channel.Server)stream.getAttachment()) != null) {
            Runnable task = channel.onData(frame, callback);
            if (task != null) {
                this.offerTask(task, false);
            }
        } else {
            callback.failed(new IOException("channel_not_found"));
        }
    }

    public void onTrailers(IStream stream, HeadersFrame frame) {
        Runnable task;
        HTTP2Channel.Server channel;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing trailers {} on {}", (Object)frame, (Object)stream);
        }
        if ((channel = (HTTP2Channel.Server)stream.getAttachment()) != null && (task = channel.onTrailer(frame)) != null) {
            this.offerTask(task, false);
        }
    }

    public boolean onStreamTimeout(IStream stream, Throwable failure) {
        boolean result;
        HTTP2Channel.Server channel = (HTTP2Channel.Server)stream.getAttachment();
        boolean bl = result = channel != null && channel.onTimeout(failure, task -> this.offerTask((Runnable)task, true));
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} idle timeout on {}: {}", result ? "Processed" : "Ignored", stream, failure);
        }
        return result;
    }

    public void onStreamFailure(IStream stream, Throwable failure, Callback callback) {
        HTTP2Channel.Server channel;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing stream failure on {}", (Object)stream, (Object)failure);
        }
        if ((channel = (HTTP2Channel.Server)stream.getAttachment()) != null) {
            Runnable task = channel.onFailure(failure, callback);
            if (task != null) {
                this.offerTask(task, true);
            }
        } else {
            callback.succeeded();
        }
    }

    public boolean onSessionTimeout(Throwable failure) {
        ISession session = this.getSession();
        boolean result = session.getStreams().stream().map(stream -> (IStream)stream).map(stream -> (HTTP2Channel.Server)stream.getAttachment()).filter(Objects::nonNull).map(HTTP2Channel.Server::isIdle).reduce(true, Boolean::logicalAnd);
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} idle timeout on {}: {}", result ? "Processed" : "Ignored", session, failure);
        }
        return result;
    }

    public void onSessionFailure(Throwable failure, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing session failure on {}", (Object)this.getSession(), (Object)failure);
        }
        callback.succeeded();
    }

    public void push(Connector connector, IStream stream, MetaData.Request request) {
        HttpChannelOverHTTP2 channel;
        Runnable task;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing push {} on {}", (Object)request, (Object)stream);
        }
        if ((task = (channel = this.provideHttpChannel(connector, stream)).onPushRequest(request)) != null) {
            this.offerTask(task, true);
        }
    }

    private HttpChannelOverHTTP2 provideHttpChannel(Connector connector, IStream stream) {
        HttpChannelOverHTTP2 channel = this.pollHttpChannel();
        if (channel != null) {
            channel.getHttpTransport().setStream(stream);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Recycling channel {} for {}", (Object)channel, (Object)this);
            }
        } else {
            HttpTransportOverHTTP2 transport = new HttpTransportOverHTTP2(connector, this);
            transport.setStream(stream);
            channel = this.newServerHttpChannelOverHTTP2(connector, this.httpConfig, transport);
            channel.setUseOutputDirectByteBuffers(this.isUseOutputDirectByteBuffers());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Creating channel {} for {}", (Object)channel, (Object)this);
            }
        }
        stream.setAttachment(channel);
        return channel;
    }

    protected ServerHttpChannelOverHTTP2 newServerHttpChannelOverHTTP2(Connector connector, HttpConfiguration httpConfig, HttpTransportOverHTTP2 transport) {
        return new ServerHttpChannelOverHTTP2(connector, httpConfig, this.getEndPoint(), transport);
    }

    private void offerHttpChannel(HttpChannelOverHTTP2 channel) {
        if (this.isRecycleHttpChannels()) {
            try (AutoLock l = this.lock.lock();){
                this.channels.offer(channel);
            }
        }
    }

    private HttpChannelOverHTTP2 pollHttpChannel() {
        if (this.isRecycleHttpChannels()) {
            try (AutoLock l = this.lock.lock();){
                HttpChannelOverHTTP2 httpChannelOverHTTP2 = this.channels.poll();
                return httpChannelOverHTTP2;
            }
        }
        return null;
    }

    public boolean upgrade(MetaData.Request request, HttpFields.Mutable responseFields) {
        if (HttpMethod.PRI.is(request.getMethod())) {
            this.getParser().directUpgrade();
        } else {
            SettingsFrame settingsFrame;
            HttpField settingsField = request.getFields().getField(HttpHeader.HTTP2_SETTINGS);
            if (settingsField == null) {
                throw new BadMessageException("Missing " + String.valueOf((Object)HttpHeader.HTTP2_SETTINGS) + " header");
            }
            String value = settingsField.getValue();
            byte[] settings = Base64.getUrlDecoder().decode(value == null ? "" : value);
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} {}: {}", new Object[]{this, HttpHeader.HTTP2_SETTINGS, TypeUtil.toHexString(settings)});
            }
            if ((settingsFrame = SettingsBodyParser.parseBody(BufferUtil.toBuffer(settings))) == null) {
                LOG.warn("Invalid {} header value: {}", (Object)HttpHeader.HTTP2_SETTINGS, (Object)value);
                throw new BadMessageException();
            }
            responseFields.put(HttpHeader.UPGRADE, "h2c");
            responseFields.put(HttpHeader.CONNECTION, "Upgrade");
            this.getParser().standardUpgrade();
            this.upgradeFrames.add(new PrefaceFrame());
            this.upgradeFrames.add(settingsFrame);
            this.upgradeFrames.add(new HeadersFrame(1, request, null, true));
        }
        return true;
    }

    protected class ServerHttpChannelOverHTTP2
    extends HttpChannelOverHTTP2
    implements Closeable {
        public ServerHttpChannelOverHTTP2(Connector connector, HttpConfiguration configuration, EndPoint endPoint, HttpTransportOverHTTP2 transport) {
            super(connector, configuration, endPoint, transport);
        }

        @Override
        protected boolean checkAndPrepareUpgrade() {
            return this.isTunnel() && this.getHttpTransport().prepareUpgrade();
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            if (!this.getStream().isReset() && !this.isTunnel()) {
                this.recycle();
            }
        }

        private boolean isTunnel() {
            return MetaData.isTunnel(this.getRequest().getMethod(), this.getResponse().getStatus());
        }

        @Override
        public void recycle() {
            this.getStream().setAttachment(null);
            super.recycle();
            HTTP2ServerConnection.this.offerHttpChannel(this);
        }

        @Override
        public void close() {
            IStream stream = this.getStream();
            if (LOG.isDebugEnabled()) {
                LOG.debug("HTTP2 Request #{}/{} rejected", (Object)stream.getId(), (Object)Integer.toHexString(stream.getSession().hashCode()));
            }
            stream.reset(new ResetFrame(stream.getId(), ErrorCode.ENHANCE_YOUR_CALM_ERROR.code), Callback.NOOP);
            this.consumeInput();
        }
    }
}

