/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class HttpFilter
extends GenericFilter {
    private static final long serialVersionUID = 7478463438252262094L;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest) || !(res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }
        this.doFilter((HttpServletRequest)req, (HttpServletResponse)res, chain);
    }

    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, res);
    }
}

