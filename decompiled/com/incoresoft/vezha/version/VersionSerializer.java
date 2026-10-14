/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.version;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.github.zafarkhaja.semver.Version;
import java.lang.reflect.Type;

public class VersionSerializer
implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object instanceof Version) {
            serializer.write(object.toString());
        } else {
            serializer.write(null);
        }
    }
}

