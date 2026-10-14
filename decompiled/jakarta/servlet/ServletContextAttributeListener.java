/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletContextAttributeEvent;
import java.util.EventListener;

public interface ServletContextAttributeListener
extends EventListener {
    default public void attributeAdded(ServletContextAttributeEvent event) {
    }

    default public void attributeRemoved(ServletContextAttributeEvent event) {
    }

    default public void attributeReplaced(ServletContextAttributeEvent event) {
    }
}

