/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.Utils.StringUtils;
import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class ZonedDateTimeDeserializer
implements ObjectDeserializer {
    private static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy[ HH:mm:ss][ z]").parseDefaulting(ChronoField.HOUR_OF_DAY, 0L).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0L).parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0L).toFormatter().withZone(ZoneId.systemDefault());

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String date = parser.parseObject(String.class);
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return (T)ZonedDateTime.parse(date, dateTimeFormatter);
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }
}

