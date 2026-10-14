/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.version;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.incoresoft.version.models.MinorVersion;
import java.lang.reflect.Type;

public class MinorVersionSerializer
implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object instanceof MinorVersion) {
            serializer.write(object.toString());
        } else {
            serializer.write(null);
        }
    }
}

