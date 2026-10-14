/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.serializers;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.incoresoft.user.UserTimeFormat;
import java.io.IOException;
import java.lang.reflect.Type;

public class UserTimeFormatSerializerDeserializer
implements ObjectSerializer,
ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return (T)((Object)UserTimeFormat.findByFormat(parser.parseObject(String.class)));
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.write(((UserTimeFormat)((Object)object)).getFormat());
    }
}

