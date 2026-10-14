/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.attributes;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

@Schema(name="AgeAttributes", description="<div><p>Age attributes enum.</p><p>Valid values are:</p><ul><li>0 - unknown<li>1 - young<li>2 - teenager<li>3 - adult<li>4 - old</ul></div>", example="3")
public enum AgeAttributes {
    unknown(0),
    young(1),
    teenager(2),
    adult(3),
    old(4);

    private final int id;

    private AgeAttributes(int id) {
        this.id = id;
    }

    public static AgeAttributes getById(int id) {
        return Arrays.stream(AgeAttributes.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(Optional.ofNullable(object).map(it -> (AgeAttributes)((Object)((Object)it))).map(AgeAttributes::getId).orElse(0));
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)Optional.ofNullable(parser).map(v -> AgeAttributes.getById(v.parseObject(Integer.TYPE))).orElse(unknown));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

