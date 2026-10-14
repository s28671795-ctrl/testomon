/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.incoresoft.plugins.models.PluginType;
import java.io.IOException;
import java.lang.reflect.Type;

public class FastjsonPluginTypeSerializer
implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object instanceof PluginType) {
            out.writeString(object.toString());
        } else {
            out.writeNull();
        }
    }
}

