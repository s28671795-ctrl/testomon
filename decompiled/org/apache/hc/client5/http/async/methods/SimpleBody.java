/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.async.methods;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.util.Args;

public final class SimpleBody {
    private final byte[] bodyAsBytes;
    private final String bodyAsText;
    private final ContentType contentType;

    SimpleBody(byte[] bodyAsBytes, String bodyAsText, ContentType contentType) {
        this.bodyAsBytes = bodyAsBytes;
        this.bodyAsText = bodyAsText;
        this.contentType = contentType;
    }

    static SimpleBody create(String body2, ContentType contentType) {
        Args.notNull(body2, "Body");
        if (body2.length() > 2048) {
            return new SimpleBody(null, body2, contentType);
        }
        Charset charset = (contentType != null ? contentType : ContentType.DEFAULT_TEXT).getCharset();
        byte[] bytes = body2.getBytes(charset != null ? charset : StandardCharsets.US_ASCII);
        return new SimpleBody(bytes, null, contentType);
    }

    static SimpleBody create(byte[] body2, ContentType contentType) {
        Args.notNull(body2, "Body");
        return new SimpleBody(body2, null, contentType);
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public byte[] getBodyBytes() {
        if (this.bodyAsBytes != null) {
            return this.bodyAsBytes;
        }
        if (this.bodyAsText != null) {
            Charset charset = (this.contentType != null ? this.contentType : ContentType.DEFAULT_TEXT).getCharset();
            return this.bodyAsText.getBytes(charset != null ? charset : StandardCharsets.US_ASCII);
        }
        return null;
    }

    public String getBodyText() {
        if (this.bodyAsBytes != null) {
            Charset charset = (this.contentType != null ? this.contentType : ContentType.DEFAULT_TEXT).getCharset();
            return new String(this.bodyAsBytes, charset != null ? charset : StandardCharsets.US_ASCII);
        }
        return this.bodyAsText;
    }

    public boolean isText() {
        return this.bodyAsText != null;
    }

    public boolean isBytes() {
        return this.bodyAsBytes != null;
    }

    public String toString() {
        return "SimpleBody{content length=" + (this.bodyAsBytes != null ? Integer.valueOf(this.bodyAsBytes.length) : "chunked") + ", content type=" + this.contentType + "}";
    }
}

