/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.genderage;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

public enum Age {
    unknown(0, 0, 0),
    AGE_1_5(1, 1, 5),
    AGE_6_12(2, 6, 12),
    AGE_13_16(3, 13, 16),
    AGE_17_21(4, 17, 21),
    AGE_22_30(5, 22, 30),
    AGE_31_35(6, 31, 35),
    AGE_36_40(7, 36, 40),
    AGE_41_45(8, 41, 45),
    AGE_46_50(9, 46, 50),
    AGE_51_55(10, 51, 55),
    AGE_56_60(11, 56, 60),
    AGE_61_65(12, 61, 65),
    AGE_66_80(13, 66, 80),
    AGE_81_100(14, 81, 100);

    private final int id;
    private final int firstAge;
    private final int lastAge;

    private Age(int id, int firstAge, int lastAge) {
        this.id = id;
        this.firstAge = firstAge;
        this.lastAge = lastAge;
    }

    public static Age getById(int id) {
        return Arrays.stream(Age.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getFirstAge() {
        return this.firstAge;
    }

    public int getLastAge() {
        return this.lastAge;
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(Optional.ofNullable(object).map(it -> (Age)((Object)((Object)it))).map(Age::getId).orElse(0));
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)Optional.ofNullable(parser).map(v -> Age.getById(v.parseObject(Integer.TYPE))).orElse(unknown));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

