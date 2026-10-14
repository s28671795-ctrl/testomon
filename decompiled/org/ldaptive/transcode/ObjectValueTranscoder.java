/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.LdapUtils;
import org.ldaptive.transcode.ValueTranscoder;

public class ObjectValueTranscoder
implements ValueTranscoder<Object> {
    @Override
    public Object decodeStringValue(String value) {
        return value;
    }

    @Override
    public Object decodeBinaryValue(byte[] value) {
        return value;
    }

    @Override
    public String encodeStringValue(Object value) {
        return value.toString();
    }

    @Override
    public byte[] encodeBinaryValue(Object value) {
        return LdapUtils.utf8Encode(this.encodeStringValue(value));
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }
}

