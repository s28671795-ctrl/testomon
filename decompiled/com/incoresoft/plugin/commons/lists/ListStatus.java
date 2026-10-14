/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.lists;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

@Schema(name="ListItemStatus", description="<div><p>Objects types allowed in analytics.</p><p>Valid values are:</p><ul><li>0 - disabled<li>1 - enabled<li>2 - deleted</ul></div>", example="1")
public enum ListStatus {
    disabled(0),
    enabled(1),
    deleted(-1);

    private final int id;

    public static ListStatus getById(int id) {
        return Arrays.stream(ListStatus.values()).filter(it -> it.id == id).findFirst().orElse(enabled);
    }

    private ListStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static class Serializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)((Object)ListStatus.getById(parser.parseObject(Integer.class)));
        }

        @Override
        public int getFastMatchToken() {
            return 2;
        }

        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            if (object instanceof ListStatus) {
                serializer.write(((ListStatus)((Object)object)).getId());
            } else {
                serializer.write(null);
            }
        }
    }
}

