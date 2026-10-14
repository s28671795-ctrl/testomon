/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.api;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.GoAwayFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PingFrame;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Promise;

public interface Session {
    default public CompletableFuture<Stream> newStream(HeadersFrame frame, Stream.Listener listener) {
        Promise.Completable<Stream> result = new Promise.Completable<Stream>();
        this.newStream(frame, result, listener);
        return result;
    }

    public void newStream(HeadersFrame var1, Promise<Stream> var2, Stream.Listener var3);

    public int priority(PriorityFrame var1, Callback var2);

    public void settings(SettingsFrame var1, Callback var2);

    public void ping(PingFrame var1, Callback var2);

    public boolean close(int var1, String var2, Callback var3);

    public boolean isClosed();

    public Collection<Stream> getStreams();

    public Stream getStream(int var1);

    @Deprecated
    public InetSocketAddress getLocalAddress();

    default public SocketAddress getLocalSocketAddress() {
        return this.getLocalAddress();
    }

    @Deprecated
    public InetSocketAddress getRemoteAddress();

    default public SocketAddress getRemoteSocketAddress() {
        return this.getRemoteAddress();
    }

    public static interface Listener {
        public Map<Integer, Integer> onPreface(Session var1);

        public Stream.Listener onNewStream(Stream var1, HeadersFrame var2);

        public void onSettings(Session var1, SettingsFrame var2);

        public void onPing(Session var1, PingFrame var2);

        public void onReset(Session var1, ResetFrame var2);

        default public void onGoAway(Session session, GoAwayFrame frame) {
        }

        default public void onClose(Session session, GoAwayFrame frame, Callback callback) {
            try {
                this.onClose(session, frame);
                callback.succeeded();
            }
            catch (Throwable x) {
                callback.failed(x);
            }
        }

        public void onClose(Session var1, GoAwayFrame var2);

        public boolean onIdleTimeout(Session var1);

        default public void onFailure(Session session, Throwable failure, Callback callback) {
            try {
                this.onFailure(session, failure);
                callback.succeeded();
            }
            catch (Throwable x) {
                callback.failed(x);
            }
        }

        public void onFailure(Session var1, Throwable var2);

        public static class Adapter
        implements Listener {
            @Override
            public Map<Integer, Integer> onPreface(Session session) {
                return null;
            }

            @Override
            public Stream.Listener onNewStream(Stream stream, HeadersFrame frame) {
                return null;
            }

            @Override
            public void onSettings(Session session, SettingsFrame frame) {
            }

            @Override
            public void onPing(Session session, PingFrame frame) {
            }

            @Override
            public void onReset(Session session, ResetFrame frame) {
            }

            @Override
            public void onClose(Session session, GoAwayFrame frame) {
            }

            @Override
            public boolean onIdleTimeout(Session session) {
                return true;
            }

            @Override
            public void onFailure(Session session, Throwable failure) {
            }
        }
    }
}

