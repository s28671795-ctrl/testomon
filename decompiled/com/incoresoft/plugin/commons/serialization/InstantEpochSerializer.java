/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.serialization;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;

public class InstantEpochSerializer
implements ObjectSerializer,
ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Long epochMilli = parser.parseObject(Long.TYPE);
        return (T)(epochMilli != null ? Instant.ofEpochMilli(epochMilli) : null);
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object instanceof Instant) {
            serializer.write(((Instant)object).toEpochMilli());
        } else {
            serializer.write(null);
        }
    }
}

