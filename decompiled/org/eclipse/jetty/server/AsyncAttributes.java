/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jetty.server.ServletPathMapping;
import org.eclipse.jetty.util.Attributes;

class AsyncAttributes
extends Attributes.Wrapper {
    private final String _requestURI;
    private final String _contextPath;
    private final ServletPathMapping _mapping;
    private final String _queryString;
    private final String _servletPath;
    private final String _pathInfo;

    public AsyncAttributes(Attributes attributes, String requestUri, String contextPath, String pathInContext, ServletPathMapping mapping, String queryString) {
        super(attributes);
        this._requestURI = requestUri;
        this._contextPath = contextPath;
        this._servletPath = mapping == null ? null : mapping.getServletPath();
        this._pathInfo = mapping == null ? pathInContext : mapping.getPathInfo();
        this._mapping = mapping;
        this._queryString = queryString;
    }

    @Override
    public Object getAttribute(String key) {
        switch (key) {
            case "jakarta.servlet.async.request_uri": {
                return this._requestURI;
            }
            case "jakarta.servlet.async.context_path": {
                return this._contextPath;
            }
            case "jakarta.servlet.async.servlet_path": {
                return this._servletPath;
            }
            case "jakarta.servlet.async.path_info": {
                return this._pathInfo;
            }
            case "jakarta.servlet.async.query_string": {
                return this._queryString;
            }
            case "jakarta.servlet.async.mapping": {
                return this._mapping;
            }
        }
        return super.getAttribute(key);
    }

    @Override
    public Set<String> getAttributeNameSet() {
        HashSet<String> set = new HashSet<String>(super.getAttributeNameSet());
        if (this._requestURI != null) {
            set.add("jakarta.servlet.async.request_uri");
        }
        if (this._contextPath != null) {
            set.add("jakarta.servlet.async.context_path");
        }
        if (this._servletPath != null) {
            set.add("jakarta.servlet.async.servlet_path");
        }
        if (this._pathInfo != null) {
            set.add("jakarta.servlet.async.path_info");
        }
        if (this._queryString != null) {
            set.add("jakarta.servlet.async.query_string");
        }
        if (this._mapping != null) {
            set.add("jakarta.servlet.async.mapping");
        }
        return set;
    }

    @Override
    public void setAttribute(String key, Object value) {
        switch (key) {
            case "jakarta.servlet.async.request_uri": 
            case "jakarta.servlet.async.context_path": 
            case "jakarta.servlet.async.servlet_path": 
            case "jakarta.servlet.async.path_info": 
            case "jakarta.servlet.async.query_string": 
            case "jakarta.servlet.async.mapping": {
                break;
            }
            default: {
                super.setAttribute(key, value);
            }
        }
    }
}

