/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Promise;

public interface ISession
extends Session {
    @Override
    public IStream getStream(int var1);

    public boolean removeStream(IStream var1);

    public void newStream(IStream.FrameList var1, Promise<Stream> var2, Stream.Listener var3);

    public void frames(IStream var1, List<? extends Frame> var2, Callback var3);

    public void push(IStream var1, Promise<Stream> var2, PushPromiseFrame var3, Stream.Listener var4);

    public void data(IStream var1, Callback var2, DataFrame var3);

    public int updateSendWindow(int var1);

    public int updateRecvWindow(int var1);

    public void onWindowUpdate(IStream var1, WindowUpdateFrame var2);

    public boolean isPushEnabled();

    public void onShutdown();

    public boolean onIdleTimeout();

    public void onFrame(Frame var1);

    public void onFlushed(long var1) throws IOException;

    public long getBytesWritten();

    public void onData(DataFrame var1, Callback var2);

    public CompletableFuture<Void> shutdown();
}

