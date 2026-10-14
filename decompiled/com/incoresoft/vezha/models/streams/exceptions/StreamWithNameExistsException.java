/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.exceptions;

import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

public class StreamWithNameExistsException
extends RuntimeException {
    private final Set<String> names;

    public StreamWithNameExistsException(@NotNull Set<String> names) {
        this.names = Objects.requireNonNull(names);
    }

    public Set<String> getNames() {
        return this.names;
    }
}

