/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.model;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import java.io.IOException;
import java.lang.reflect.Type;

public enum LPRLicenseType {
    LITE,
    BASIC,
    MEDIUM,
    FULL;


    public int getLevelValue() {
        return this.ordinal();
    }

    static {
        SerializeConfig.getGlobalInstance().put((Type)((Object)LPRLicenseType.class), new Serializer());
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)LPRLicenseType.class), new Deserializer());
    }

    public static class Serializer
    implements ObjectSerializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            if (object instanceof LPRLicenseType) {
                serializer.write(((LPRLicenseType)((Object)object)).name().toLowerCase());
            } else {
                serializer.writeNull();
            }
        }
    }

    public static class Deserializer
    implements ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            String value = parser.parseObject(String.class);
            if (value == null) {
                return null;
            }
            return (T)((Object)LPRLicenseType.valueOf(value.toUpperCase()));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

