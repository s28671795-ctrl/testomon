/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpgradeHttpServletResponse
implements HttpServletResponse {
    private static final String UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE = "Feature unsupported after Upgraded to WebSocket";
    private HttpServletResponse _response;
    private int _status;
    private Map<String, Collection<String>> _headers;
    private Locale _locale;
    private String _characterEncoding;
    private String _contentType;

    public UpgradeHttpServletResponse(HttpServletResponse response) {
        this._response = response;
    }

    public void upgrade() {
        this._status = this._response.getStatus();
        this._locale = this._response.getLocale();
        this._characterEncoding = this._response.getCharacterEncoding();
        this._contentType = this._response.getContentType();
        this._headers = new HashMap<String, Collection<String>>();
        for (String name : this._response.getHeaderNames()) {
            this._headers.put(name, this._response.getHeaders(name));
        }
        this._response = null;
    }

    public HttpServletResponse getResponse() {
        return this._response;
    }

    @Override
    public int getStatus() {
        if (this._response == null) {
            return this._status;
        }
        return this._response.getStatus();
    }

    @Override
    public String getHeader(String s2) {
        if (this._response == null) {
            Collection<String> values2 = this._headers.get(s2);
            if (values2 == null) {
                return null;
            }
            return values2.stream().findFirst().orElse(null);
        }
        return this._response.getHeader(s2);
    }

    @Override
    public Collection<String> getHeaders(String s2) {
        if (this._response == null) {
            return this._headers.get(s2);
        }
        return this._response.getHeaders(s2);
    }

    @Override
    public Collection<String> getHeaderNames() {
        if (this._response == null) {
            return this._headers.keySet();
        }
        return this._response.getHeaderNames();
    }

    @Override
    public Locale getLocale() {
        if (this._response == null) {
            return this._locale;
        }
        return this._response.getLocale();
    }

    @Override
    public boolean containsHeader(String s2) {
        if (this._response == null) {
            Collection<String> values2 = this._headers.get(s2);
            return values2 != null && !values2.isEmpty();
        }
        return this._response.containsHeader(s2);
    }

    @Override
    public String getCharacterEncoding() {
        if (this._response == null) {
            return this._characterEncoding;
        }
        return this._response.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        if (this._response == null) {
            return this._contentType;
        }
        return this._response.getContentType();
    }

    @Override
    public void addCookie(Cookie cookie) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.addCookie(cookie);
    }

    @Override
    public String encodeURL(String s2) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.encodeURL(s2);
    }

    @Override
    public String encodeRedirectURL(String s2) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.encodeRedirectURL(s2);
    }

    @Override
    public String encodeUrl(String s2) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.encodeUrl(s2);
    }

    @Override
    public String encodeRedirectUrl(String s2) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.encodeRedirectUrl(s2);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.getWriter();
    }

    @Override
    public void setCharacterEncoding(String s2) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setCharacterEncoding(s2);
    }

    @Override
    public void setContentLength(int i) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setContentLength(i);
    }

    @Override
    public void setContentLengthLong(long l) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setContentLengthLong(l);
    }

    @Override
    public void setContentType(String s2) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setContentType(s2);
    }

    @Override
    public void setBufferSize(int i) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setBufferSize(i);
    }

    @Override
    public int getBufferSize() {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this._response.isCommitted();
    }

    @Override
    public void reset() {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.reset();
    }

    @Override
    public void setLocale(Locale locale) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setLocale(locale);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.sendError(sc, msg);
    }

    @Override
    public void sendError(int sc) throws IOException {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.sendError(sc);
    }

    @Override
    public void setHeader(String name, String value) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setHeader(name, value);
    }

    @Override
    public void sendRedirect(String s2) throws IOException {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.sendRedirect(s2);
    }

    @Override
    public void setDateHeader(String s2, long l) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setDateHeader(s2, l);
    }

    @Override
    public void addDateHeader(String s2, long l) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.addDateHeader(s2, l);
    }

    @Override
    public void addHeader(String name, String value) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.addHeader(name, value);
    }

    @Override
    public void setIntHeader(String s2, int i) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setIntHeader(s2, i);
    }

    @Override
    public void addIntHeader(String s2, int i) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.addIntHeader(s2, i);
    }

    @Override
    public void setStatus(int i) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setStatus(i);
    }

    @Override
    public void setStatus(int i, String s2) {
        if (this._response == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this._response.setStatus(i, s2);
    }
}

