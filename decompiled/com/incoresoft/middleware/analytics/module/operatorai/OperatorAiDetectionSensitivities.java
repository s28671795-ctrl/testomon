/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.operatorai;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public enum OperatorAiDetectionSensitivities {
    LOW,
    NORMAL,
    HIGH;


    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)OperatorAiDetectionSensitivities.valueOf(parser.parseObject(String.class).toUpperCase()));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }

        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            if (object != null) {
                serializer.write(((OperatorAiDetectionSensitivities)((Object)object)).name().toLowerCase());
            } else {
                serializer.writeNull();
            }
        }
    }
}

