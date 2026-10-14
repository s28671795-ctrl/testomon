/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;

public enum AnalyticsModes {
    alert,
    stats;


    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)AnalyticsModes.valueOf(parser.parseObject(String.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }

        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            if (object instanceof AnalyticsModes) {
                serializer.write(((AnalyticsModes)((Object)object)).name());
            } else {
                serializer.write(null);
            }
        }
    }
}

