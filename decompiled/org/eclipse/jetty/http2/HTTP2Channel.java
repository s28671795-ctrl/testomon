/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.util.function.Consumer;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.util.Callback;

public interface HTTP2Channel {

    public static interface Server {
        public Runnable onData(DataFrame var1, Callback var2);

        public Runnable onTrailer(HeadersFrame var1);

        public boolean onTimeout(Throwable var1, Consumer<Runnable> var2);

        public Runnable onFailure(Throwable var1, Callback var2);

        public boolean isIdle();
    }

    public static interface Client {
        public void onData(DataFrame var1, Callback var2);

        public boolean onTimeout(Throwable var1);

        public void onFailure(Throwable var1, Callback var2);
    }
}

