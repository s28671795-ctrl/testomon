/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.motion;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

@Schema(name="MotionRuleType", description="Motion rule type enum", example="2")
public enum MotionRuleType {
    intruded_object(0),
    loitering_object(1),
    left_object(2),
    disappeared_object(3),
    line_crossing_object(4),
    camera_sabotage(5);

    private final int id;

    public static MotionRuleType getById(int id) {
        return Arrays.stream(MotionRuleType.values()).filter(it -> it.getId() == id).findAny().orElse(null);
    }

    private MotionRuleType(int id) {
        this.id = id;
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
                jsonSerializer.write(((MotionRuleType)((Object)o)).getId());
            } else {
                jsonSerializer.write(null);
            }
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)MotionRuleType.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

