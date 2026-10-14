/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.face.gender;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.incoresoft.middleware.analytics.models.face.gender.Gender;
import java.lang.reflect.Type;

public class GenderSerializer
implements ObjectDeserializer,
ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) {
        if (o != null) {
            jsonSerializer.write(((Gender)((Object)o)).getId());
        } else {
            jsonSerializer.write(null);
        }
    }

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Integer id = parser.parseObject(Integer.class);
        return (T)((Object)(id == null ? null : Gender.getById(id)));
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }
}

