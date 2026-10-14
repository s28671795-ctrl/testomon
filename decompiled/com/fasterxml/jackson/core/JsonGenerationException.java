/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.exc.StreamWriteException;

public class JsonGenerationException
extends StreamWriteException {
    private static final long serialVersionUID = 123L;

    @Deprecated
    public JsonGenerationException(Throwable rootCause) {
        super(rootCause, null);
    }

    @Deprecated
    public JsonGenerationException(String msg) {
        super(msg, (JsonGenerator)null);
    }

    @Deprecated
    public JsonGenerationException(String msg, Throwable rootCause) {
        super(msg, rootCause, null);
    }

    public JsonGenerationException(Throwable rootCause, JsonGenerator g2) {
        super(rootCause, g2);
    }

    public JsonGenerationException(String msg, JsonGenerator g2) {
        super(msg, g2);
        this._processor = g2;
    }

    public JsonGenerationException(String msg, Throwable rootCause, JsonGenerator g2) {
        super(msg, rootCause, g2);
        this._processor = g2;
    }

    @Override
    public JsonGenerationException withGenerator(JsonGenerator g2) {
        this._processor = g2;
        return this;
    }

    @Override
    public JsonGenerator getProcessor() {
        return this._processor;
    }
}

