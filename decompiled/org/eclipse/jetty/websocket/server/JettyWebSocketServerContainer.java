/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketContainer;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.api.WebSocketSessionListener;
import org.eclipse.jetty.websocket.common.SessionTracker;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.exception.WebSocketException;
import org.eclipse.jetty.websocket.core.internal.util.ReflectUtils;
import org.eclipse.jetty.websocket.core.server.Handshaker;
import org.eclipse.jetty.websocket.core.server.WebSocketCreator;
import org.eclipse.jetty.websocket.core.server.WebSocketMappings;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiator;
import org.eclipse.jetty.websocket.core.server.WebSocketServerComponents;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketCreator;
import org.eclipse.jetty.websocket.server.internal.DelegatedServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.internal.DelegatedServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.internal.JettyServerFrameHandlerFactory;
import org.eclipse.jetty.websocket.servlet.WebSocketUpgradeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebSocketServerContainer
extends ContainerLifeCycle
implements WebSocketContainer,
WebSocketPolicy,
LifeCycle.Listener {
    public static final String JETTY_WEBSOCKET_CONTAINER_ATTRIBUTE = WebSocketContainer.class.getName();
    private static final Logger LOG = LoggerFactory.getLogger(JettyWebSocketServerContainer.class);
    private final ServletContextHandler contextHandler;
    private final WebSocketMappings webSocketMappings;
    private final WebSocketComponents components;
    private final JettyServerFrameHandlerFactory frameHandlerFactory;
    private final Executor executor;
    private final Configuration.ConfigurationCustomizer customizer = new Configuration.ConfigurationCustomizer();
    private final List<WebSocketSessionListener> sessionListeners = new ArrayList<WebSocketSessionListener>();
    private final SessionTracker sessionTracker = new SessionTracker();

    public static JettyWebSocketServerContainer getContainer(ServletContext servletContext) {
        return (JettyWebSocketServerContainer)servletContext.getAttribute(JETTY_WEBSOCKET_CONTAINER_ATTRIBUTE);
    }

    public static JettyWebSocketServerContainer ensureContainer(ServletContext servletContext) {
        final ServletContextHandler contextHandler = ServletContextHandler.getServletContextHandler(servletContext, "Jakarta Websocket");
        if (contextHandler.getServer() == null) {
            throw new IllegalStateException("Server has not been set on the ServletContextHandler");
        }
        JettyWebSocketServerContainer containerFromServletContext = JettyWebSocketServerContainer.getContainer(servletContext);
        if (containerFromServletContext != null) {
            return containerFromServletContext;
        }
        Executor executor2 = (Executor)servletContext.getAttribute("org.eclipse.jetty.server.Executor");
        if (executor2 == null) {
            executor2 = contextHandler.getServer().getThreadPool();
        }
        WebSocketMappings mappings = WebSocketMappings.ensureMappings(servletContext);
        WebSocketComponents components = WebSocketServerComponents.getWebSocketComponents(servletContext);
        final JettyWebSocketServerContainer container = new JettyWebSocketServerContainer(contextHandler, mappings, components, executor2);
        contextHandler.addManaged(container);
        contextHandler.addEventListener(container);
        contextHandler.addEventListener(new LifeCycle.Listener(){

            @Override
            public void lifeCycleStopping(LifeCycle event) {
                contextHandler.getServletContext().removeAttribute(JETTY_WEBSOCKET_CONTAINER_ATTRIBUTE);
                contextHandler.removeBean(container);
                contextHandler.removeEventListener(container);
                contextHandler.removeEventListener(this);
            }

            public String toString() {
                return String.format("%sCleanupListener", JettyWebSocketServerContainer.class.getSimpleName());
            }
        });
        servletContext.setAttribute(JETTY_WEBSOCKET_CONTAINER_ATTRIBUTE, container);
        return container;
    }

    JettyWebSocketServerContainer(ServletContextHandler contextHandler, WebSocketMappings webSocketMappings, WebSocketComponents components, Executor executor2) {
        this.contextHandler = contextHandler;
        this.webSocketMappings = webSocketMappings;
        this.components = components;
        this.executor = executor2;
        this.frameHandlerFactory = new JettyServerFrameHandlerFactory(this, components);
        this.addBean(this.frameHandlerFactory);
        this.addSessionListener(this.sessionTracker);
        this.addBean(this.sessionTracker);
    }

    public void addMapping(String pathSpec, JettyWebSocketCreator creator) {
        PathSpec ps = WebSocketMappings.parsePathSpec(pathSpec);
        if (this.webSocketMappings.getWebSocketNegotiator(ps) != null) {
            throw new WebSocketException("Duplicate WebSocket Mapping for PathSpec");
        }
        WebSocketUpgradeFilter.ensureFilter(this.contextHandler.getServletContext());
        WebSocketCreator coreCreator = (req, resp) -> creator.createWebSocket(new DelegatedServerUpgradeRequest(req), new DelegatedServerUpgradeResponse(resp));
        this.webSocketMappings.addMapping(ps, coreCreator, this.frameHandlerFactory, this.customizer);
    }

    public void addMapping(String pathSpec, Class<?> endpointClass) {
        if (!ReflectUtils.isDefaultConstructable(endpointClass)) {
            throw new IllegalArgumentException("Cannot access default constructor for the class: " + endpointClass.getName());
        }
        this.addMapping(pathSpec, (JettyServerUpgradeRequest req, JettyServerUpgradeResponse resp) -> {
            try {
                return endpointClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception e) {
                throw new org.eclipse.jetty.websocket.api.exceptions.WebSocketException("Unable to create instance of " + endpointClass.getName(), e);
            }
        });
    }

    public boolean upgrade(JettyWebSocketCreator creator, HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebSocketCreator coreCreator = (req, resp) -> creator.createWebSocket(new DelegatedServerUpgradeRequest(req), new DelegatedServerUpgradeResponse(resp));
        WebSocketNegotiator negotiator = WebSocketNegotiator.from(coreCreator, this.frameHandlerFactory, this.customizer);
        Handshaker handshaker = this.webSocketMappings.getHandshaker();
        return handshaker.upgradeRequest(negotiator, request, response, this.components, null);
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }

    @Override
    public void addSessionListener(WebSocketSessionListener listener) {
        this.sessionListeners.add(listener);
    }

    @Override
    public boolean removeSessionListener(WebSocketSessionListener listener) {
        return this.sessionListeners.remove(listener);
    }

    @Override
    public void notifySessionListeners(Consumer<WebSocketSessionListener> consumer) {
        for (WebSocketSessionListener listener : this.sessionListeners) {
            try {
                consumer.accept(listener);
            }
            catch (Throwable x) {
                LOG.info("Exception while invoking listener {}", (Object)listener, (Object)x);
            }
        }
    }

    @Override
    public Collection<Session> getOpenSessions() {
        return this.sessionTracker.getSessions();
    }

    @Override
    public WebSocketBehavior getBehavior() {
        return WebSocketBehavior.SERVER;
    }

    @Override
    public Duration getIdleTimeout() {
        return this.customizer.getIdleTimeout();
    }

    @Override
    public int getInputBufferSize() {
        return this.customizer.getInputBufferSize();
    }

    @Override
    public int getOutputBufferSize() {
        return this.customizer.getOutputBufferSize();
    }

    @Override
    public long getMaxBinaryMessageSize() {
        return this.customizer.getMaxBinaryMessageSize();
    }

    @Override
    public long getMaxTextMessageSize() {
        return this.customizer.getMaxTextMessageSize();
    }

    @Override
    public long getMaxFrameSize() {
        return this.customizer.getMaxFrameSize();
    }

    @Override
    public boolean isAutoFragment() {
        return this.customizer.isAutoFragment();
    }

    @Override
    public void setIdleTimeout(Duration duration) {
        this.customizer.setIdleTimeout(duration);
    }

    @Override
    public void setInputBufferSize(int size) {
        this.customizer.setInputBufferSize(size);
    }

    @Override
    public void setOutputBufferSize(int size) {
        this.customizer.setOutputBufferSize(size);
    }

    @Override
    public void setMaxBinaryMessageSize(long size) {
        this.customizer.setMaxBinaryMessageSize(size);
    }

    @Override
    public void setMaxTextMessageSize(long size) {
        this.customizer.setMaxTextMessageSize(size);
    }

    @Override
    public void setMaxFrameSize(long maxFrameSize) {
        this.customizer.setMaxFrameSize(maxFrameSize);
    }

    @Override
    public void setAutoFragment(boolean autoFragment) {
        this.customizer.setAutoFragment(autoFragment);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, this.customizer);
    }
}

