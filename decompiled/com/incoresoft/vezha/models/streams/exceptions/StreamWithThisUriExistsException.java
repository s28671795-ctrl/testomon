/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.exceptions;

import java.net.URI;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class StreamWithThisUriExistsException
extends RuntimeException {
    private final URI uri;

    public StreamWithThisUriExistsException(@NotNull URI uri) {
        this.uri = Objects.requireNonNull(uri);
    }

    public URI getUri() {
        return this.uri;
    }
}

