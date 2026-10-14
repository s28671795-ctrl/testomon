/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.http.HttpSessionEvent;
import java.util.EventListener;

public interface HttpSessionActivationListener
extends EventListener {
    default public void sessionWillPassivate(HttpSessionEvent se) {
    }

    default public void sessionDidActivate(HttpSessionEvent se) {
    }
}

