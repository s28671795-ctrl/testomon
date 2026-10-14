/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.hardware.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareAcceleration;
import java.lang.reflect.Type;

public class HardwareAccelerationSerializer
implements ObjectSerializer,
ObjectDeserializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object instanceof HardwareAcceleration) {
            serializer.write(((HardwareAcceleration)((Object)object)).getId());
        } else {
            serializer.write(null);
        }
    }

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return (T)((Object)HardwareAcceleration.lookup(parser.parseObject(String.class)));
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }
}

