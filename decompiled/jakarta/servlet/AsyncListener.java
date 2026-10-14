/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.AsyncEvent;
import java.io.IOException;
import java.util.EventListener;

public interface AsyncListener
extends EventListener {
    public void onComplete(AsyncEvent var1) throws IOException;

    public void onTimeout(AsyncEvent var1) throws IOException;

    public void onError(AsyncEvent var1) throws IOException;

    public void onStartAsync(AsyncEvent var1) throws IOException;
}

