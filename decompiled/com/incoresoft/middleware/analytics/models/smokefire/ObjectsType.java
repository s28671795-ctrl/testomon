/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smokefire;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.util.Arrays;

@Schema(name="ObjectsType", description="<div><p>ObjectsType enum.</p><p>Valid values are:</p><ul><li>0 - smoke<li>1 - fire</ul></div>", example="1")
public enum ObjectsType {
    smoke(0),
    fire(1);

    private final int id;

    public static ObjectsType getById(int id) {
        return Arrays.stream(ObjectsType.values()).filter(it -> it.getId() == id).findAny().orElse(null);
    }

    private ObjectsType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) {
            if (o != null) {
                jsonSerializer.write(((ObjectsType)((Object)o)).getId());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)ObjectsType.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

