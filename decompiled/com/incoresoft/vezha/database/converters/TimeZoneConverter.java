/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import java.time.ZoneId;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class TimeZoneConverter
implements Converter<String, ZoneId> {
    @Override
    public String to(ZoneId zoneId) {
        return zoneId != null ? zoneId.getId() : "UTC";
    }

    @Override
    public ZoneId from(String s2) {
        try {
            return ZoneId.of(s2);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ZoneId.systemDefault();
        }
    }

    @Override
    @NotNull
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    @NotNull
    public Class<ZoneId> toType() {
        return ZoneId.class;
    }
}

