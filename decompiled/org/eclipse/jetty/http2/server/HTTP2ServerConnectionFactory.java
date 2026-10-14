/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.GoAwayFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.server.AbstractHTTP2ServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.QuietException;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.NegotiatingServerConnection;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.annotation.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTP2ServerConnectionFactory
extends AbstractHTTP2ServerConnectionFactory
implements NegotiatingServerConnection.CipherDiscriminator {
    private static final Logger LOG = LoggerFactory.getLogger(HTTP2ServerConnectionFactory.class);

    public HTTP2ServerConnectionFactory(@Name(value="config") HttpConfiguration httpConfiguration) {
        super(httpConfiguration);
    }

    public HTTP2ServerConnectionFactory(@Name(value="config") HttpConfiguration httpConfiguration, String ... protocols) {
        super(httpConfiguration, protocols);
    }

    @Override
    protected ServerSessionListener newSessionListener(Connector connector, EndPoint endPoint) {
        return new HTTPServerSessionListener(connector, endPoint);
    }

    @Override
    public boolean isAcceptable(String protocol, String tlsProtocol, String tlsCipher) {
        boolean acceptable;
        boolean bl = acceptable = "h2-14".equals(protocol) || !HTTP2Cipher.isBlackListProtocol(tlsProtocol) || !HTTP2Cipher.isBlackListCipher(tlsCipher);
        if (LOG.isDebugEnabled()) {
            LOG.debug("proto={} tls={} cipher={} 9.2.2-acceptable={}", protocol, tlsProtocol, tlsCipher, acceptable);
        }
        return acceptable;
    }

    protected class HTTPServerSessionListener
    extends ServerSessionListener.Adapter
    implements Stream.Listener {
        private final Connector connector;
        private final EndPoint endPoint;

        public HTTPServerSessionListener(Connector connector, EndPoint endPoint) {
            this.connector = connector;
            this.endPoint = endPoint;
        }

        protected HTTP2ServerConnection getConnection() {
            return (HTTP2ServerConnection)this.endPoint.getConnection();
        }

        @Override
        public Map<Integer, Integer> onPreface(Session session) {
            return HTTP2ServerConnectionFactory.this.newSettings();
        }

        @Override
        public Stream.Listener onNewStream(Stream stream, HeadersFrame frame) {
            this.getConnection().onNewStream(this.connector, (IStream)stream, frame);
            return this;
        }

        @Override
        public void onBeforeData(Stream stream) {
        }

        @Override
        public boolean onIdleTimeout(Session session) {
            boolean close = super.onIdleTimeout(session);
            if (!close) {
                return false;
            }
            long idleTimeout = this.getConnection().getEndPoint().getIdleTimeout();
            return this.getConnection().onSessionTimeout(new TimeoutException("Session idle timeout " + idleTimeout + " ms"));
        }

        @Override
        public void onClose(Session session, GoAwayFrame frame, Callback callback) {
            Object reason = frame.tryConvertPayload();
            if (!StringUtil.isEmpty((String)reason)) {
                reason = " (" + (String)reason + ")";
            }
            EofException failure = new EofException(String.format("Close %s/%s", ErrorCode.toString(frame.getError(), null), reason));
            this.onFailure(session, (Throwable)failure, callback);
        }

        @Override
        public void onFailure(Session session, Throwable failure, Callback callback) {
            this.getConnection().onSessionFailure(failure, callback);
        }

        @Override
        public void onHeaders(Stream stream, HeadersFrame frame) {
            if (frame.isEndStream()) {
                this.getConnection().onTrailers((IStream)stream, frame);
            } else {
                this.close(stream, "invalid_trailers");
            }
        }

        @Override
        public Stream.Listener onPush(Stream stream, PushPromiseFrame frame) {
            this.close(stream, "push_promise");
            return null;
        }

        @Override
        public void onDataDemanded(Stream stream, DataFrame frame, Callback callback) {
            this.getConnection().onData((IStream)stream, frame, callback);
        }

        @Override
        public void onReset(Stream stream, ResetFrame frame, Callback callback) {
            EofException failure = new EofException("Reset " + ErrorCode.toString(frame.getError(), null));
            this.onFailure(stream, (Throwable)failure, callback);
        }

        @Override
        public void onFailure(Stream stream, int error, String reason, Throwable failure, Callback callback) {
            if (!(failure instanceof QuietException)) {
                failure = new EofException(failure);
            }
            this.onFailure(stream, failure, callback);
        }

        private void onFailure(Stream stream, Throwable failure, Callback callback) {
            this.getConnection().onStreamFailure((IStream)stream, failure, callback);
        }

        @Override
        public boolean onIdleTimeout(Stream stream, Throwable x) {
            return this.getConnection().onStreamTimeout((IStream)stream, x);
        }

        private void close(Stream stream, String reason) {
            stream.getSession().close(ErrorCode.PROTOCOL_ERROR.code, reason, Callback.NOOP);
        }
    }
}

