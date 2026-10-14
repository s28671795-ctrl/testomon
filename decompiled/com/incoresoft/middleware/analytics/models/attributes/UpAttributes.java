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

@Schema(name="UpAttributes", description="<div><p>Up attributes enum.</p><p>Valid values are:</p><ul><li>0 - unknown<li>1 - black<li>2 - white<li>3 - red<li>4 - purple<li>5 - yellow<li>6 - gray<li>7 - blue<li>8 - green</ul></div>", example="3")
public enum UpAttributes {
    unknown(0),
    black(1),
    white(2),
    pink(3),
    purple(4),
    yellow(5),
    gray(6),
    blue(7),
    green(8),
    brown(9);

    private final int id;

    private UpAttributes(int id) {
        this.id = id;
    }

    public static UpAttributes getById(int id) {
        return Arrays.stream(UpAttributes.values()).filter(it -> it.getId() == id).findAny().orElse(unknown);
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(Optional.ofNullable(object).map(it -> (UpAttributes)((Object)((Object)it))).map(UpAttributes::getId).orElse(0));
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)Optional.ofNullable(parser).map(v -> UpAttributes.getById(v.parseObject(Integer.TYPE))).orElse(unknown));
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

