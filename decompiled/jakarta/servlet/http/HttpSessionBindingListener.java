/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.http.HttpSessionBindingEvent;
import java.util.EventListener;

public interface HttpSessionBindingListener
extends EventListener {
    default public void valueBound(HttpSessionBindingEvent event) {
    }

    default public void valueUnbound(HttpSessionBindingEvent event) {
    }
}

