/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.alpr;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

@Schema(name="Color", description="<div><p>Vehicle plates enum.</p><p>Valid values are:</p><ul><li>0 - PLATE<li>1 - CAR<li>2 - MOTORBIKE<li>3 - BUS<li>4 - TRUCK<li>5 - VAN<li>6 - PICKUP<li>7 - SUV<li>8 - ADR</ul></div>", example="1")
public enum VehicleType {
    PLATE(0),
    CAR(1),
    MOTORBIKE(2),
    BUS(3),
    TRUCK(4),
    VAN(5),
    PICKUP(6),
    SUV(7),
    ADR(8);

    private final int id;

    private VehicleType(int id) {
        this.id = id;
    }

    public static VehicleType getById(int id) {
        return Arrays.stream(VehicleType.values()).filter(it -> it.getId() == id).findAny().orElse(null);
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
            if (o != null) {
                jsonSerializer.write(((VehicleType)((Object)o)).getId());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)VehicleType.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

