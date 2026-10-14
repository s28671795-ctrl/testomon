/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.exceptions;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class StreamGroupWithThisNameExistsException
extends RuntimeException {
    private final String name;

    public StreamGroupWithThisNameExistsException(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }
}

