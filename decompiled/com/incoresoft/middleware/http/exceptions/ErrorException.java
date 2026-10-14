/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.exceptions;

import com.incoresoft.middleware.http.errors.Error;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class ErrorException
extends RuntimeException {
    private final Error error;
    private final Object[] args;

    public ErrorException(@NotNull Error error, Object ... args2) {
        this.error = Objects.requireNonNull(error);
        this.args = args2;
    }

    public Error getError() {
        return this.error;
    }

    public Object[] getArgs() {
        return this.args;
    }
}

