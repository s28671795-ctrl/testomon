/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.traffic;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public enum TrafficType {
    people(0),
    bike(1),
    car(2),
    motorcycle(3),
    bus(4),
    truck(5);

    private static final Map<Integer, TrafficType> typesMap;
    private final int id;

    public static TrafficType getById(Integer id) {
        return typesMap.get(id);
    }

    public static TrafficType getById(Byte id) {
        return id == null ? null : TrafficType.getById(id.intValue());
    }

    private TrafficType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    static {
        typesMap = new HashMap<Integer, TrafficType>();
        for (TrafficType type : TrafficType.values()) {
            if (typesMap.get(type.getId()) != null) {
                throw new RuntimeException("duplicate id: " + type.getId());
            }
            typesMap.put(type.getId(), type);
        }
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) {
            if (o != null) {
                jsonSerializer.write(((TrafficType)((Object)o)).getId());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)TrafficType.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

