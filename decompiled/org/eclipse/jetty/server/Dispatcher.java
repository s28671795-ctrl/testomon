/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.UriCompliance;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.ServletPathMapping;
import org.eclipse.jetty.server.ServletRequestHttpWrapper;
import org.eclipse.jetty.server.ServletResponseHttpWrapper;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher
implements RequestDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);
    public static final String __INCLUDE_PREFIX = "jakarta.servlet.include.";
    public static final String __FORWARD_PREFIX = "jakarta.servlet.forward.";
    private final ContextHandler _contextHandler;
    private final HttpURI _uri;
    private final String _pathInContext;
    private final String _named;

    public Dispatcher(ContextHandler contextHandler, HttpURI uri, String pathInContext) {
        this._contextHandler = contextHandler;
        this._uri = uri.asImmutable();
        this._pathInContext = pathInContext;
        this._named = null;
    }

    public Dispatcher(ContextHandler contextHandler, String name) throws IllegalStateException {
        this._contextHandler = contextHandler;
        this._uri = null;
        this._pathInContext = null;
        this._named = name;
    }

    public void error(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        this.forward(request, response, DispatcherType.ERROR);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        Request baseRequest = Objects.requireNonNull(Request.getBaseRequest(request));
        if (!(request instanceof HttpServletRequest)) {
            request = new ServletRequestHttpWrapper(request);
        }
        if (!(response instanceof HttpServletResponse)) {
            response = new ServletResponseHttpWrapper(response);
        }
        DispatcherType old_type = baseRequest.getDispatcherType();
        Attributes old_attr = baseRequest.getAttributes();
        MultiMap<String> old_query_params = baseRequest.getQueryParameters();
        ContextHandler.Context old_context = baseRequest.getContext();
        ServletPathMapping old_mapping = baseRequest.getServletPathMapping();
        try {
            baseRequest.setDispatcherType(DispatcherType.INCLUDE);
            baseRequest.getResponse().include();
            if (this._named != null) {
                this._contextHandler.handle(this._named, baseRequest, (HttpServletRequest)request, (HttpServletResponse)response);
            } else {
                Objects.requireNonNull(this._uri);
                Dispatcher.checkUriViolations(this._uri, baseRequest);
                IncludeAttributes attr = new IncludeAttributes(old_attr, baseRequest, old_context, old_mapping, this._uri.getPath(), this._pathInContext, this._uri.getQuery());
                if (attr._query != null) {
                    baseRequest.mergeQueryParameters(baseRequest.getQueryString(), attr._query);
                }
                baseRequest.setAttributes(attr);
                this._contextHandler.handle(this._pathInContext, baseRequest, (HttpServletRequest)request, (HttpServletResponse)response);
            }
        }
        finally {
            baseRequest.setAttributes(old_attr);
            baseRequest.getResponse().included();
            baseRequest.setQueryParameters(old_query_params);
            baseRequest.resetParameters();
            baseRequest.setDispatcherType(old_type);
        }
    }

    @Override
    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        this.forward(request, response, DispatcherType.FORWARD);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void forward(ServletRequest request, ServletResponse response, DispatcherType dispatch) throws ServletException, IOException {
        block15: {
            Request baseRequest = Objects.requireNonNull(Request.getBaseRequest(request));
            Response baseResponse = baseRequest.getResponse();
            baseResponse.resetForForward();
            if (!(request instanceof HttpServletRequest)) {
                request = new ServletRequestHttpWrapper(request);
            }
            if (!(response instanceof HttpServletResponse)) {
                response = new ServletResponseHttpWrapper(response);
            }
            HttpURI old_uri = baseRequest.getHttpURI();
            ContextHandler.Context old_context = baseRequest.getContext();
            String old_path_in_context = baseRequest.getPathInContext();
            ServletPathMapping old_mapping = baseRequest.getServletPathMapping();
            ServletPathMapping source_mapping = baseRequest.findServletPathMapping();
            MultiMap<String> old_query_params = baseRequest.getQueryParameters();
            Attributes old_attr = baseRequest.getAttributes();
            DispatcherType old_type = baseRequest.getDispatcherType();
            try {
                String query;
                baseRequest.setDispatcherType(dispatch);
                if (this._named != null) {
                    this._contextHandler.handle(this._named, baseRequest, (HttpServletRequest)request, (HttpServletResponse)response);
                    break block15;
                }
                Objects.requireNonNull(this._uri);
                Dispatcher.checkUriViolations(this._uri, baseRequest);
                if (old_attr.getAttribute("jakarta.servlet.forward.request_uri") == null) {
                    baseRequest.setAttributes(new ForwardAttributes(old_attr, old_uri.getPath(), baseRequest.getContextPath(), baseRequest.getPathInContext(), source_mapping, old_uri.getQuery()));
                }
                if ((query = this._uri.getQuery()) == null) {
                    query = old_uri.getQuery();
                }
                baseRequest.setHttpURI(HttpURI.build(old_uri, this._uri.getPath(), this._uri.getParam(), query));
                baseRequest.setContext(this._contextHandler.getServletContext(), this._pathInContext);
                baseRequest.setServletPathMapping(null);
                if (this._uri.getQuery() != null || old_uri.getQuery() != null) {
                    try {
                        baseRequest.mergeQueryParameters(old_uri.getQuery(), this._uri.getQuery());
                    }
                    catch (BadMessageException e) {
                        if (dispatch != DispatcherType.ERROR) {
                            throw e;
                        }
                        LOG.warn("Ignoring Original Bad Request Query String: {}", (Object)old_uri, (Object)e);
                    }
                }
                this._contextHandler.handle(this._pathInContext, baseRequest, (HttpServletRequest)request, (HttpServletResponse)response);
                if (!baseRequest.getHttpChannelState().isAsync() && !baseResponse.getHttpOutput().isClosed()) {
                    try {
                        response.getOutputStream().close();
                    }
                    catch (IllegalStateException e) {
                        response.getWriter().close();
                    }
                }
            }
            finally {
                baseRequest.setHttpURI(old_uri);
                baseRequest.setContext(old_context, old_path_in_context);
                baseRequest.setServletPathMapping(old_mapping);
                baseRequest.setQueryParameters(old_query_params);
                baseRequest.resetParameters();
                baseRequest.setAttributes(old_attr);
                baseRequest.setDispatcherType(old_type);
            }
        }
    }

    private static void checkUriViolations(HttpURI uri, Request baseRequest) {
        HttpChannel channel;
        UriCompliance compliance;
        String illegalState;
        if (uri.hasViolations() && (illegalState = UriCompliance.checkUriCompliance(compliance = (channel = baseRequest.getHttpChannel()) == null || channel.getHttpConfiguration() == null ? null : channel.getHttpConfiguration().getUriCompliance(), uri)) != null) {
            throw new IllegalStateException(illegalState);
        }
    }

    public String toString() {
        return String.format("Dispatcher@0x%x{%s,%s}", this.hashCode(), this._named, this._uri);
    }

    class IncludeAttributes
    extends Attributes.Wrapper {
        private final Request _baseRequest;
        private final ContextHandler.Context _sourceContext;
        private final ServletPathMapping _sourceMapping;
        private final String _requestURI;
        private final String _pathInContext;
        private final String _query;

        public IncludeAttributes(Attributes attributes, Request baseRequest, ContextHandler.Context sourceContext, ServletPathMapping sourceMapping, String requestURI, String pathInContext, String query) {
            super(attributes);
            this._baseRequest = baseRequest;
            this._sourceMapping = sourceMapping;
            this._requestURI = requestURI;
            this._sourceContext = sourceContext;
            this._pathInContext = pathInContext;
            this._query = query;
        }

        ContextHandler.Context getSourceContext() {
            return this._sourceContext;
        }

        ServletPathMapping getSourceMapping() {
            return this._sourceMapping;
        }

        @Override
        public Object getAttribute(String key) {
            if (Dispatcher.this._named == null) {
                switch (key) {
                    case "jakarta.servlet.include.path_info": {
                        ServletPathMapping mapping = this._baseRequest.getServletPathMapping();
                        return mapping == null ? this._pathInContext : mapping.getPathInfo();
                    }
                    case "jakarta.servlet.include.servlet_path": {
                        ServletPathMapping mapping = this._baseRequest.getServletPathMapping();
                        return mapping == null ? null : mapping.getServletPath();
                    }
                    case "jakarta.servlet.include.context_path": {
                        ContextHandler.Context context = this._baseRequest.getContext();
                        return context == null ? null : context.getContextHandler().getRequestContextPath();
                    }
                    case "jakarta.servlet.include.query_string": {
                        return this._query;
                    }
                    case "jakarta.servlet.include.request_uri": {
                        return this._requestURI;
                    }
                    case "jakarta.servlet.include.mapping": {
                        return this._baseRequest.getServletPathMapping();
                    }
                }
            }
            return this._attributes.getAttribute(key);
        }

        @Override
        public Set<String> getAttributeNameSet() {
            HashSet<String> set = new HashSet<String>();
            for (String name : this._attributes.getAttributeNameSet()) {
                if (name.startsWith(Dispatcher.__INCLUDE_PREFIX)) continue;
                set.add(name);
            }
            String pathInfo = (String)this.getAttribute("jakarta.servlet.include.path_info");
            String servletPath = (String)this.getAttribute("jakarta.servlet.include.servlet_path");
            String contextPath = (String)this.getAttribute("jakarta.servlet.include.context_path");
            HttpServletMapping includeMapping = (HttpServletMapping)this.getAttribute("jakarta.servlet.include.mapping");
            if (Dispatcher.this._named == null) {
                if (pathInfo != null) {
                    set.add("jakarta.servlet.include.path_info");
                }
                if (this._requestURI != null) {
                    set.add("jakarta.servlet.include.request_uri");
                }
                if (servletPath != null) {
                    set.add("jakarta.servlet.include.servlet_path");
                }
                if (contextPath != null) {
                    set.add("jakarta.servlet.include.context_path");
                }
                if (includeMapping != null) {
                    set.add("jakarta.servlet.include.mapping");
                }
                if (this._query != null) {
                    set.add("jakarta.servlet.include.query_string");
                }
            }
            return set;
        }

        @Override
        public void setAttribute(String key, Object value) {
            this._attributes.setAttribute(key, value);
        }

        public String toString() {
            return "INCLUDE+" + this._attributes.toString();
        }

        @Override
        public void clearAttributes() {
            throw new IllegalStateException();
        }

        @Override
        public void removeAttribute(String name) {
            this.setAttribute(name, null);
        }
    }

    private class ForwardAttributes
    extends Attributes.Wrapper {
        private final String _requestURI;
        private final String _contextPath;
        private final String _servletPath;
        private final String _pathInfo;
        private final ServletPathMapping _servletPathMapping;
        private final String _query;

        public ForwardAttributes(Attributes attributes, String requestURI, String contextPath, String pathInContext, ServletPathMapping mapping, String query) {
            super(attributes);
            this._requestURI = requestURI;
            this._contextPath = contextPath;
            this._servletPathMapping = mapping;
            this._query = query;
            this._pathInfo = this._servletPathMapping == null ? pathInContext : this._servletPathMapping.getPathInfo();
            this._servletPath = this._servletPathMapping == null ? null : this._servletPathMapping.getServletPath();
        }

        @Override
        public Object getAttribute(String key) {
            if (Dispatcher.this._named == null) {
                switch (key) {
                    case "jakarta.servlet.forward.path_info": {
                        return this._pathInfo;
                    }
                    case "jakarta.servlet.forward.request_uri": {
                        return this._requestURI;
                    }
                    case "jakarta.servlet.forward.servlet_path": {
                        return this._servletPath;
                    }
                    case "jakarta.servlet.forward.context_path": {
                        return this._contextPath;
                    }
                    case "jakarta.servlet.forward.query_string": {
                        return this._query;
                    }
                    case "jakarta.servlet.forward.mapping": {
                        return this._servletPathMapping;
                    }
                }
            }
            if (key.startsWith(Dispatcher.__INCLUDE_PREFIX)) {
                return null;
            }
            return this._attributes.getAttribute(key);
        }

        @Override
        public Set<String> getAttributeNameSet() {
            HashSet<String> set = new HashSet<String>();
            for (String name : this._attributes.getAttributeNameSet()) {
                if (name.startsWith(Dispatcher.__INCLUDE_PREFIX) || name.startsWith(Dispatcher.__FORWARD_PREFIX)) continue;
                set.add(name);
            }
            if (Dispatcher.this._named == null) {
                if (this._pathInfo != null) {
                    set.add("jakarta.servlet.forward.path_info");
                }
                if (this._requestURI != null) {
                    set.add("jakarta.servlet.forward.request_uri");
                }
                if (this._servletPath != null) {
                    set.add("jakarta.servlet.forward.servlet_path");
                }
                if (this._contextPath != null) {
                    set.add("jakarta.servlet.forward.context_path");
                }
                if (this._servletPathMapping != null) {
                    set.add("jakarta.servlet.forward.mapping");
                }
                if (this._query != null) {
                    set.add("jakarta.servlet.forward.query_string");
                }
            }
            return set;
        }

        @Override
        public void setAttribute(String key, Object value) {
            this._attributes.setAttribute(key, value);
        }

        public String toString() {
            return "FORWARD+" + this._attributes.toString();
        }

        @Override
        public void clearAttributes() {
            throw new IllegalStateException();
        }

        @Override
        public void removeAttribute(String name) {
            this.setAttribute(name, null);
        }
    }
}

