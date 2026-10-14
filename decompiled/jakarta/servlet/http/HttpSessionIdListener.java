/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.http.HttpSessionEvent;
import java.util.EventListener;

public interface HttpSessionIdListener
extends EventListener {
    public void sessionIdChanged(HttpSessionEvent var1, String var2);
}

