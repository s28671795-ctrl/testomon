/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.HttpFieldPreEncoder;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http2.hpack.HpackContext;
import org.eclipse.jetty.http2.hpack.HpackEncoder;
import org.eclipse.jetty.http2.hpack.Huffman;
import org.eclipse.jetty.http2.hpack.NBitInteger;
import org.eclipse.jetty.util.BufferUtil;

public class HpackFieldPreEncoder
implements HttpFieldPreEncoder {
    @Override
    public HttpVersion getHttpVersion() {
        return HttpVersion.HTTP_2;
    }

    @Override
    public byte[] getEncodedField(HttpHeader header, String name, String value) {
        int bits;
        boolean huffman;
        boolean notIndexed = HpackEncoder.DO_NOT_INDEX.contains((Object)header);
        ByteBuffer buffer = BufferUtil.allocate(name.length() + value.length() + 10);
        BufferUtil.clearToFill(buffer);
        if (notIndexed) {
            boolean neverIndex = HpackEncoder.NEVER_INDEX.contains((Object)header);
            huffman = !HpackEncoder.DO_NOT_HUFFMAN.contains((Object)header);
            buffer.put(neverIndex ? (byte)16 : 0);
            bits = 4;
        } else if (header == HttpHeader.CONTENT_LENGTH && value.length() > 1) {
            buffer.put((byte)0);
            huffman = true;
            bits = 4;
        } else {
            buffer.put((byte)64);
            huffman = !HpackEncoder.DO_NOT_HUFFMAN.contains((Object)header);
            bits = 6;
        }
        int nameIdx = HpackContext.staticIndex(header);
        if (nameIdx > 0) {
            NBitInteger.encode(buffer, bits, nameIdx);
        } else {
            buffer.put((byte)-128);
            NBitInteger.encode(buffer, 7, Huffman.octetsNeededLC(name));
            Huffman.encodeLC(buffer, name);
        }
        HpackEncoder.encodeValue(buffer, huffman, value);
        BufferUtil.flipToFlush(buffer, 0);
        return BufferUtil.toArray(buffer);
    }
}

