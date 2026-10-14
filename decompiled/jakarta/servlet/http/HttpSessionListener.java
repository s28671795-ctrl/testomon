/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.http.HttpSessionEvent;
import java.util.EventListener;

public interface HttpSessionListener
extends EventListener {
    default public void sessionCreated(HttpSessionEvent se) {
    }

    default public void sessionDestroyed(HttpSessionEvent se) {
    }
}

