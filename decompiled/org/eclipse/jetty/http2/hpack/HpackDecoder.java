/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpTokens;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.hpack.AuthorityHttpField;
import org.eclipse.jetty.http2.hpack.HpackContext;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.http2.hpack.Huffman;
import org.eclipse.jetty.http2.hpack.MetaDataBuilder;
import org.eclipse.jetty.http2.hpack.NBitInteger;
import org.eclipse.jetty.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HpackDecoder {
    public static final Logger LOG = LoggerFactory.getLogger(HpackDecoder.class);
    public static final HttpField.LongValueHttpField CONTENT_LENGTH_0 = new HttpField.LongValueHttpField(HttpHeader.CONTENT_LENGTH, 0L);
    private final HpackContext _context;
    private final MetaDataBuilder _builder;
    private int _localMaxDynamicTableSize;

    public HpackDecoder(int localMaxDynamicTableSize, int maxHeaderSize) {
        this._context = new HpackContext(localMaxDynamicTableSize);
        this._localMaxDynamicTableSize = localMaxDynamicTableSize;
        this._builder = new MetaDataBuilder(maxHeaderSize);
    }

    public HpackContext getHpackContext() {
        return this._context;
    }

    public void setLocalMaxDynamicTableSize(int localMaxdynamciTableSize) {
        this._localMaxDynamicTableSize = localMaxdynamciTableSize;
    }

    public MetaData decode(ByteBuffer buffer) throws HpackException.SessionException, HpackException.StreamException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("CtxTbl[%x] decoding %d octets", this._context.hashCode(), buffer.remaining()));
        }
        if (buffer.remaining() > this._builder.getMaxSize()) {
            throw new HpackException.SessionException("431 Request Header Fields too large", new Object[0]);
        }
        boolean emitted = false;
        block14: while (buffer.hasRemaining()) {
            HttpField field2;
            HttpHeader header;
            String name;
            int nameIndex;
            boolean indexed;
            byte b;
            if (LOG.isDebugEnabled()) {
                LOG.debug("decode {}", (Object)BufferUtil.toHexString(buffer));
            }
            if ((b = buffer.get()) < 0) {
                int index = NBitInteger.decode(buffer, 7);
                HpackContext.Entry entry = this._context.get(index);
                if (entry == null) {
                    throw new HpackException.SessionException("Unknown index %d", index);
                }
                if (entry.isStatic()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("decode IdxStatic {}", (Object)entry);
                    }
                    emitted = true;
                    this._builder.emit(entry.getHttpField());
                    continue;
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("decode Idx {}", (Object)entry);
                }
                emitted = true;
                this._builder.emit(entry.getHttpField());
                continue;
            }
            byte f = (byte)((b & 0xF0) >> 4);
            switch (f) {
                case 2: 
                case 3: {
                    int size = NBitInteger.decode(buffer, 5);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("decode resize={}", (Object)size);
                    }
                    if (size > this._localMaxDynamicTableSize) {
                        throw new IllegalArgumentException();
                    }
                    if (emitted) {
                        throw new HpackException.CompressionException("Dynamic table resize after fields", new Object[0]);
                    }
                    this._context.resize(size);
                    continue block14;
                }
                case 0: 
                case 1: {
                    indexed = false;
                    nameIndex = NBitInteger.decode(buffer, 4);
                    break;
                }
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    indexed = true;
                    nameIndex = NBitInteger.decode(buffer, 6);
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            boolean huffmanName = false;
            if (nameIndex > 0) {
                HpackContext.Entry nameEntry = this._context.get(nameIndex);
                name = nameEntry.getHttpField().getName();
                header = nameEntry.getHttpField().getHeader();
            } else {
                huffmanName = (buffer.get() & 0x80) == 128;
                int length = NBitInteger.decode(buffer, 7);
                this._builder.checkSize(length, huffmanName);
                name = huffmanName ? Huffman.decode(buffer, length) : HpackDecoder.toASCIIString(buffer, length);
                int i = name.length();
                block15: while (i-- > 0) {
                    char c = name.charAt(i);
                    if (c > '\u00ff') {
                        this._builder.streamException("Illegal header name %s", name);
                        break;
                    }
                    HttpTokens.Token token = HttpTokens.TOKENS[0xFF & c];
                    switch (token.getType()) {
                        case ALPHA: {
                            if (c < 'A' || c > 'Z') continue block15;
                            this._builder.streamException("Uppercase header name %s", name);
                            break;
                        }
                        case COLON: 
                        case TCHAR: 
                        case DIGIT: {
                            continue block15;
                        }
                        default: {
                            this._builder.streamException("Illegal header name %s", name);
                            break;
                        }
                    }
                    break;
                }
                header = HttpHeader.CACHE.get(name);
            }
            boolean huffmanValue = (buffer.get() & 0x80) == 128;
            int length = NBitInteger.decode(buffer, 7);
            this._builder.checkSize(length, huffmanValue);
            String value = huffmanValue ? Huffman.decode(buffer, length) : HpackDecoder.toASCIIString(buffer, length);
            if (header == null) {
                field2 = new HttpField(null, name, value);
            } else {
                switch (header) {
                    case C_STATUS: {
                        if (indexed) {
                            field2 = new HttpField.IntValueHttpField(header, name, value);
                            break;
                        }
                        field2 = new HttpField(header, name, value);
                        break;
                    }
                    case C_AUTHORITY: {
                        field2 = new AuthorityHttpField(value);
                        break;
                    }
                    case CONTENT_LENGTH: {
                        if ("0".equals(value)) {
                            field2 = CONTENT_LENGTH_0;
                            break;
                        }
                        field2 = new HttpField.LongValueHttpField(header, name, value);
                        break;
                    }
                    default: {
                        field2 = new HttpField(header, name, value);
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("decoded '{}' by {}/{}/{}", field2, nameIndex > 0 ? "IdxName" : (huffmanName ? "HuffName" : "LitName"), huffmanValue ? "HuffVal" : "LitVal", indexed ? "Idx" : "");
            }
            emitted = true;
            this._builder.emit(field2);
            if (!indexed) continue;
            this._context.add(field2);
        }
        return this._builder.build();
    }

    public static String toASCIIString(ByteBuffer buffer, int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            builder.append((char)(0x7F & buffer.get()));
        }
        return builder.toString();
    }

    public String toString() {
        return String.format("HpackDecoder@%x{%s}", this.hashCode(), this._context);
    }
}

