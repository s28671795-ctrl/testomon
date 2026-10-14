/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection.exceptions;

import com.incoresoft.middleware.http.errors.Error;

public abstract class SystemIsUnavailableException
extends RuntimeException {
    public abstract Error getError();
}

