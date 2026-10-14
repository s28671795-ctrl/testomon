/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Crowd;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Heatmap;
import com.incoresoft.middleware.analytics.module.smartva.triggers.InZone;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Intrusion;
import com.incoresoft.middleware.analytics.module.smartva.triggers.ObjectCounter;
import com.incoresoft.middleware.analytics.module.smartva.triggers.OutOfZone;
import com.incoresoft.middleware.analytics.module.smartva.triggers.PresenceControl;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Trigger;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

@Schema(name="Trigger", description="Trigger types that are present in Smart VA analytics.</p>", example="in_zone")
public enum TriggerType {
    IN_ZONE(0, InZone.class),
    OUT_OF_ZONE(1, OutOfZone.class),
    OBJECT_COUNTER(2, ObjectCounter.class),
    PRESENCE_CONTROL(3, PresenceControl.class),
    INTRUSION(4, Intrusion.class),
    HEATMAP(5, Heatmap.class),
    CROWD(6, Crowd.class);

    private final int id;
    private final Class<? extends Trigger> triggerClass;

    public static TriggerType getById(int id) {
        return Arrays.stream(TriggerType.values()).filter(it -> it.getId() == id).findAny().orElse(IN_ZONE);
    }

    private TriggerType(int id, Class<? extends Trigger> triggerClass) {
        this.id = id;
        this.triggerClass = triggerClass;
    }

    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static TriggerType fromString(String value) {
        return TriggerType.valueOf(value.toUpperCase());
    }

    public int getId() {
        return this.id;
    }

    public Class<? extends Trigger> getTriggerClass() {
        return this.triggerClass;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
            jsonSerializer.write(((TriggerType)((Object)o)).name().toLowerCase());
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)TriggerType.valueOf(parser.parseObject(String.class).toUpperCase()));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

