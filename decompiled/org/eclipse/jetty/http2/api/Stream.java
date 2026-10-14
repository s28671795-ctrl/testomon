/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.api;

import java.util.concurrent.CompletableFuture;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Promise;

public interface Stream {
    public int getId();

    public Session getSession();

    default public CompletableFuture<Stream> headers(HeadersFrame frame) {
        Promise.Completable<Stream> result = new Promise.Completable<Stream>();
        this.headers(frame, Callback.from(() -> result.succeeded(this), result::failed));
        return result;
    }

    public void headers(HeadersFrame var1, Callback var2);

    default public CompletableFuture<Stream> push(PushPromiseFrame frame, Listener listener) {
        Promise.Completable<Stream> result = new Promise.Completable<Stream>();
        this.push(frame, result, listener);
        return result;
    }

    public void push(PushPromiseFrame var1, Promise<Stream> var2, Listener var3);

    default public CompletableFuture<Stream> data(DataFrame frame) {
        Promise.Completable<Stream> result = new Promise.Completable<Stream>();
        this.data(frame, Callback.from(() -> result.succeeded(this), result::failed));
        return result;
    }

    public void data(DataFrame var1, Callback var2);

    public void reset(ResetFrame var1, Callback var2);

    public Object getAttribute(String var1);

    public void setAttribute(String var1, Object var2);

    public Object removeAttribute(String var1);

    public boolean isReset();

    public boolean isClosed();

    public long getIdleTimeout();

    public void setIdleTimeout(long var1);

    public void demand(long var1);

    public static interface Listener {
        default public void onNewStream(Stream stream) {
        }

        public void onHeaders(Stream var1, HeadersFrame var2);

        public Listener onPush(Stream var1, PushPromiseFrame var2);

        default public void onBeforeData(Stream stream) {
            stream.demand(1L);
        }

        default public void onData(Stream stream, DataFrame frame, Callback callback) {
            callback.succeeded();
        }

        default public void onDataDemanded(Stream stream, DataFrame frame, Callback callback) {
            this.onData(stream, frame, callback);
            stream.demand(1L);
        }

        default public void onReset(Stream stream, ResetFrame frame, Callback callback) {
            try {
                this.onReset(stream, frame);
                callback.succeeded();
            }
            catch (Throwable x) {
                callback.failed(x);
            }
        }

        default public void onReset(Stream stream, ResetFrame frame) {
        }

        public boolean onIdleTimeout(Stream var1, Throwable var2);

        default public void onFailure(Stream stream, int error, String reason, Throwable failure, Callback callback) {
            callback.succeeded();
        }

        default public void onClosed(Stream stream) {
        }

        public static class Adapter
        implements Listener {
            @Override
            public void onHeaders(Stream stream, HeadersFrame frame) {
            }

            @Override
            public Listener onPush(Stream stream, PushPromiseFrame frame) {
                return null;
            }

            @Override
            public void onData(Stream stream, DataFrame frame, Callback callback) {
                callback.succeeded();
            }

            @Override
            public void onReset(Stream stream, ResetFrame frame) {
            }

            @Override
            public boolean onIdleTimeout(Stream stream, Throwable x) {
                return true;
            }
        }
    }
}

