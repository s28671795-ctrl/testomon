/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.poseestimation;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

@Schema(name="PoseEstimationType", description="<div><p>Pose estimation type enum.</p><p>Valid values are:</p><ul><li>0 - falling down</ul></div>", example="0")
public enum PoseEstimationType {
    fall_down(0),
    sitting(2);

    private final int id;

    private PoseEstimationType(int id) {
        this.id = id;
    }

    public static PoseEstimationType getById(int id) {
        return Arrays.stream(PoseEstimationType.values()).filter(it -> it.getId() == id).findAny().orElse(null);
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(Optional.ofNullable(object).map(it -> (PoseEstimationType)((Object)((Object)it))).map(PoseEstimationType::getId).orElse(0));
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return Optional.ofNullable(parser).map(v -> PoseEstimationType.getById(v.parseObject(Integer.TYPE))).orElse(null);
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

