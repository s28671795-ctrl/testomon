/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.server;

import java.util.function.Consumer;
import org.eclipse.jetty.http2.HTTP2Channel;
import org.eclipse.jetty.http2.HTTP2StreamEndPoint;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHTTP2StreamEndPoint
extends HTTP2StreamEndPoint
implements HTTP2Channel.Server {
    private static final Logger LOG = LoggerFactory.getLogger(ServerHTTP2StreamEndPoint.class);

    public ServerHTTP2StreamEndPoint(IStream stream) {
        super(stream);
    }

    @Override
    public Runnable onData(DataFrame frame, Callback callback) {
        this.offerData(frame, callback);
        return null;
    }

    @Override
    public Runnable onTrailer(HeadersFrame frame) {
        return null;
    }

    @Override
    public boolean onTimeout(Throwable failure, Consumer<Runnable> consumer) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("idle timeout on {}: {}", (Object)this, (Object)failure);
        }
        boolean result = true;
        Connection connection = this.getConnection();
        if (connection != null) {
            result = connection.onIdleExpired();
        }
        if (result) {
            this.offerFailure(failure);
            consumer.accept(() -> this.close(failure));
        }
        return result;
    }

    @Override
    public Runnable onFailure(Throwable failure, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("failure on {}: {}", (Object)this, (Object)failure);
        }
        this.offerFailure(failure);
        this.close(failure);
        return callback::succeeded;
    }

    @Override
    public boolean isIdle() {
        return false;
    }
}

