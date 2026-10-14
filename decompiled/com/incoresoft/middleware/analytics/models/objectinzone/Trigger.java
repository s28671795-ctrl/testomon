/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.objectinzone;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

@Schema(name="Trigger", description="<div><p>Array of IDs of triggers allowed in analytics for searching events.</p><p>Valid values are:</p><ul><li>0 - In zone<li>1 - Out of zone<li>2 - Constantly present<li>3 - Object Absent in Zone<li>4 - Object Counter<li>5 - Presence Control<li>6 - Intrusion<li>7 - Heatmap<li>8 - Crowd</ul></div>", example="0")
public enum Trigger {
    IN_ZONE(0),
    OUT_ZONE(1),
    ABSENT_ANY(2),
    AT_LEAST_ONCE(3),
    COUNTER(4),
    PRESENCE_CONTROL(5),
    INTRUSION(6),
    HEATMAP(7),
    CROWD(8);

    private final int id;

    public static Trigger getById(int id) {
        return Arrays.stream(Trigger.values()).filter(it -> it.getId() == id).findAny().orElse(IN_ZONE);
    }

    private Trigger(int id) {
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
            jsonSerializer.write(Optional.ofNullable(o).map(it -> (Trigger)((Object)((Object)it))).map(Trigger::getId).orElse(0));
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            try {
                return (T)((Object)Trigger.getById(parser.parseObject(Integer.class)));
            }
            catch (Exception e) {
                return (T)((Object)IN_ZONE);
            }
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }
    }
}

