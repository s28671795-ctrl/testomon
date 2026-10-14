/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer
implements ObjectSerializer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy[ HH:mm:ss]");

    public static String serialize(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(FORMATTER);
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object != null) {
            serializer.write(((ZonedDateTime)object).format(FORMATTER));
        } else {
            serializer.write(null);
        }
    }
}

