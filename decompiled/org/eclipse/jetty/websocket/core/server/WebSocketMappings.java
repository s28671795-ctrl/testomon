/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;
import org.eclipse.jetty.http.pathmap.MatchedResource;
import org.eclipse.jetty.http.pathmap.PathMappings;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.http.pathmap.RegexPathSpec;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;
import org.eclipse.jetty.http.pathmap.UriTemplatePathSpec;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.exception.WebSocketException;
import org.eclipse.jetty.websocket.core.server.FrameHandlerFactory;
import org.eclipse.jetty.websocket.core.server.Handshaker;
import org.eclipse.jetty.websocket.core.server.WebSocketCreator;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiator;
import org.eclipse.jetty.websocket.core.server.WebSocketServerComponents;
import org.eclipse.jetty.websocket.core.server.internal.CreatorNegotiator;
import org.eclipse.jetty.websocket.core.server.internal.HandshakerSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketMappings
implements Dumpable,
LifeCycle.Listener {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketMappings.class);
    public static final String WEBSOCKET_MAPPING_ATTRIBUTE = WebSocketMappings.class.getName();
    private final PathMappings<WebSocketNegotiator> mappings = new PathMappings();
    private final WebSocketComponents components;
    private final Handshaker handshaker = new HandshakerSelector();

    public static WebSocketMappings getMappings(ServletContext servletContext) {
        return (WebSocketMappings)servletContext.getAttribute(WEBSOCKET_MAPPING_ATTRIBUTE);
    }

    public static WebSocketMappings ensureMappings(ServletContext servletContext) {
        WebSocketMappings mapping = WebSocketMappings.getMappings(servletContext);
        if (mapping == null) {
            mapping = new WebSocketMappings(WebSocketServerComponents.getWebSocketComponents(servletContext));
            servletContext.setAttribute(WEBSOCKET_MAPPING_ATTRIBUTE, mapping);
        }
        return mapping;
    }

    public static PathSpec parsePathSpec(String rawSpec) {
        if (rawSpec.charAt(0) == '/' || rawSpec.startsWith("*.") || rawSpec.startsWith("servlet|")) {
            return new ServletPathSpec(rawSpec);
        }
        if (rawSpec.charAt(0) == '^' || rawSpec.startsWith("regex|")) {
            return new RegexPathSpec(rawSpec);
        }
        if (rawSpec.startsWith("uri-template|")) {
            return new UriTemplatePathSpec(rawSpec.substring("uri-template|".length()));
        }
        throw new IllegalArgumentException("Unrecognized path spec syntax [" + rawSpec + "]");
    }

    public WebSocketMappings() {
        this(new WebSocketComponents());
    }

    public WebSocketMappings(WebSocketComponents components) {
        this.components = components;
    }

    public Handshaker getHandshaker() {
        return this.handshaker;
    }

    @Override
    public void lifeCycleStopping(LifeCycle context) {
        ContextHandler contextHandler = (ContextHandler)context;
        WebSocketMappings mapping = contextHandler.getBean(WebSocketMappings.class);
        if (mapping == this) {
            contextHandler.removeBean(mapping);
            this.mappings.reset();
        }
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, this.mappings);
    }

    public WebSocketNegotiator getWebSocketNegotiator(PathSpec pathSpec) {
        return this.mappings.get(pathSpec);
    }

    public WebSocketCreator getWebSocketCreator(PathSpec pathSpec) {
        WebSocketNegotiator negotiator = this.getWebSocketNegotiator(pathSpec);
        if (negotiator instanceof CreatorNegotiator) {
            return ((CreatorNegotiator)negotiator).getWebSocketCreator();
        }
        return null;
    }

    public void addMapping(PathSpec pathSpec, WebSocketCreator creator, FrameHandlerFactory factory2, Configuration.Customizer customizer) throws WebSocketException {
        this.mappings.put(pathSpec, WebSocketNegotiator.from(creator, factory2, customizer));
    }

    public void addMapping(PathSpec pathSpec, WebSocketNegotiator negotiator) throws WebSocketException {
        this.mappings.put(pathSpec, negotiator);
    }

    public boolean removeMapping(PathSpec pathSpec) {
        return this.mappings.remove(pathSpec);
    }

    public WebSocketNegotiator getMatchedNegotiator(String target, Consumer<PathSpec> pathSpecConsumer) {
        MatchedResource<WebSocketNegotiator> mapping = this.mappings.getMatched(target);
        if (mapping == null) {
            return null;
        }
        pathSpecConsumer.accept(mapping.getPathSpec());
        return mapping.getResource();
    }

    public boolean upgrade(HttpServletRequest request, HttpServletResponse response, Configuration.Customizer defaultCustomizer) throws IOException {
        String target = URIUtil.addPaths(request.getServletPath(), request.getPathInfo());
        WebSocketNegotiator negotiator = this.getMatchedNegotiator(target, pathSpec -> request.setAttribute(PathSpec.class.getName(), pathSpec));
        if (negotiator == null) {
            return false;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("WebSocket Negotiated detected on {} for endpoint {}", (Object)target, (Object)negotiator);
        }
        return this.handshaker.upgradeRequest(negotiator, request, response, this.components, defaultCustomizer);
    }
}

