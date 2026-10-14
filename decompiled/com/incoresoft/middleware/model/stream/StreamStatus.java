/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.stream;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

public enum StreamStatus {
    online(1),
    offline(0),
    deleted(-1);

    private final int id;

    private StreamStatus(int id) {
        this.id = id;
    }

    public static StreamStatus getById(int id) {
        return Arrays.stream(StreamStatus.values()).filter(item -> item.id == id).findFirst().orElse(null);
    }

    @JsonValue
    final int value() {
        return this.id;
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            serializer.write(((StreamStatus)((Object)object)).getId());
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)StreamStatus.getById(parser.parseObject(Integer.TYPE)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

