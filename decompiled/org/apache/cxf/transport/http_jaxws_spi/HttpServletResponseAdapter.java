/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.WriteListener
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http_jaxws_spi;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpExchange;

class HttpServletResponseAdapter
implements HttpServletResponse {
    private HttpExchange exchange;
    private String characterEncoding;
    private Locale locale;
    private boolean committed;
    private ServletOutputStreamAdapter servletOutputStream;
    private PrintWriter writer;
    private int status;

    HttpServletResponseAdapter(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public void flushBuffer() throws IOException {
        this.exchange.getResponseBody().flush();
        this.committed = true;
    }

    public int getBufferSize() {
        throw new UnsupportedOperationException();
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public String getContentType() {
        return this.getHeader("Content-Type");
    }

    public Locale getLocale() {
        return this.locale;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (this.servletOutputStream == null) {
            this.servletOutputStream = new ServletOutputStreamAdapter(this.exchange.getResponseBody());
        }
        return this.servletOutputStream;
    }

    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            this.writer = this.characterEncoding != null ? new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.exchange.getResponseBody(), this.characterEncoding))) : new PrintWriter(this.exchange.getResponseBody());
        }
        return this.writer;
    }

    public boolean isCommitted() {
        return this.committed;
    }

    public void reset() {
        throw new UnsupportedOperationException();
    }

    public void resetBuffer() {
        throw new UnsupportedOperationException();
    }

    public void setBufferSize(int size) {
        throw new UnsupportedOperationException();
    }

    public void setCharacterEncoding(String charset) {
        this.characterEncoding = charset;
    }

    public void setContentLength(int len) {
        if (!this.committed) {
            this.exchange.getResponseHeaders().put("Content-Length", Collections.singletonList(String.valueOf(len)));
        }
    }

    public void setContentType(String type) {
        if (!this.committed) {
            this.exchange.getResponseHeaders().put("Content-Type", Collections.singletonList(type));
        }
    }

    public void setLocale(Locale loc) {
        this.locale = loc;
    }

    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException();
    }

    public void addDateHeader(String name, long date) {
        this.addHeader(name, String.valueOf(date));
    }

    public void addHeader(String name, String value) {
        this.exchange.addResponseHeader(name, value);
    }

    public void addIntHeader(String name, int value) {
        this.addHeader(name, String.valueOf(value));
    }

    public boolean containsHeader(String name) {
        return this.exchange.getResponseHeaders().containsKey(name);
    }

    public String encodeURL(String url2) {
        throw new UnsupportedOperationException();
    }

    public String encodeRedirectURL(String url2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public String encodeUrl(String url2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public String encodeRedirectUrl(String url2) {
        throw new UnsupportedOperationException();
    }

    public String getHeader(String name) {
        List<String> headers = this.exchange.getResponseHeaders().get(name);
        return headers != null && !headers.isEmpty() ? headers.get(0) : null;
    }

    public Collection<String> getHeaderNames() {
        return this.exchange.getResponseHeaders().keySet();
    }

    public Collection<String> getHeaders(String headerName) {
        return this.exchange.getResponseHeaders().get(headerName);
    }

    public int getStatus() {
        return this.status;
    }

    public void sendError(int sc) throws IOException {
        this.setStatus(sc);
        this.committed = true;
    }

    public void sendError(int sc, String msg) throws IOException {
        this.sendError(sc);
    }

    public void sendRedirect(String location) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void setDateHeader(String name, long date) {
        this.setHeader(name, String.valueOf(date));
    }

    public void setHeader(String name, String value) {
        LinkedList<String> list = new LinkedList<String>();
        list.add(value);
        this.exchange.getResponseHeaders().put(name, list);
    }

    public void setIntHeader(String name, int value) {
        this.setHeader(name, String.valueOf(value));
    }

    public void setStatus(int sc) {
        this.status = sc;
        this.exchange.setStatus(sc);
    }

    @Deprecated
    public void setStatus(int sc, String sm) {
        this.setStatus(sc);
    }

    public void setContentLengthLong(long arg0) {
        throw new UnsupportedOperationException();
    }

    private static class ServletOutputStreamAdapter
    extends ServletOutputStream {
        private OutputStream delegate;

        ServletOutputStreamAdapter(OutputStream delegate) {
            this.delegate = delegate;
        }

        public void write(int b) throws IOException {
            this.delegate.write(b);
        }

        public void flush() throws IOException {
            this.delegate.flush();
        }

        public boolean isReady() {
            throw new UnsupportedOperationException();
        }

        public void setWriteListener(WriteListener arg0) {
            throw new UnsupportedOperationException();
        }
    }
}

