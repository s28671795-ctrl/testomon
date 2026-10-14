/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorPageErrorHandler
extends ErrorHandler
implements ErrorHandler.ErrorPageMapper {
    public static final String GLOBAL_ERROR_PAGE = "org.eclipse.jetty.server.error_page.global";
    private static final Logger LOG = LoggerFactory.getLogger(ErrorPageErrorHandler.class);
    private final Map<String, String> _errorPages = new HashMap<String, String>();
    private final List<ErrorCodeRange> _errorPageList = new ArrayList<ErrorCodeRange>();
    protected ServletContext _servletContext;
    private boolean _unwrapServletException = false;

    public boolean isUnwrapServletException() {
        return this._unwrapServletException;
    }

    public void setUnwrapServletException(boolean unwrapServletException) {
        this._unwrapServletException = unwrapServletException;
    }

    @Override
    public String getErrorPage(HttpServletRequest request) {
        Throwable unwrapped;
        Throwable error;
        String errorPage = null;
        PageLookupTechnique pageSource = null;
        Class<?> matchedThrowable = null;
        Throwable cause = error = (Throwable)request.getAttribute("jakarta.servlet.error.exception");
        while (errorPage == null && cause != null) {
            pageSource = PageLookupTechnique.THROWABLE;
            Class<?> exClass = cause.getClass();
            errorPage = this._errorPages.get(exClass.getName());
            while (errorPage == null && (exClass = exClass.getSuperclass()) != null) {
                errorPage = this._errorPages.get(exClass.getName());
            }
            if (errorPage != null) {
                matchedThrowable = exClass;
            }
            cause = cause instanceof ServletException ? ((ServletException)cause).getRootCause() : null;
        }
        if (error instanceof ServletException && this._unwrapServletException && (unwrapped = this.getFirstNonServletException(error)) != null) {
            request.setAttribute("jakarta.servlet.error.exception", unwrapped);
            request.setAttribute("jakarta.servlet.error.exception_type", unwrapped.getClass());
        }
        Integer errorStatusCode = null;
        if (errorPage == null) {
            pageSource = PageLookupTechnique.STATUS_CODE;
            errorStatusCode = (Integer)request.getAttribute("jakarta.servlet.error.status_code");
            if (errorStatusCode != null && (errorPage = this._errorPages.get(Integer.toString(errorStatusCode))) == null) {
                for (ErrorCodeRange errCode : this._errorPageList) {
                    if (!errCode.isInRange(errorStatusCode)) continue;
                    errorPage = errCode.getUri();
                    break;
                }
            }
        }
        if (errorPage == null) {
            pageSource = PageLookupTechnique.GLOBAL;
            errorPage = this._errorPages.get(GLOBAL_ERROR_PAGE);
        }
        if (LOG.isDebugEnabled()) {
            StringBuilder dbg = new StringBuilder();
            dbg.append("getErrorPage(");
            dbg.append(request.getMethod()).append(' ');
            dbg.append(request.getRequestURI());
            dbg.append(") => error_page=").append(errorPage);
            switch (pageSource) {
                case THROWABLE: {
                    dbg.append(" (using matched Throwable ");
                    dbg.append(matchedThrowable.getName());
                    dbg.append(" / actually thrown as ");
                    Throwable originalThrowable = (Throwable)request.getAttribute("jakarta.servlet.error.exception");
                    dbg.append(originalThrowable.getClass().getName());
                    dbg.append(')');
                    LOG.debug(dbg.toString(), cause);
                    break;
                }
                case STATUS_CODE: {
                    dbg.append(" (from status code ");
                    dbg.append(errorStatusCode);
                    dbg.append(')');
                    LOG.debug(dbg.toString());
                    break;
                }
                case GLOBAL: {
                    dbg.append(" (from global default)");
                    LOG.debug(dbg.toString());
                    break;
                }
                default: {
                    throw new IllegalStateException(pageSource.toString());
                }
            }
        }
        return errorPage;
    }

    private Throwable getFirstNonServletException(Throwable t2) {
        if (t2 instanceof ServletException && t2.getCause() != null) {
            return this.getFirstNonServletException(t2.getCause());
        }
        return t2;
    }

    public Map<String, String> getErrorPages() {
        return this._errorPages;
    }

    public void setErrorPages(Map<String, String> errorPages) {
        this._errorPages.clear();
        if (errorPages != null) {
            this._errorPages.putAll(errorPages);
        }
    }

    public void addErrorPage(Class<? extends Throwable> exception, String uri) {
        this._errorPages.put(exception.getName(), uri);
    }

    public void addErrorPage(String exceptionClassName, String uri) {
        this._errorPages.put(exceptionClassName, uri);
    }

    public void addErrorPage(int code, String uri) {
        this._errorPages.put(Integer.toString(code), uri);
    }

    public void addErrorPage(int from, int to, String uri) {
        this._errorPageList.add(new ErrorCodeRange(from, to, uri));
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        this._servletContext = ContextHandler.getCurrentContext();
    }

    private static enum PageLookupTechnique {
        THROWABLE,
        STATUS_CODE,
        GLOBAL;

    }

    private static class ErrorCodeRange {
        private final int _from;
        private final int _to;
        private final String _uri;

        ErrorCodeRange(int from, int to, String uri) throws IllegalArgumentException {
            if (from > to) {
                throw new IllegalArgumentException("from>to");
            }
            this._from = from;
            this._to = to;
            this._uri = uri;
        }

        boolean isInRange(int value) {
            return this._from <= value && value <= this._to;
        }

        String getUri() {
            return this._uri;
        }

        public String toString() {
            return "from: " + this._from + ",to: " + this._to + ",uri: " + this._uri;
        }
    }
}

