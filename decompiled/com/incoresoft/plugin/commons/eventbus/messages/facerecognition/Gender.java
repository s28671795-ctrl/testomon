/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.facerecognition;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import java.io.IOException;
import java.lang.reflect.Type;

public enum Gender {
    female,
    male;


    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            String value = parser.parseObject(String.class);
            return (T)((Object)Gender.valueOf(value));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }

        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            if (object instanceof Gender) {
                serializer.write(((Gender)((Object)object)).name());
            } else {
                serializer.writeNull();
            }
        }

        static {
            SerializeConfig.getGlobalInstance().put((Type)((Object)Gender.class), new Serializer());
            ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)Gender.class), new Serializer());
        }
    }
}

