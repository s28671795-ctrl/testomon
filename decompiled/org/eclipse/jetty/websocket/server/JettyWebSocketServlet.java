/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.Set;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.server.FrameHandlerFactory;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeRequest;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeResponse;
import org.eclipse.jetty.websocket.core.server.WebSocketCreator;
import org.eclipse.jetty.websocket.core.server.WebSocketMappings;
import org.eclipse.jetty.websocket.core.server.WebSocketServerComponents;
import org.eclipse.jetty.websocket.server.JettyWebSocketCreator;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.eclipse.jetty.websocket.server.internal.DelegatedServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.internal.DelegatedServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.internal.JettyServerFrameHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JettyWebSocketServlet
extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(JettyWebSocketServlet.class);
    private final CustomizedWebSocketServletFactory customizer = new CustomizedWebSocketServletFactory();
    private WebSocketMappings mapping;
    private WebSocketComponents components;

    protected abstract void configure(JettyWebSocketServletFactory var1);

    private FrameHandlerFactory getFactory() {
        JettyServerFrameHandlerFactory frameHandlerFactory = JettyServerFrameHandlerFactory.getFactory(this.getServletContext());
        if (frameHandlerFactory == null) {
            throw new IllegalStateException("JettyServerFrameHandlerFactory not found");
        }
        return frameHandlerFactory;
    }

    @Override
    public void init() throws ServletException {
        try {
            String autoFragment;
            ServletContext servletContext = this.getServletContext();
            this.components = WebSocketServerComponents.getWebSocketComponents(servletContext);
            this.mapping = new WebSocketMappings(this.components);
            String max = this.getInitParameter("idleTimeout");
            if (max == null && (max = this.getInitParameter("maxIdleTime")) != null) {
                LOG.warn("'maxIdleTime' init param is deprecated, use 'idleTimeout' instead");
            }
            if (max != null) {
                this.customizer.setIdleTimeout(Duration.ofMillis(Long.parseLong(max)));
            }
            if ((max = this.getInitParameter("maxTextMessageSize")) != null) {
                this.customizer.setMaxTextMessageSize(Long.parseLong(max));
            }
            if ((max = this.getInitParameter("maxBinaryMessageSize")) != null) {
                this.customizer.setMaxBinaryMessageSize(Long.parseLong(max));
            }
            if ((max = this.getInitParameter("inputBufferSize")) != null) {
                this.customizer.setInputBufferSize(Integer.parseInt(max));
            }
            if ((max = this.getInitParameter("outputBufferSize")) != null) {
                this.customizer.setOutputBufferSize(Integer.parseInt(max));
            }
            if ((max = this.getInitParameter("maxFrameSize")) == null) {
                max = this.getInitParameter("maxAllowedFrameSize");
            }
            if (max != null) {
                this.customizer.setMaxFrameSize(Long.parseLong(max));
            }
            if ((autoFragment = this.getInitParameter("autoFragment")) != null) {
                this.customizer.setAutoFragment(Boolean.parseBoolean(autoFragment));
            }
            this.configure(this.customizer);
        }
        catch (Throwable x) {
            throw new ServletException(x);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this.mapping.upgrade(req, resp, null)) {
            return;
        }
        if (resp.isCommitted()) {
            return;
        }
        super.service(req, resp);
    }

    private class CustomizedWebSocketServletFactory
    extends Configuration.ConfigurationCustomizer
    implements JettyWebSocketServletFactory {
        private CustomizedWebSocketServletFactory() {
        }

        @Override
        public Set<String> getAvailableExtensionNames() {
            return JettyWebSocketServlet.this.components.getExtensionRegistry().getAvailableExtensionNames();
        }

        @Override
        public void addMapping(String pathSpec, JettyWebSocketCreator creator) {
            JettyWebSocketServlet.this.mapping.addMapping(WebSocketMappings.parsePathSpec(pathSpec), new WrappedJettyCreator(creator), JettyWebSocketServlet.this.getFactory(), this);
        }

        @Override
        public void register(Class<?> endpointClass) {
            Constructor<?> constructor;
            try {
                constructor = endpointClass.getDeclaredConstructor(new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            JettyWebSocketCreator creator = (req, resp) -> {
                try {
                    return constructor.newInstance(new Object[0]);
                }
                catch (Throwable t2) {
                    t2.printStackTrace();
                    return null;
                }
            };
            this.addMapping("/", creator);
        }

        @Override
        public void setCreator(JettyWebSocketCreator creator) {
            this.addMapping("/", creator);
        }

        @Override
        public JettyWebSocketCreator getMapping(String pathSpec) {
            WebSocketCreator creator = JettyWebSocketServlet.this.mapping.getWebSocketCreator(WebSocketMappings.parsePathSpec(pathSpec));
            if (creator instanceof WrappedJettyCreator) {
                return ((WrappedJettyCreator)creator).getJettyWebSocketCreator();
            }
            return null;
        }

        @Override
        public boolean removeMapping(String pathSpec) {
            return JettyWebSocketServlet.this.mapping.removeMapping(WebSocketMappings.parsePathSpec(pathSpec));
        }
    }

    private static class WrappedJettyCreator
    implements WebSocketCreator {
        private final JettyWebSocketCreator creator;

        private WrappedJettyCreator(JettyWebSocketCreator creator) {
            this.creator = creator;
        }

        private JettyWebSocketCreator getJettyWebSocketCreator() {
            return this.creator;
        }

        @Override
        public Object createWebSocket(ServerUpgradeRequest req, ServerUpgradeResponse resp) {
            return this.creator.createWebSocket(new DelegatedServerUpgradeRequest(req), new DelegatedServerUpgradeResponse(resp));
        }
    }
}

