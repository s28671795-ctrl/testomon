/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.objectinzone;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ObjectType {
    person(0),
    bicycle(1),
    car(2),
    motorbike(3),
    bus(4),
    truck(5),
    animal(6),
    stuff(7);

    private static final Map<Integer, ObjectType> typesMap;
    private final int id;

    public static ObjectType getById(Integer id) {
        return typesMap.get(id);
    }

    private ObjectType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    static {
        typesMap = new HashMap(Arrays.stream(ObjectType.values()).collect(Collectors.toMap(ObjectType::getId, Function.identity())));
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
            if (o != null) {
                jsonSerializer.write(((ObjectType)((Object)o)).getId());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)ObjectType.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

