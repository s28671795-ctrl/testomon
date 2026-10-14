/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.serialization;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.incoresoft.http.serialization.erroranswer.ErrorAnswerSerializer;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;

public class JacksonSerializersInitializer {
    private JacksonSerializersInitializer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void init() {
        Serialization.objectMapper().registerModule(JacksonSerializersInitializer.errorAnswerModule());
    }

    private static Module errorAnswerModule() {
        return new SimpleModule().addSerializer(ErrorAnswer.class, new ErrorAnswerSerializer());
    }
}

