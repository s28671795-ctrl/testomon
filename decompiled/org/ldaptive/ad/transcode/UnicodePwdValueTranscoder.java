/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.transcode;

import java.nio.charset.StandardCharsets;
import org.ldaptive.transcode.AbstractBinaryValueTranscoder;

public class UnicodePwdValueTranscoder
extends AbstractBinaryValueTranscoder<String> {
    @Override
    public String decodeBinaryValue(byte[] value) {
        String pwd = new String(value, StandardCharsets.UTF_16LE);
        if (pwd.length() < 2) {
            throw new IllegalArgumentException("unicodePwd must be at least 2 characters long");
        }
        return pwd.substring(1, pwd.length() - 1);
    }

    @Override
    public byte[] encodeBinaryValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot encode null value");
        }
        String pwd = String.format("\"%s\"", value);
        return pwd.getBytes(StandardCharsets.UTF_16LE);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}

