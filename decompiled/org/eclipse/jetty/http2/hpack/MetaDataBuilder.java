/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

import org.eclipse.jetty.http.HostPortHttpField;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.hpack.AuthorityHttpField;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.http2.hpack.StaticTableHttpField;

public class MetaDataBuilder {
    private final int _maxSize;
    private final HttpFields.Mutable _fields = HttpFields.build();
    private int _size;
    private Integer _status;
    private String _method;
    private HttpScheme _scheme;
    private HostPortHttpField _authority;
    private String _path;
    private String _protocol;
    private long _contentLength = Long.MIN_VALUE;
    private HpackException.StreamException _streamException;
    private boolean _request;
    private boolean _response;

    protected MetaDataBuilder(int maxHeadersSize) {
        this._maxSize = maxHeadersSize;
    }

    public int getMaxSize() {
        return this._maxSize;
    }

    public int getSize() {
        return this._size;
    }

    public void emit(HttpField field2) throws HpackException.SessionException {
        HttpHeader header = field2.getHeader();
        String name = field2.getName();
        if (name == null || name.length() == 0) {
            throw new HpackException.SessionException("Header size 0", new Object[0]);
        }
        String value = field2.getValue();
        int fieldSize = name.length() + (value == null ? 0 : value.length());
        this._size += fieldSize + 32;
        if (this._size > this._maxSize) {
            throw new HpackException.SessionException("Header size %d > %d", this._size, this._maxSize);
        }
        if (field2 instanceof StaticTableHttpField) {
            StaticTableHttpField staticField = (StaticTableHttpField)field2;
            switch (header) {
                case C_STATUS: {
                    if (this.checkPseudoHeader(header, this._status)) {
                        this._status = staticField.getIntValue();
                    }
                    this._response = true;
                    break;
                }
                case C_METHOD: {
                    if (this.checkPseudoHeader(header, this._method)) {
                        this._method = value;
                    }
                    this._request = true;
                    break;
                }
                case C_SCHEME: {
                    if (this.checkPseudoHeader(header, (Object)this._scheme)) {
                        this._scheme = (HttpScheme)((Object)staticField.getStaticValue());
                    }
                    this._request = true;
                    break;
                }
                default: {
                    throw new IllegalArgumentException(name);
                }
            }
        } else if (header != null) {
            switch (header) {
                case C_STATUS: {
                    if (this.checkPseudoHeader(header, this._status)) {
                        this._status = field2.getIntValue();
                    }
                    this._response = true;
                    break;
                }
                case C_METHOD: {
                    if (this.checkPseudoHeader(header, this._method)) {
                        this._method = value;
                    }
                    this._request = true;
                    break;
                }
                case C_SCHEME: {
                    if (this.checkPseudoHeader(header, (Object)this._scheme) && value != null) {
                        this._scheme = HttpScheme.CACHE.get(value);
                    }
                    this._request = true;
                    break;
                }
                case C_AUTHORITY: {
                    if (this.checkPseudoHeader(header, this._authority)) {
                        if (field2 instanceof HostPortHttpField) {
                            this._authority = (HostPortHttpField)field2;
                        } else if (value != null) {
                            this._authority = new AuthorityHttpField(value);
                        }
                    }
                    this._request = true;
                    break;
                }
                case C_PATH: {
                    if (this.checkPseudoHeader(header, this._path)) {
                        if (value != null && value.length() > 0) {
                            this._path = value;
                        } else {
                            this.streamException("No Path", new Object[0]);
                        }
                    }
                    this._request = true;
                    break;
                }
                case C_PROTOCOL: {
                    if (this.checkPseudoHeader(header, this._protocol)) {
                        this._protocol = value;
                    }
                    this._request = true;
                    break;
                }
                case HOST: {
                    this._fields.add(field2);
                    break;
                }
                case CONTENT_LENGTH: {
                    this._contentLength = field2.getLongValue();
                    this._fields.add(field2);
                    break;
                }
                case TE: {
                    if ("trailers".equalsIgnoreCase(value)) {
                        this._fields.add(field2);
                        break;
                    }
                    this.streamException("Unsupported TE value '%s'", value);
                    break;
                }
                case CONNECTION: {
                    if ("TE".equalsIgnoreCase(value)) {
                        this._fields.add(field2);
                        break;
                    }
                    this.streamException("Connection specific field '%s'", new Object[]{header});
                    break;
                }
                default: {
                    if (name.charAt(0) == ':') {
                        this.streamException("Unknown pseudo header '%s'", name);
                        break;
                    }
                    this._fields.add(field2);
                    break;
                }
            }
        } else if (name.charAt(0) == ':') {
            this.streamException("Unknown pseudo header '%s'", name);
        } else {
            this._fields.add(field2);
        }
    }

    protected void streamException(String messageFormat, Object ... args2) {
        HpackException.StreamException stream = new HpackException.StreamException(messageFormat, args2);
        if (this._streamException == null) {
            this._streamException = stream;
        } else {
            this._streamException.addSuppressed(stream);
        }
    }

    protected boolean checkPseudoHeader(HttpHeader header, Object value) {
        if (this._fields.size() > 0) {
            this.streamException("Pseudo header %s after fields", header.asString());
            return false;
        }
        if (value == null) {
            return true;
        }
        this.streamException("Duplicate pseudo header %s", header.asString());
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MetaData build() throws HpackException.StreamException {
        if (this._streamException != null) {
            this._streamException.addSuppressed(new Throwable());
            throw this._streamException;
        }
        if (this._request && this._response) {
            throw new HpackException.StreamException("Request and Response headers", new Object[0]);
        }
        HttpFields.Mutable fields = this._fields;
        try {
            if (this._request) {
                if (this._method == null) {
                    throw new HpackException.StreamException("No Method", new Object[0]);
                }
                boolean isConnect = HttpMethod.CONNECT.is(this._method);
                if (!isConnect || this._protocol != null) {
                    if (this._scheme == null) {
                        throw new HpackException.StreamException("No Scheme", new Object[0]);
                    }
                    if (this._path == null) {
                        throw new HpackException.StreamException("No Path", new Object[0]);
                    }
                }
                if (isConnect) {
                    MetaData.ConnectRequest connectRequest = new MetaData.ConnectRequest(this._scheme, this._authority, this._path, (HttpFields)fields, this._protocol);
                    return connectRequest;
                }
                MetaData.Request request = new MetaData.Request(this._method, this._scheme == null ? HttpScheme.HTTP.asString() : this._scheme.asString(), this._authority, this._path, HttpVersion.HTTP_2, fields, this._contentLength);
                return request;
            }
            if (this._response) {
                if (this._status == null) {
                    throw new HpackException.StreamException("No Status", new Object[0]);
                }
                MetaData.Response response = new MetaData.Response(HttpVersion.HTTP_2, this._status, fields, this._contentLength);
                return response;
            }
            MetaData metaData = new MetaData(HttpVersion.HTTP_2, fields, this._contentLength);
            return metaData;
        }
        finally {
            this._fields.clear();
            this._request = false;
            this._response = false;
            this._status = null;
            this._method = null;
            this._scheme = null;
            this._authority = null;
            this._path = null;
            this._protocol = null;
            this._size = 0;
            this._contentLength = Long.MIN_VALUE;
        }
    }

    public void checkSize(int length, boolean huffman) throws HpackException.SessionException {
        if (huffman) {
            length = length * 4 / 3;
        }
        if (this._size + length > this._maxSize) {
            throw new HpackException.SessionException("Header too large %d > %d", this._size + length, this._maxSize);
        }
    }
}

