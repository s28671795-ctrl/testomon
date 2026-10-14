/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection.exceptions;

import com.incoresoft.dependencyinjection.exceptions.SystemIsUnavailableException;
import java.util.function.Supplier;

public interface UnavailabilityReason {
    public Supplier<SystemIsUnavailableException> getExceptionSupplier();
}

