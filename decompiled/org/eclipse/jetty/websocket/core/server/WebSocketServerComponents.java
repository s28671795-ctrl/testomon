/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import jakarta.servlet.ServletContext;
import java.util.Objects;
import java.util.concurrent.Executor;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.DecoratedObjectFactory;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.compression.DeflaterPool;
import org.eclipse.jetty.util.compression.InflaterPool;
import org.eclipse.jetty.websocket.core.WebSocketComponents;

public class WebSocketServerComponents
extends WebSocketComponents {
    public static final String WEBSOCKET_COMPONENTS_ATTRIBUTE = WebSocketComponents.class.getName();
    public static final String WEBSOCKET_INFLATER_POOL_ATTRIBUTE = "jetty.websocket.inflater";
    public static final String WEBSOCKET_DEFLATER_POOL_ATTRIBUTE = "jetty.websocket.deflater";
    public static final String WEBSOCKET_BUFFER_POOL_ATTRIBUTE = "jetty.websocket.bufferPool";

    WebSocketServerComponents(InflaterPool inflaterPool, DeflaterPool deflaterPool, ByteBufferPool bufferPool, DecoratedObjectFactory objectFactory, Executor executor2) {
        super(null, objectFactory, bufferPool, inflaterPool, deflaterPool, executor2);
    }

    public static WebSocketComponents ensureWebSocketComponents(Server server, final ServletContext servletContext) {
        Executor executor2;
        ByteBufferPool bufferPool;
        DeflaterPool deflaterPool;
        WebSocketComponents components = (WebSocketComponents)servletContext.getAttribute(WEBSOCKET_COMPONENTS_ATTRIBUTE);
        if (components != null) {
            return components;
        }
        InflaterPool inflaterPool = (InflaterPool)servletContext.getAttribute(WEBSOCKET_INFLATER_POOL_ATTRIBUTE);
        if (inflaterPool == null) {
            inflaterPool = InflaterPool.ensurePool(server);
        }
        if ((deflaterPool = (DeflaterPool)servletContext.getAttribute(WEBSOCKET_DEFLATER_POOL_ATTRIBUTE)) == null) {
            deflaterPool = DeflaterPool.ensurePool(server);
        }
        if ((bufferPool = (ByteBufferPool)servletContext.getAttribute(WEBSOCKET_BUFFER_POOL_ATTRIBUTE)) == null) {
            bufferPool = server.getBean(ByteBufferPool.class);
        }
        if ((executor2 = (Executor)servletContext.getAttribute("org.eclipse.jetty.server.Executor")) == null) {
            executor2 = server.getThreadPool();
        }
        DecoratedObjectFactory objectFactory = (DecoratedObjectFactory)servletContext.getAttribute(DecoratedObjectFactory.ATTR);
        final WebSocketServerComponents serverComponents = new WebSocketServerComponents(inflaterPool, deflaterPool, bufferPool, objectFactory, executor2);
        if (objectFactory != null) {
            serverComponents.unmanage(objectFactory);
        }
        if (server.contains(inflaterPool)) {
            serverComponents.unmanage(inflaterPool);
        }
        if (server.contains(deflaterPool)) {
            serverComponents.unmanage(deflaterPool);
        }
        if (server.contains(bufferPool)) {
            serverComponents.unmanage(bufferPool);
        }
        if (executor2 != null) {
            serverComponents.unmanage(executor2);
        }
        final ContextHandler contextHandler = Objects.requireNonNull(ContextHandler.getContextHandler(servletContext));
        contextHandler.addManaged(serverComponents);
        contextHandler.addEventListener(new LifeCycle.Listener(){

            @Override
            public void lifeCycleStopping(LifeCycle event) {
                servletContext.removeAttribute(WEBSOCKET_COMPONENTS_ATTRIBUTE);
                contextHandler.removeBean(serverComponents);
                contextHandler.removeEventListener(this);
            }

            public String toString() {
                return String.format("%sCleanupListener", WebSocketServerComponents.class.getSimpleName());
            }
        });
        servletContext.setAttribute(WEBSOCKET_COMPONENTS_ATTRIBUTE, serverComponents);
        return serverComponents;
    }

    public static WebSocketComponents getWebSocketComponents(ServletContext servletContext) {
        WebSocketComponents components = (WebSocketComponents)servletContext.getAttribute(WEBSOCKET_COMPONENTS_ATTRIBUTE);
        if (components == null) {
            throw new IllegalStateException("WebSocketComponents has not been created");
        }
        return components;
    }
}

