/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.servlet;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Objects;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.server.WebSocketMappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="WebSocket Upgrade Filter")
public class WebSocketUpgradeFilter
implements Filter,
Dumpable {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketUpgradeFilter.class);
    private static final AutoLock LOCK = new AutoLock();
    private final Configuration.ConfigurationCustomizer defaultCustomizer = new Configuration.ConfigurationCustomizer();
    private WebSocketMappings mappings;

    public static FilterHolder getFilter(ServletContext servletContext) {
        ContextHandler contextHandler = Objects.requireNonNull(ContextHandler.getContextHandler(servletContext));
        ServletHandler servletHandler = contextHandler.getChildHandlerByClass(ServletHandler.class);
        return servletHandler.getFilter(WebSocketUpgradeFilter.class.getName());
    }

    public static FilterHolder ensureFilter(ServletContext servletContext) {
        try (AutoLock l = LOCK.lock();){
            FilterHolder existingFilter = WebSocketUpgradeFilter.getFilter(servletContext);
            if (existingFilter != null) {
                FilterHolder filterHolder = existingFilter;
                return filterHolder;
            }
            final ContextHandler contextHandler = Objects.requireNonNull(ContextHandler.getContextHandler(servletContext));
            final ServletHandler servletHandler = contextHandler.getChildHandlerByClass(ServletHandler.class);
            String pathSpec = "/*";
            final FilterHolder holder = new FilterHolder(new WebSocketUpgradeFilter());
            holder.setName(WebSocketUpgradeFilter.class.getName());
            holder.setAsyncSupported(true);
            final FilterMapping mapping = new FilterMapping();
            mapping.setFilterName(holder.getName());
            mapping.setPathSpec("/*");
            mapping.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
            servletHandler.prependFilter(holder);
            servletHandler.prependFilterMapping(mapping);
            contextHandler.addEventListener(new LifeCycle.Listener(){

                @Override
                public void lifeCycleStopping(LifeCycle event) {
                    servletHandler.removeFilterHolder(holder);
                    servletHandler.removeFilterMapping(mapping);
                    contextHandler.removeEventListener(this);
                }

                public String toString() {
                    return String.format("%sCleanupListener", WebSocketUpgradeFilter.class.getSimpleName());
                }
            });
            if (LOG.isDebugEnabled()) {
                LOG.debug("Adding {} mapped to {} in {}", holder, "/*", servletContext);
            }
            FilterHolder filterHolder = holder;
            return filterHolder;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpreq = (HttpServletRequest)request;
        HttpServletResponse httpresp = (HttpServletResponse)response;
        if (this.mappings.upgrade(httpreq, httpresp, this.defaultCustomizer)) {
            return;
        }
        if (response.isCommitted()) {
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, this.defaultCustomizer, this.mappings);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        String autoFragment;
        this.mappings = WebSocketMappings.ensureMappings(config.getServletContext());
        String max = config.getInitParameter("idleTimeout");
        if (max == null && (max = config.getInitParameter("maxIdleTime")) != null) {
            LOG.warn("'maxIdleTime' init param is deprecated, use 'idleTimeout' instead");
        }
        if (max != null) {
            this.defaultCustomizer.setIdleTimeout(Duration.ofMillis(Long.parseLong(max)));
        }
        if ((max = config.getInitParameter("maxTextMessageSize")) != null) {
            this.defaultCustomizer.setMaxTextMessageSize(Integer.parseInt(max));
        }
        if ((max = config.getInitParameter("maxBinaryMessageSize")) != null) {
            this.defaultCustomizer.setMaxBinaryMessageSize(Integer.parseInt(max));
        }
        if ((max = config.getInitParameter("inputBufferSize")) != null) {
            this.defaultCustomizer.setInputBufferSize(Integer.parseInt(max));
        }
        if ((max = config.getInitParameter("outputBufferSize")) != null) {
            this.defaultCustomizer.setOutputBufferSize(Integer.parseInt(max));
        }
        if ((max = config.getInitParameter("maxFrameSize")) == null) {
            max = config.getInitParameter("maxAllowedFrameSize");
        }
        if (max != null) {
            this.defaultCustomizer.setMaxFrameSize(Long.parseLong(max));
        }
        if ((autoFragment = config.getInitParameter("autoFragment")) != null) {
            this.defaultCustomizer.setAutoFragment(Boolean.parseBoolean(autoFragment));
        }
    }

    @Override
    public void destroy() {
    }
}

