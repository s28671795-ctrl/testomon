/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Supplier;
import org.eclipse.jetty.http.HostPortHttpField;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.util.NanoTime;

public class MetaData
implements Iterable<HttpField> {
    private final HttpVersion _httpVersion;
    private final HttpFields _fields;
    private final long _contentLength;
    private final Supplier<HttpFields> _trailerSupplier;

    public static boolean isTunnel(String method2, int status) {
        return HttpMethod.CONNECT.is(method2) && HttpStatus.isSuccess(status);
    }

    public MetaData(HttpVersion version, HttpFields fields) {
        this(version, fields, -1L);
    }

    public MetaData(HttpVersion version, HttpFields fields, long contentLength) {
        this(version, fields, contentLength, null);
    }

    public MetaData(HttpVersion version, HttpFields fields, long contentLength, Supplier<HttpFields> trailerSupplier) {
        this._httpVersion = version;
        HttpFields httpFields = this._fields = fields == null ? null : fields.asImmutable();
        this._contentLength = contentLength >= 0L ? contentLength : (this._fields == null ? -1L : this._fields.getLongField(HttpHeader.CONTENT_LENGTH));
        this._trailerSupplier = trailerSupplier;
    }

    public boolean isRequest() {
        return false;
    }

    public boolean isResponse() {
        return false;
    }

    public HttpVersion getHttpVersion() {
        return this._httpVersion;
    }

    public HttpFields getFields() {
        return this._fields;
    }

    public Supplier<HttpFields> getTrailerSupplier() {
        return this._trailerSupplier;
    }

    public long getContentLength() {
        return this._contentLength;
    }

    @Override
    public Iterator<HttpField> iterator() {
        if (this._fields == null) {
            return Collections.emptyIterator();
        }
        return this._fields.iterator();
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        for (HttpField field2 : this) {
            out.append(field2).append(System.lineSeparator());
        }
        return out.toString();
    }

    public static class Response
    extends MetaData {
        private final int _status;
        private final String _reason;

        public Response(HttpVersion version, int status, HttpFields fields) {
            this(version, status, fields, Long.MIN_VALUE);
        }

        public Response(HttpVersion version, int status, HttpFields fields, long contentLength) {
            this(version, status, null, fields, contentLength);
        }

        public Response(HttpVersion version, int status, String reason, HttpFields fields, long contentLength) {
            this(version, status, reason, fields, contentLength, null);
        }

        public Response(HttpVersion version, int status, String reason, HttpFields fields, long contentLength, Supplier<HttpFields> trailers) {
            super(version, fields, contentLength, trailers);
            this._reason = reason;
            this._status = status;
        }

        @Override
        public boolean isResponse() {
            return true;
        }

        public int getStatus() {
            return this._status;
        }

        public String getReason() {
            return this._reason;
        }

        @Override
        public String toString() {
            HttpFields fields = this.getFields();
            return String.format("%s{s=%d,h=%d,cl=%d}", new Object[]{this.getHttpVersion(), this.getStatus(), fields == null ? -1 : fields.size(), this.getContentLength()});
        }
    }

    public static class ConnectRequest
    extends Request {
        private final String _protocol;

        public ConnectRequest(HttpScheme scheme2, HostPortHttpField authority, String path, HttpFields fields, String protocol) {
            this(scheme2 == null ? null : scheme2.asString(), authority, path, fields, protocol);
        }

        public ConnectRequest(String scheme2, HostPortHttpField authority, String path, HttpFields fields, String protocol) {
            super(HttpMethod.CONNECT.asString(), HttpURI.build().scheme(scheme2).host(authority == null ? null : authority.getHost()).port(authority == null ? -1 : authority.getPort()).pathQuery(path), HttpVersion.HTTP_2, fields, Long.MIN_VALUE, null);
            this._protocol = protocol;
        }

        @Override
        public String getProtocol() {
            return this._protocol;
        }
    }

    public static class Request
    extends MetaData {
        private final String _method;
        private final HttpURI _uri;
        private final long _beginNanoTime;

        public Request(HttpFields fields) {
            this(null, null, null, fields);
        }

        public Request(String method2, HttpURI uri, HttpVersion version, HttpFields fields) {
            this(method2, uri, version, fields, Long.MIN_VALUE);
        }

        public Request(long beginNanoTime, String method2, HttpURI uri, HttpVersion version, HttpFields fields) {
            this(beginNanoTime, method2, uri, version, fields, Long.MIN_VALUE);
        }

        public Request(String method2, HttpURI uri, HttpVersion version, HttpFields fields, long contentLength) {
            this(method2, uri.asImmutable(), version, fields, contentLength, null);
        }

        public Request(long beginNanoTime, String method2, HttpURI uri, HttpVersion version, HttpFields fields, long contentLength) {
            this(beginNanoTime, method2, uri.asImmutable(), version, fields, contentLength, null);
        }

        public Request(String method2, String scheme2, HostPortHttpField authority, String uri, HttpVersion version, HttpFields fields, long contentLength) {
            this(method2, HttpURI.build().scheme(scheme2).host(authority == null ? null : authority.getHost()).port(authority == null ? -1 : authority.getPort()).pathQuery(uri), version, fields, contentLength);
        }

        public Request(String method2, HttpURI uri, HttpVersion version, HttpFields fields, long contentLength, Supplier<HttpFields> trailers) {
            this(NanoTime.now(), method2, uri, version, fields, contentLength, trailers);
        }

        public Request(long beginNanoTime, String method2, HttpURI uri, HttpVersion version, HttpFields fields, long contentLength, Supplier<HttpFields> trailers) {
            super(version, fields, contentLength, trailers);
            this._method = method2;
            this._uri = uri;
            this._beginNanoTime = beginNanoTime;
        }

        public long getBeginNanoTime() {
            return this._beginNanoTime;
        }

        @Override
        public boolean isRequest() {
            return true;
        }

        public String getMethod() {
            return this._method;
        }

        public HttpURI getURI() {
            return this._uri;
        }

        public String getURIString() {
            return this._uri == null ? null : this._uri.toString();
        }

        public String getProtocol() {
            return null;
        }

        @Override
        public String toString() {
            HttpFields fields = this.getFields();
            return String.format("%s{u=%s,%s,h=%d,cl=%d,p=%s}", new Object[]{this.getMethod(), this.getURI(), this.getHttpVersion(), fields == null ? -1 : fields.size(), this.getContentLength(), this.getProtocol()});
        }
    }
}

