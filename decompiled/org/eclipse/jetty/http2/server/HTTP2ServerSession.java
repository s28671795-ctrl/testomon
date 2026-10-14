/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.CloseState;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.FlowControlStrategy;
import org.eclipse.jetty.http2.HTTP2Session;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.http2.generator.Generator;
import org.eclipse.jetty.http2.parser.ServerParser;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTP2ServerSession
extends HTTP2Session
implements ServerParser.Listener {
    private static final Logger LOG = LoggerFactory.getLogger(HTTP2ServerSession.class);
    private final ServerSessionListener listener;

    public HTTP2ServerSession(Scheduler scheduler, EndPoint endPoint, Generator generator, ServerSessionListener listener, FlowControlStrategy flowControl) {
        super(scheduler, endPoint, generator, listener, flowControl, 2);
        this.listener = listener;
    }

    @Override
    public void onPreface() {
        Map<Integer, Integer> settings = this.notifyPreface(this);
        if (settings == null) {
            settings = Collections.emptyMap();
        }
        SettingsFrame settingsFrame = new SettingsFrame(settings, false);
        int sessionWindow = this.getInitialSessionRecvWindow() - 65535;
        this.updateRecvWindow(sessionWindow);
        if (sessionWindow > 0) {
            this.frames(null, List.of(settingsFrame, new WindowUpdateFrame(0, sessionWindow)), Callback.NOOP);
        } else {
            this.frames(null, List.of(settingsFrame), Callback.NOOP);
        }
    }

    @Override
    public void onHeaders(HeadersFrame frame) {
        int streamId;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {}", (Object)frame);
        }
        if (!HTTP2ServerSession.isClientStream(streamId = frame.getStreamId())) {
            this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "invalid_stream_id");
            return;
        }
        IStream stream = this.getStream(streamId);
        MetaData metaData = frame.getMetaData();
        if (metaData.isRequest()) {
            if (stream == null) {
                if (this.isRemoteStreamClosed(streamId)) {
                    this.onConnectionFailure(ErrorCode.STREAM_CLOSED_ERROR.code, "unexpected_headers_frame");
                } else {
                    stream = this.createRemoteStream(streamId, (MetaData.Request)metaData);
                    if (stream != null) {
                        this.onStreamOpened(stream);
                        if (metaData instanceof MetaData.ConnectRequest && !this.isConnectProtocolEnabled() && ((MetaData.ConnectRequest)metaData).getProtocol() != null) {
                            stream.reset(new ResetFrame(streamId, ErrorCode.PROTOCOL_ERROR.code), Callback.NOOP);
                            return;
                        }
                        stream.process(frame, Callback.NOOP);
                        boolean closed = stream.updateClose(frame.isEndStream(), CloseState.Event.RECEIVED);
                        Stream.Listener listener = this.notifyNewStream(stream, frame);
                        stream.setListener(listener);
                        if (closed) {
                            this.removeStream(stream);
                        }
                    }
                }
            } else {
                this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "duplicate_stream");
            }
        } else if (metaData.isResponse()) {
            this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "invalid_request");
        } else if (stream != null) {
            stream.process(frame, Callback.NOOP);
            boolean closed = stream.updateClose(frame.isEndStream(), CloseState.Event.RECEIVED);
            this.notifyHeaders(stream, frame);
            if (closed) {
                this.removeStream(stream);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stream #{} not found", (Object)streamId);
            }
            this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "unexpected_headers_frame");
        }
    }

    @Override
    public void onPushPromise(PushPromiseFrame frame) {
        this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "push_promise");
    }

    private Map<Integer, Integer> notifyPreface(Session session) {
        try {
            return this.listener.onPreface(session);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
            return null;
        }
    }

    @Override
    public void onFrame(Frame frame) {
        switch (frame.getType()) {
            case PREFACE: {
                this.onPreface();
                break;
            }
            case SETTINGS: {
                this.onSettings((SettingsFrame)frame, false);
                break;
            }
            case HEADERS: {
                this.onHeaders((HeadersFrame)frame);
                break;
            }
            default: {
                super.onFrame(frame);
            }
        }
    }
}

