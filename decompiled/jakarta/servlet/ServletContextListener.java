/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletContextEvent;
import java.util.EventListener;

public interface ServletContextListener
extends EventListener {
    default public void contextInitialized(ServletContextEvent sce) {
    }

    default public void contextDestroyed(ServletContextEvent sce) {
    }
}

