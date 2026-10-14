/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletRequestEvent;
import java.util.EventListener;

public interface ServletRequestListener
extends EventListener {
    default public void requestDestroyed(ServletRequestEvent sre) {
    }

    default public void requestInitialized(ServletRequestEvent sre) {
    }
}

