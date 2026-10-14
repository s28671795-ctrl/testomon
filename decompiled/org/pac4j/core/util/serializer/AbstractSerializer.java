/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.serializer;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.pac4j.core.util.serializer.Serializer;

public abstract class AbstractSerializer
implements Serializer {
    @Override
    public final String serializeToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return this.internalSerializeToString(obj);
    }

    protected String internalSerializeToString(Object obj) {
        return Base64.getEncoder().encodeToString(this.internalSerializeToBytes(obj));
    }

    @Override
    public final byte[] serializeToBytes(Object obj) {
        if (obj == null) {
            return null;
        }
        return this.internalSerializeToBytes(obj);
    }

    protected byte[] internalSerializeToBytes(Object obj) {
        return this.internalSerializeToString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public final Object deserializeFromString(String encoded) {
        if (encoded == null) {
            return null;
        }
        return this.internalDeserializeFromString(encoded);
    }

    protected Object internalDeserializeFromString(String encoded) {
        byte[] enc = Base64.getDecoder().decode(encoded);
        return this.internalDeserializeFromBytes(enc);
    }

    @Override
    public final Object deserializeFromBytes(byte[] encoded) {
        if (encoded == null) {
            return null;
        }
        return this.internalDeserializeFromBytes(encoded);
    }

    protected Object internalDeserializeFromBytes(byte[] encoded) {
        return this.internalDeserializeFromString(new String(encoded, StandardCharsets.UTF_8));
    }
}

