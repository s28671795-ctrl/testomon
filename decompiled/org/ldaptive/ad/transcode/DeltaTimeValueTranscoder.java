/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.transcode;

import org.ldaptive.transcode.AbstractStringValueTranscoder;

public class DeltaTimeValueTranscoder
extends AbstractStringValueTranscoder<Long> {
    private static final long ONE_HUNDRED_NANOSECOND_INTERVAL = 10000L;

    @Override
    public Long decodeStringValue(String value) {
        return -Long.parseLong(value) / 10000L;
    }

    @Override
    public String encodeStringValue(Long value) {
        return String.valueOf(-value.longValue() * 10000L);
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }
}

