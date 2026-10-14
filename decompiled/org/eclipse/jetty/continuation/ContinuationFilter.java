/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 */
package org.eclipse.jetty.continuation;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.eclipse.jetty.continuation.ContinuationThrowable;
import org.eclipse.jetty.continuation.FauxContinuation;

@Deprecated
public class ContinuationFilter
implements Filter {
    static boolean _initialized;
    static boolean __debug;
    private boolean _faux;
    private boolean _filtered;
    ServletContext _context;
    private boolean _debug;

    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext().log("WARNING: " + this.getClass().getName() + " is now DEPRECATED, use Servlet 3.0 AsyncContext instead.");
        boolean jetty7OrGreater = "org.eclipse.jetty.servlet".equals(filterConfig.getClass().getPackage().getName());
        this._context = filterConfig.getServletContext();
        String param2 = filterConfig.getInitParameter("debug");
        boolean bl = this._debug = param2 != null && Boolean.parseBoolean(param2);
        if (this._debug) {
            __debug = true;
        }
        param2 = filterConfig.getInitParameter("partial");
        param2 = filterConfig.getInitParameter("faux");
        this._faux = param2 != null ? Boolean.parseBoolean(param2) : !jetty7OrGreater && this._context.getMajorVersion() < 3;
        this._filtered = this._faux;
        if (this._debug) {
            this._context.log("ContinuationFilter  jetty=" + jetty7OrGreater + " faux=" + this._faux + " filtered=" + this._filtered + " servlet3=" + ContinuationSupport.__servlet3);
        }
        _initialized = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (this._filtered) {
            FilteredContinuation fc;
            Continuation c = (Continuation)request.getAttribute("org.eclipse.jetty.continuation");
            if (this._faux && (c == null || !(c instanceof FauxContinuation))) {
                fc = new FauxContinuation(request);
                request.setAttribute("org.eclipse.jetty.continuation", (Object)fc);
            } else {
                fc = (FilteredContinuation)c;
            }
            boolean complete = false;
            while (!complete) {
                block13: {
                    try {
                        if (fc == null || fc.enter(response)) {
                            chain.doFilter(request, response);
                        }
                        if (fc != null) break block13;
                    }
                    catch (ContinuationThrowable e) {
                        block14: {
                            try {
                                this.debug("faux", e);
                                if (fc != null) break block14;
                            }
                            catch (Throwable throwable) {
                                if (fc == null) {
                                    fc = (FilteredContinuation)request.getAttribute("org.eclipse.jetty.continuation");
                                }
                                complete = fc == null || fc.exit();
                                throw throwable;
                            }
                            fc = (FilteredContinuation)request.getAttribute("org.eclipse.jetty.continuation");
                        }
                        complete = fc == null || fc.exit();
                        continue;
                    }
                    fc = (FilteredContinuation)request.getAttribute("org.eclipse.jetty.continuation");
                }
                complete = fc == null || fc.exit();
            }
        } else {
            try {
                chain.doFilter(request, response);
            }
            catch (ContinuationThrowable e) {
                this.debug("caught", e);
            }
        }
    }

    private void debug(String string) {
        if (this._debug) {
            this._context.log(string);
        }
    }

    private void debug(String string, Throwable th) {
        if (this._debug) {
            if (th instanceof ContinuationThrowable) {
                this._context.log(string + ":" + th);
            } else {
                this._context.log(string, th);
            }
        }
    }

    public void destroy() {
    }

    public static interface FilteredContinuation
    extends Continuation {
        public boolean enter(ServletResponse var1);

        public boolean exit();
    }
}

