/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.http.PushBuilder;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushBuilderImpl
implements PushBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(PushBuilderImpl.class);
    private static final HttpField JETTY_PUSH = new HttpField("x-http2-push", "PushBuilder");
    private static EnumSet<HttpMethod> UNSAFE_METHODS = EnumSet.of(HttpMethod.POST, new HttpMethod[]{HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.CONNECT, HttpMethod.OPTIONS, HttpMethod.TRACE});
    private final Request _request;
    private final HttpFields.Mutable _fields;
    private String _method;
    private String _queryString;
    private String _sessionId;
    private String _path;
    private String _lastModified;

    public PushBuilderImpl(Request request, HttpFields fields, String method2, String queryString, String sessionId) {
        this._request = request;
        this._fields = HttpFields.build(fields);
        this._method = method2;
        this._queryString = queryString;
        this._sessionId = sessionId;
        this._fields.add(JETTY_PUSH);
        if (LOG.isDebugEnabled()) {
            LOG.debug("PushBuilder({} {}?{} s={})", this._method, this._request.getRequestURI(), this._queryString, this._sessionId);
        }
    }

    @Override
    public String getMethod() {
        return this._method;
    }

    @Override
    public PushBuilder method(String method2) {
        Objects.requireNonNull(method2);
        if (StringUtil.isBlank(method2) || UNSAFE_METHODS.contains((Object)HttpMethod.fromString(method2))) {
            throw new IllegalArgumentException("Method not allowed for push: " + method2);
        }
        this._method = method2;
        return this;
    }

    @Override
    public String getQueryString() {
        return this._queryString;
    }

    @Override
    public PushBuilder queryString(String queryString) {
        this._queryString = queryString;
        return this;
    }

    @Override
    public String getSessionId() {
        return this._sessionId;
    }

    @Override
    public PushBuilder sessionId(String sessionId) {
        this._sessionId = sessionId;
        return this;
    }

    @Override
    public Set<String> getHeaderNames() {
        return this._fields.getFieldNamesCollection();
    }

    @Override
    public String getHeader(String name) {
        return this._fields.get(name);
    }

    @Override
    public PushBuilder setHeader(String name, String value) {
        this._fields.put(name, value);
        return this;
    }

    @Override
    public PushBuilder addHeader(String name, String value) {
        this._fields.add(name, value);
        return this;
    }

    @Override
    public PushBuilder removeHeader(String name) {
        this._fields.remove(name);
        return this;
    }

    @Override
    public String getPath() {
        return this._path;
    }

    @Override
    public PushBuilder path(String path) {
        this._path = path;
        return this;
    }

    @Override
    public void push() {
        if (this._path == null || this._path.length() == 0) {
            throw new IllegalStateException("Bad Path " + this._path);
        }
        String path = this._path;
        String query = this._queryString;
        int q = path.indexOf(63);
        if (q >= 0) {
            query = query != null && query.length() > 0 ? path.substring(q + 1) + "&" + query : path.substring(q + 1);
            path = path.substring(0, q);
        }
        if (!path.startsWith("/")) {
            path = URIUtil.addPaths(this._request.getContextPath(), path);
        }
        String param2 = null;
        if (this._sessionId != null && this._request.isRequestedSessionIdFromURL()) {
            param2 = "jsessionid=" + this._sessionId;
        }
        HttpURI.Mutable uri = HttpURI.build(this._request.getHttpURI(), path, param2, query).normalize();
        MetaData.Request push = new MetaData.Request(this._method, uri, this._request.getHttpVersion(), this._fields);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Push {} {} inm={} ims={}", this._method, uri, this._fields.get(HttpHeader.IF_NONE_MATCH), this._fields.get(HttpHeader.IF_MODIFIED_SINCE));
        }
        this._request.getHttpChannel().getHttpTransport().push(push);
        this._path = null;
        this._lastModified = null;
    }
}

