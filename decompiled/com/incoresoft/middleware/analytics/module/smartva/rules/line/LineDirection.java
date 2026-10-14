/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.rules.line;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;

public enum LineDirection {
    UP,
    DOWN,
    BOTH;


    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            if (object instanceof LineDirection) {
                serializer.write(((LineDirection)((Object)object)).name().toLowerCase());
            } else {
                serializer.writeNull();
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            String value = parser.parseObject(String.class);
            if (value == null) {
                return null;
            }
            return (T)((Object)LineDirection.valueOf(value.toUpperCase()));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

