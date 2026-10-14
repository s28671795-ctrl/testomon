/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;

public enum RuleType {
    ZONE(0),
    ZONE_WITH_DIRECTION(1),
    PATH(2),
    LINE(3);

    private final int id;

    public static RuleType getById(int id) {
        return Arrays.stream(RuleType.values()).filter(it -> it.getId() == id).findAny().orElseThrow();
    }

    private RuleType(int id) {
        this.id = id;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    public int getId() {
        return this.id;
    }

    public static class RuleTypeSerializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(((RuleType)((Object)object)).name().toLowerCase());
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)RuleType.valueOf(parser.parseObject(String.class).toUpperCase()));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

