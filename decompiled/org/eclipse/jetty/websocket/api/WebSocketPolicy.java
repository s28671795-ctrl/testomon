/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.time.Duration;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;

public interface WebSocketPolicy {
    public WebSocketBehavior getBehavior();

    public Duration getIdleTimeout();

    public int getInputBufferSize();

    public int getOutputBufferSize();

    public long getMaxBinaryMessageSize();

    public long getMaxTextMessageSize();

    public long getMaxFrameSize();

    public boolean isAutoFragment();

    public void setIdleTimeout(Duration var1);

    public void setInputBufferSize(int var1);

    public void setOutputBufferSize(int var1);

    public void setMaxBinaryMessageSize(long var1);

    public void setMaxTextMessageSize(long var1);

    public void setMaxFrameSize(long var1);

    public void setAutoFragment(boolean var1);
}

