/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.compress.archivers.zip;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.commons.compress.archivers.zip.NioZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.io.Charsets;

public abstract class ZipEncodingHelper {
    static final ZipEncoding ZIP_ENCODING_UTF_8 = ZipEncodingHelper.getZipEncoding(CharsetNames.UTF_8);

    public static ZipEncoding getZipEncoding(Charset charset) {
        Charset actual = Charsets.toCharset(charset);
        boolean useReplacement = ZipEncodingHelper.isUTF8(actual);
        return new NioZipEncoding(actual, useReplacement);
    }

    public static ZipEncoding getZipEncoding(String name) {
        Charset charset = Charset.defaultCharset();
        try {
            charset = Charsets.toCharset(name);
        }
        catch (UnsupportedCharsetException unsupportedCharsetException) {
            // empty catch block
        }
        boolean useReplacement = ZipEncodingHelper.isUTF8(charset.name());
        return new NioZipEncoding(charset, useReplacement);
    }

    static ByteBuffer growBufferBy(ByteBuffer buffer, int increment) {
        buffer.limit(buffer.position());
        buffer.rewind();
        ByteBuffer on = ByteBuffer.allocate(buffer.capacity() + increment);
        on.put(buffer);
        return on;
    }

    static boolean isUTF8(Charset charset) {
        return ZipEncodingHelper.isUTF8Alias(Charsets.toCharset(charset).name());
    }

    static boolean isUTF8(String charsetName) {
        return ZipEncodingHelper.isUTF8Alias(charsetName != null ? charsetName : Charset.defaultCharset().name());
    }

    private static boolean isUTF8Alias(String actual) {
        return StandardCharsets.UTF_8.name().equalsIgnoreCase(actual) || StandardCharsets.UTF_8.aliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(actual));
    }
}

