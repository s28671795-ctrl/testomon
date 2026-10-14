/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.LdapUtils;
import org.ldaptive.transcode.ValueTranscoder;

public abstract class AbstractStringValueTranscoder<T>
implements ValueTranscoder<T> {
    @Override
    public T decodeBinaryValue(byte[] value) {
        return this.decodeStringValue(LdapUtils.utf8Encode(value));
    }

    @Override
    public byte[] encodeBinaryValue(T value) {
        return LdapUtils.utf8Encode(this.encodeStringValue(value));
    }
}

