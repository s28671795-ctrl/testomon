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

@Schema(name="GenderAttributes", description="<div><p>Gender attributes enum.</p><p>Valid values are:</p><ul><li>0 - unknown<li>1 - male<li>2 - female</ul></div>", example="2")
public enum GenderAttributes {
    unknown(0),
    male(1),
    female(2);

    private final int id;

    private GenderAttributes(int id) {
        this.id = id;
    }

    public static GenderAttributes getById(int id) {
        return Arrays.stream(GenderAttributes.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(Optional.ofNullable(object).map(it -> (GenderAttributes)((Object)((Object)it))).map(GenderAttributes::getId).orElse(0));
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)Optional.ofNullable(parser).map(v -> GenderAttributes.getById(v.parseObject(Integer.TYPE))).orElse(unknown));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

