/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import org.ldaptive.LdapUtils;
import org.ldaptive.transcode.ValueTranscoder;

public abstract class AbstractBinaryValueTranscoder<T>
implements ValueTranscoder<T> {
    @Override
    public T decodeStringValue(String value) {
        return this.decodeBinaryValue(LdapUtils.utf8Encode(value));
    }

    @Override
    public String encodeStringValue(T value) {
        return LdapUtils.utf8Encode(this.encodeBinaryValue(value));
    }
}

