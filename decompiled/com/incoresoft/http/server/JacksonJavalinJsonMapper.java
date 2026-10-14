/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.server;

import com.fasterxml.jackson.databind.JavaType;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.exceptions.http.HttpBodyProcessingException;
import io.javalin.json.JsonMapper;
import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;

public class JacksonJavalinJsonMapper
implements JsonMapper {
    @Override
    @NotNull
    public String toJsonString(@NotNull Object obj, @NotNull Type type) {
        try {
            return Serialization.objectMapper().writer().writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new HttpBodyProcessingException(e);
        }
    }

    @Override
    @NotNull
    public <T> T fromJsonString(@NotNull String json, @NotNull Type targetClass) {
        try {
            JavaType javaType = Serialization.objectMapper().getTypeFactory().constructType(targetClass);
            return Serialization.objectMapper().readerFor(javaType).readValue(json);
        }
        catch (Exception e) {
            throw new HttpBodyProcessingException(e);
        }
    }
}

