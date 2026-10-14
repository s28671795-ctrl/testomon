/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva;

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

public enum SmartVaObjectType {
    PERSON(0),
    BICYCLE(1),
    CAR(2),
    MOTORBIKE(3),
    BUS(4),
    TRUCK(5);

    private static final Map<Integer, SmartVaObjectType> typesMap;
    private final int id;

    public static SmartVaObjectType getById(Integer id) {
        return typesMap.get(id);
    }

    private SmartVaObjectType(int id) {
        this.id = id;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    public int getId() {
        return this.id;
    }

    static {
        typesMap = new HashMap(Arrays.stream(SmartVaObjectType.values()).collect(Collectors.toMap(SmartVaObjectType::getId, Function.identity())));
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
            if (o != null) {
                jsonSerializer.write(((SmartVaObjectType)((Object)o)).name().toLowerCase());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)SmartVaObjectType.valueOf(parser.parseObject(String.class).toUpperCase()));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

