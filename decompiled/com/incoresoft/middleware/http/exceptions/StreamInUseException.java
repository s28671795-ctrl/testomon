/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

public class StreamInUseException
extends RuntimeException {
    public StreamInUseException(String message) {
        super(message);
    }

    public StreamInUseException(Collection<Integer> analyticsIds) {
        this(String.format("Stream is being used by analytics %s", analyticsIds.stream().map(String::valueOf).collect(Collectors.joining(","))));
    }
}

