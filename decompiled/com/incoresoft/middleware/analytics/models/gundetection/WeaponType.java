/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.gundetection;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Schema(name="WeaponType", description="<div><p>Weapon types enum.</p><p>Valid values are:</p><ul><li>0 - gun<li>1 - rifle<li>2 - knife</ul></div>", example="1")
public enum WeaponType {
    gun(0),
    rifle(1),
    knife(2);

    private static final Map<Integer, WeaponType> idMapping;
    private final int id;

    public static WeaponType getById(int id) {
        return idMapping.get(id);
    }

    private WeaponType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    static {
        HashMap<Integer, WeaponType> tempMappings = new HashMap<Integer, WeaponType>();
        for (WeaponType type : WeaponType.values()) {
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
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
            if (o != null) {
                jsonSerializer.write(((WeaponType)((Object)o)).getId());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)WeaponType.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

