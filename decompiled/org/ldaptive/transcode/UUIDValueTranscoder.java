/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import java.util.UUID;
import org.ldaptive.transcode.AbstractStringValueTranscoder;

public class UUIDValueTranscoder
extends AbstractStringValueTranscoder<UUID> {
    @Override
    public UUID decodeStringValue(String value) {
        return UUID.fromString(value);
    }

    @Override
    public String encodeStringValue(UUID value) {
        return value.toString();
    }

    @Override
    public Class<UUID> getType() {
        return UUID.class;
    }
}

