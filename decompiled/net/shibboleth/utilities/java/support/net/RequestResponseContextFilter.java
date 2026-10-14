/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package net.shibboleth.utilities.java.support.net;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.net.HttpServletRequestResponseContext;

public class RequestResponseContextFilter
implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Request is not an instance of HttpServletRequest");
        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Response is not an instance of HttpServletResponse");
        }
        try {
            HttpServletRequestResponseContext.loadCurrent((HttpServletRequest)request, (HttpServletResponse)response);
            chain.doFilter(request, response);
        }
        finally {
            HttpServletRequestResponseContext.clearCurrent();
        }
    }
}

