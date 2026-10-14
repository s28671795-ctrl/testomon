/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.serialization.erroranswer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import java.io.IOException;

public class ErrorAnswerSerializer
extends JsonSerializer<ErrorAnswer> {
    @Override
    public void serialize(ErrorAnswer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getErrors());
    }
}

