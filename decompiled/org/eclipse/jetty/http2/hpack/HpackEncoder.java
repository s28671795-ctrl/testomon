/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.http2.hpack.HpackContext;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.http2.hpack.Huffman;
import org.eclipse.jetty.http2.hpack.NBitInteger;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HpackEncoder {
    private static final Logger LOG = LoggerFactory.getLogger(HpackEncoder.class);
    private static final HttpField[] STATUSES = new HttpField[599];
    static final EnumSet<HttpHeader> DO_NOT_HUFFMAN = EnumSet.of(HttpHeader.AUTHORIZATION, HttpHeader.CONTENT_MD5, HttpHeader.PROXY_AUTHENTICATE, HttpHeader.PROXY_AUTHORIZATION);
    static final EnumSet<HttpHeader> DO_NOT_INDEX = EnumSet.of(HttpHeader.AUTHORIZATION, new HttpHeader[]{HttpHeader.CONTENT_MD5, HttpHeader.CONTENT_RANGE, HttpHeader.ETAG, HttpHeader.IF_MODIFIED_SINCE, HttpHeader.IF_UNMODIFIED_SINCE, HttpHeader.IF_NONE_MATCH, HttpHeader.IF_RANGE, HttpHeader.IF_MATCH, HttpHeader.LOCATION, HttpHeader.RANGE, HttpHeader.RETRY_AFTER, HttpHeader.LAST_MODIFIED, HttpHeader.SET_COOKIE, HttpHeader.SET_COOKIE2});
    static final EnumSet<HttpHeader> NEVER_INDEX = EnumSet.of(HttpHeader.AUTHORIZATION, HttpHeader.SET_COOKIE, HttpHeader.SET_COOKIE2);
    private static final EnumSet<HttpHeader> IGNORED_HEADERS = EnumSet.of(HttpHeader.CONNECTION, HttpHeader.KEEP_ALIVE, HttpHeader.PROXY_CONNECTION, HttpHeader.TRANSFER_ENCODING, HttpHeader.UPGRADE);
    private static final PreEncodedHttpField TE_TRAILERS = new PreEncodedHttpField(HttpHeader.TE, "trailers");
    private static final PreEncodedHttpField C_SCHEME_HTTP = new PreEncodedHttpField(HttpHeader.C_SCHEME, "http");
    private static final PreEncodedHttpField C_SCHEME_HTTPS = new PreEncodedHttpField(HttpHeader.C_SCHEME, "https");
    private static final EnumMap<HttpMethod, PreEncodedHttpField> C_METHODS = new EnumMap(HttpMethod.class);
    private final HpackContext _context;
    private final boolean _debug;
    private int _remoteMaxDynamicTableSize;
    private int _localMaxDynamicTableSize;
    private int _maxHeaderListSize;
    private int _headerListSize;
    private boolean _validateEncoding = true;

    public HpackEncoder() {
        this(4096, 4096, -1);
    }

    public HpackEncoder(int localMaxDynamicTableSize) {
        this(localMaxDynamicTableSize, 4096, -1);
    }

    public HpackEncoder(int localMaxDynamicTableSize, int remoteMaxDynamicTableSize) {
        this(localMaxDynamicTableSize, remoteMaxDynamicTableSize, -1);
    }

    public HpackEncoder(int localMaxDynamicTableSize, int remoteMaxDynamicTableSize, int maxHeaderListSize) {
        this._context = new HpackContext(remoteMaxDynamicTableSize);
        this._remoteMaxDynamicTableSize = remoteMaxDynamicTableSize;
        this._localMaxDynamicTableSize = localMaxDynamicTableSize;
        this._maxHeaderListSize = maxHeaderListSize;
        this._debug = LOG.isDebugEnabled();
    }

    public int getMaxHeaderListSize() {
        return this._maxHeaderListSize;
    }

    public void setMaxHeaderListSize(int maxHeaderListSize) {
        this._maxHeaderListSize = maxHeaderListSize;
    }

    public HpackContext getHpackContext() {
        return this._context;
    }

    public void setRemoteMaxDynamicTableSize(int remoteMaxDynamicTableSize) {
        this._remoteMaxDynamicTableSize = remoteMaxDynamicTableSize;
    }

    public void setLocalMaxDynamicTableSize(int localMaxDynamicTableSize) {
        this._localMaxDynamicTableSize = localMaxDynamicTableSize;
    }

    public boolean isValidateEncoding() {
        return this._validateEncoding;
    }

    public void setValidateEncoding(boolean validateEncoding) {
        this._validateEncoding = validateEncoding;
    }

    /*
     * WARNING - void declaration
     */
    public void encode(ByteBuffer buffer, MetaData metadata) throws HpackException {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("CtxTbl[%x] encoding", this._context.hashCode()));
            }
            HttpFields fields = metadata.getFields();
            if (this.isValidateEncoding() && fields != null) {
                for (HttpField field2 : fields) {
                    String name = field2.getName();
                    char firstChar = name.charAt(0);
                    if (firstChar > ' ' && firstChar != ':') continue;
                    throw new HpackException.StreamException("Invalid header name: '%s'", name);
                }
            }
            this._headerListSize = 0;
            int pos = buffer.position();
            int maxDynamicTableSize = Math.min(this._remoteMaxDynamicTableSize, this._localMaxDynamicTableSize);
            if (maxDynamicTableSize != this._context.getMaxDynamicTableSize()) {
                this.encodeMaxDynamicTableSize(buffer, maxDynamicTableSize);
            }
            if (metadata.isRequest()) {
                MetaData.Request request = (MetaData.Request)metadata;
                String method2 = request.getMethod();
                HttpMethod httpMethod = method2 == null ? null : HttpMethod.fromString(method2);
                HttpField methodField = C_METHODS.get((Object)httpMethod);
                this.encode(buffer, methodField == null ? new HttpField(HttpHeader.C_METHOD, method2) : methodField);
                this.encode(buffer, new HttpField(HttpHeader.C_AUTHORITY, request.getURI().getAuthority()));
                boolean isConnect = HttpMethod.CONNECT.is(request.getMethod());
                String protocol = request.getProtocol();
                if (!isConnect || protocol != null) {
                    String scheme2 = request.getURI().getScheme();
                    this.encode(buffer, HttpScheme.HTTPS.is(scheme2) ? C_SCHEME_HTTPS : C_SCHEME_HTTP);
                    this.encode(buffer, new HttpField(HttpHeader.C_PATH, request.getURI().getPathQuery()));
                    if (protocol != null) {
                        this.encode(buffer, new HttpField(HttpHeader.C_PROTOCOL, protocol));
                    }
                }
            } else if (metadata.isResponse()) {
                void var8_19;
                HttpField httpField;
                MetaData.Response response = (MetaData.Response)metadata;
                int code = response.getStatus();
                HttpField httpField2 = httpField = code < STATUSES.length ? STATUSES[code] : null;
                if (httpField == null) {
                    HttpField.IntValueHttpField intValueHttpField = new HttpField.IntValueHttpField(HttpHeader.C_STATUS, code);
                }
                this.encode(buffer, (HttpField)var8_19);
            }
            if (fields != null) {
                long l;
                HashSet<String> hopHeaders = null;
                for (String string : fields.getCSV(HttpHeader.CONNECTION, false)) {
                    if (hopHeaders == null) {
                        hopHeaders = new HashSet<String>();
                    }
                    hopHeaders.add(StringUtil.asciiToLowerCase(string));
                }
                boolean contentLengthEncoded = false;
                for (HttpField field3 : fields) {
                    HttpHeader header = field3.getHeader();
                    if (header != null && IGNORED_HEADERS.contains((Object)header)) continue;
                    if (header == HttpHeader.TE) {
                        if (!field3.contains("trailers")) continue;
                        this.encode(buffer, TE_TRAILERS);
                        continue;
                    }
                    String name = field3.getLowerCaseName();
                    if (hopHeaders != null && hopHeaders.contains(name)) continue;
                    if (header == HttpHeader.CONTENT_LENGTH) {
                        contentLengthEncoded = true;
                    }
                    this.encode(buffer, field3);
                }
                if (!contentLengthEncoded && (l = metadata.getContentLength()) >= 0L) {
                    this.encode(buffer, new HttpField(HttpHeader.CONTENT_LENGTH, String.valueOf(l)));
                }
            }
            if (this._maxHeaderListSize > 0 && this._headerListSize > this._maxHeaderListSize) {
                if (LOG.isDebugEnabled()) {
                    LOG.warn("Header list size too large {} > {} metadata={}", this._headerListSize, this._maxHeaderListSize, metadata);
                } else {
                    LOG.warn("Header list size too large {} > {}", (Object)this._headerListSize, (Object)this._maxHeaderListSize);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("CtxTbl[%x] encoded %d octets", this._context.hashCode(), buffer.position() - pos));
            }
        }
        catch (HpackException x) {
            throw x;
        }
        catch (Throwable x) {
            HpackException.SessionException failure = new HpackException.SessionException("Could not hpack encode %s", metadata);
            failure.initCause(x);
            throw failure;
        }
    }

    public void encodeMaxDynamicTableSize(ByteBuffer buffer, int maxDynamicTableSize) {
        if (maxDynamicTableSize > this._remoteMaxDynamicTableSize) {
            throw new IllegalArgumentException();
        }
        buffer.put((byte)32);
        NBitInteger.encode(buffer, 5, maxDynamicTableSize);
        this._context.resize(maxDynamicTableSize);
    }

    public void encode(ByteBuffer buffer, HttpField field2) {
        if (field2.getValue() == null) {
            field2 = new HttpField(field2.getHeader(), field2.getName(), "");
        }
        int fieldSize = field2.getName().length() + field2.getValue().length();
        this._headerListSize += fieldSize + 32;
        Object encoding = null;
        HpackContext.Entry entry = this._context.get(field2);
        if (entry != null) {
            if (entry.isStatic()) {
                buffer.put(((HpackContext.StaticEntry)entry).getEncodedField());
                if (this._debug) {
                    encoding = "IdxFieldS1";
                }
            } else {
                int index = this._context.index(entry);
                buffer.put((byte)-128);
                NBitInteger.encode(buffer, 7, index);
                if (this._debug) {
                    encoding = "IdxField" + (entry.isStatic() ? "S" : "") + (1 + NBitInteger.octectsNeeded(7, index));
                }
            }
        } else {
            boolean indexed;
            HttpHeader header = field2.getHeader();
            if (header == null) {
                HpackContext.Entry name = this._context.get(field2.getName());
                if (field2 instanceof PreEncodedHttpField) {
                    int i = buffer.position();
                    ((PreEncodedHttpField)field2).putTo(buffer, HttpVersion.HTTP_2);
                    byte b = buffer.get(i);
                    boolean bl = indexed = b < 0 || b >= 64;
                    if (this._debug) {
                        encoding = indexed ? "PreEncodedIdx" : "PreEncoded";
                    }
                } else if (name == null && fieldSize < this._context.getMaxDynamicTableSize()) {
                    indexed = true;
                    this.encodeName(buffer, (byte)64, 6, field2.getName(), null);
                    HpackEncoder.encodeValue(buffer, true, field2.getValue());
                    if (this._debug) {
                        encoding = "LitHuffNHuffVIdx";
                    }
                } else {
                    indexed = false;
                    this.encodeName(buffer, (byte)0, 4, field2.getName(), null);
                    HpackEncoder.encodeValue(buffer, true, field2.getValue());
                    if (this._debug) {
                        encoding = "LitHuffNHuffV!Idx";
                    }
                }
            } else {
                HpackContext.Entry name = this._context.get(header);
                if (field2 instanceof PreEncodedHttpField) {
                    int i = buffer.position();
                    ((PreEncodedHttpField)field2).putTo(buffer, HttpVersion.HTTP_2);
                    byte b = buffer.get(i);
                    boolean bl = indexed = b < 0 || b >= 64;
                    if (this._debug) {
                        encoding = indexed ? "PreEncodedIdx" : "PreEncoded";
                    }
                } else if (DO_NOT_INDEX.contains((Object)header)) {
                    indexed = false;
                    boolean neverIndex = NEVER_INDEX.contains((Object)header);
                    boolean huffman = !DO_NOT_HUFFMAN.contains((Object)header);
                    this.encodeName(buffer, neverIndex ? (byte)16 : 0, 4, header.asString(), name);
                    HpackEncoder.encodeValue(buffer, huffman, field2.getValue());
                    if (this._debug) {
                        encoding = "Lit" + (String)(name == null ? "HuffN" : "IdxN" + (name.isStatic() ? "S" : "") + (1 + NBitInteger.octectsNeeded(4, this._context.index(name)))) + (huffman ? "HuffV" : "LitV") + (neverIndex ? "!!Idx" : "!Idx");
                    }
                } else if (fieldSize >= this._context.getMaxDynamicTableSize() || header == HttpHeader.CONTENT_LENGTH && !"0".equals(field2.getValue())) {
                    indexed = false;
                    this.encodeName(buffer, (byte)0, 4, header.asString(), name);
                    HpackEncoder.encodeValue(buffer, true, field2.getValue());
                    if (this._debug) {
                        encoding = "Lit" + (String)(name == null ? "HuffN" : "IdxNS" + (1 + NBitInteger.octectsNeeded(4, this._context.index(name)))) + "HuffV!Idx";
                    }
                } else {
                    indexed = true;
                    boolean huffman = !DO_NOT_HUFFMAN.contains((Object)header);
                    this.encodeName(buffer, (byte)64, 6, header.asString(), name);
                    HpackEncoder.encodeValue(buffer, huffman, field2.getValue());
                    if (this._debug) {
                        encoding = (String)(name == null ? "LitHuffN" : "LitIdxN" + (name.isStatic() ? "S" : "") + (1 + NBitInteger.octectsNeeded(6, this._context.index(name)))) + (huffman ? "HuffVIdx" : "LitVIdx");
                    }
                }
            }
            if (indexed) {
                this._context.add(field2);
            }
        }
        if (this._debug && LOG.isDebugEnabled()) {
            LOG.debug("encode {}:'{}' to '{}'", encoding, field2, BufferUtil.toHexString(buffer.duplicate().flip()));
        }
    }

    private void encodeName(ByteBuffer buffer, byte mask, int bits, String name, HpackContext.Entry entry) {
        buffer.put(mask);
        if (entry == null) {
            buffer.put((byte)-128);
            NBitInteger.encode(buffer, 7, Huffman.octetsNeededLC(name));
            Huffman.encodeLC(buffer, name);
        } else {
            NBitInteger.encode(buffer, bits, this._context.index(entry));
        }
    }

    static void encodeValue(ByteBuffer buffer, boolean huffman, String value) {
        if (huffman) {
            buffer.put((byte)-128);
            int needed = Huffman.octetsNeeded(value);
            if (needed >= 0) {
                NBitInteger.encode(buffer, 7, needed);
                Huffman.encode(buffer, value);
            } else {
                byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
                NBitInteger.encode(buffer, 7, Huffman.octetsNeeded(bytes));
                Huffman.encode(buffer, bytes);
            }
        } else {
            buffer.put((byte)0).mark();
            NBitInteger.encode(buffer, 7, value.length());
            for (int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if (c < ' ' || c > '\u007f') {
                    buffer.reset();
                    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
                    NBitInteger.encode(buffer, 7, bytes.length);
                    buffer.put(bytes, 0, bytes.length);
                    return;
                }
                buffer.put((byte)c);
            }
        }
    }

    static {
        for (HttpStatus.Code code : HttpStatus.Code.values()) {
            HpackEncoder.STATUSES[code.getCode()] = new PreEncodedHttpField(HttpHeader.C_STATUS, Integer.toString(code.getCode()));
        }
        for (Enum enum_ : HttpMethod.values()) {
            C_METHODS.put((HttpMethod)enum_, new PreEncodedHttpField(HttpHeader.C_METHOD, ((HttpMethod)enum_).asString()));
        }
    }
}

