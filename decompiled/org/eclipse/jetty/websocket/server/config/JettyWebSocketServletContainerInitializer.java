/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server.config;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import java.util.Set;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.WebSocketServerComponents;
import org.eclipse.jetty.websocket.server.JettyWebSocketServerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebSocketServletContainerInitializer
implements ServletContainerInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(JettyWebSocketServletContainerInitializer.class);
    private final Configurator configurator;

    public JettyWebSocketServletContainerInitializer() {
        this(null);
    }

    public JettyWebSocketServletContainerInitializer(Configurator configurator2) {
        this.configurator = configurator2;
    }

    public static void configure(ServletContextHandler context, Configurator configurator2) {
        if (!context.isStopped()) {
            throw new IllegalStateException("configure should be called before starting");
        }
        context.addServletContainerInitializer(new JettyWebSocketServletContainerInitializer(configurator2));
    }

    private static JettyWebSocketServerContainer initialize(ServletContextHandler context) {
        WebSocketComponents components = WebSocketServerComponents.ensureWebSocketComponents(context.getServer(), context.getServletContext());
        JettyWebSocketServerContainer container = JettyWebSocketServerContainer.ensureContainer(context.getServletContext());
        if (LOG.isDebugEnabled()) {
            LOG.debug("initialize {} {}", (Object)container, (Object)components);
        }
        return container;
    }

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext context) {
        ServletContextHandler contextHandler = ServletContextHandler.getServletContextHandler(context, "Jetty WebSocket SCI");
        JettyWebSocketServerContainer container = JettyWebSocketServletContainerInitializer.initialize(contextHandler);
        if (LOG.isDebugEnabled()) {
            LOG.debug("onStartup {}", (Object)container);
        }
        if (this.configurator != null) {
            this.configurator.accept(context, container);
        }
    }

    public static interface Configurator {
        public void accept(ServletContext var1, JettyWebSocketServerContainer var2);
    }
}

