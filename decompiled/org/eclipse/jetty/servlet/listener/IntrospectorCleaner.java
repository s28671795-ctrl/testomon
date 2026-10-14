/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.beans.Introspector;

public class IntrospectorCleaner
implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Introspector.flushCaches();
    }
}

