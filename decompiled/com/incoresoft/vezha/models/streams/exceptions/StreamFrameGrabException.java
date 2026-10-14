/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.exceptions;

import org.jetbrains.annotations.NotNull;

public class StreamFrameGrabException
extends RuntimeException {
    public StreamFrameGrabException(@NotNull String message) {
        super(message);
    }

    public StreamFrameGrabException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}

