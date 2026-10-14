/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.incoresoft.middleware.model.stream.Codec;
import org.jooq.impl.EnumConverter;

public class CodecConverter
extends EnumConverter<String, Codec> {
    public CodecConverter() {
        super(String.class, Codec.class);
    }
}

