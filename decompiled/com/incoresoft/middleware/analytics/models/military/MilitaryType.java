/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.military;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum MilitaryType {
    tank(0),
    armored_vehicle(1),
    mrl(2);

    private static final Map<Integer, MilitaryType> idMapping;
    private final int id;

    public static MilitaryType getById(int id) {
        return idMapping.get(id);
    }

    private MilitaryType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    static {
        HashMap<Integer, MilitaryType> tempMappings = new HashMap<Integer, MilitaryType>();
        for (MilitaryType type : MilitaryType.values()) {
            if (tempMappings.containsKey(type.id)) {
                throw new RuntimeException(String.format("Duplicate item for id %d", type.id));
            }
            tempMappings.put(type.id, type);
        }
        idMapping = Collections.unmodifiableMap(tempMappings);
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) {
            if (o != null) {
                jsonSerializer.write(((MilitaryType)((Object)o)).getId());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)MilitaryType.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

