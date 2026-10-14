/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.util.thread.AutoLock;

public interface ContentProducer {
    public AutoLock lock();

    public void recycle();

    public void reopen();

    public boolean consumeAll();

    public void checkMinDataRate();

    public long getRawContentArrived();

    public int available();

    public boolean hasContent();

    public boolean isError();

    public HttpInput.Content nextContent();

    public void reclaim(HttpInput.Content var1);

    public boolean isReady();

    public HttpInput.Interceptor getInterceptor();

    public void setInterceptor(HttpInput.Interceptor var1);

    public boolean onContentProducible();
}

