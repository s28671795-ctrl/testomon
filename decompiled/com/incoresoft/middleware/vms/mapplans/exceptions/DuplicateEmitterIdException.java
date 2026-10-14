/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.mapplans.exceptions;

import java.util.UUID;

public class DuplicateEmitterIdException
extends RuntimeException {
    private final UUID id;

    public DuplicateEmitterIdException(UUID id) {
        super("Emitter with id " + String.valueOf(id) + " is already registered");
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}

