/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.javalin.json.JsonMapper;
import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;

class JsonMapperImpl
implements JsonMapper {
    JsonMapperImpl() {
    }

    @Override
    @NotNull
    public String toJsonString(@NotNull Object obj, @NotNull Type type) {
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.WriteMapNullValue);
    }

    @Override
    @NotNull
    public <T> T fromJsonString(@NotNull String jsonValue, @NotNull Type targetClass) {
        return JSON.parseObject(jsonValue, targetClass, new Feature[0]);
    }
}

