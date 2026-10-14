/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.transcode;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.ldaptive.transcode.AbstractStringValueTranscoder;

public class FileTimeValueTranscoder
extends AbstractStringValueTranscoder<ZonedDateTime> {
    private static final long ERA_OFFSET = 11644473600000L;
    private static final long ONE_HUNDRED_NANOSECOND_INTERVAL = 10000L;

    @Override
    public ZonedDateTime decodeStringValue(String value) {
        Instant i = Instant.ofEpochMilli(Long.parseLong(value) / 10000L - 11644473600000L);
        return ZonedDateTime.ofInstant(i, ZoneId.of("Z"));
    }

    @Override
    public String encodeStringValue(ZonedDateTime value) {
        return String.valueOf((value.toInstant().toEpochMilli() + 11644473600000L) * 10000L);
    }

    @Override
    public Class<ZonedDateTime> getType() {
        return ZonedDateTime.class;
    }
}

