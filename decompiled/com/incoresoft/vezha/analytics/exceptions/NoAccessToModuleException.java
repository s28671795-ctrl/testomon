/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.exceptions;

import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsException;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class NoAccessToModuleException
extends AnalyticsException {
    private final String type;

    public NoAccessToModuleException(@NotNull String type) {
        this.type = Objects.requireNonNull(type);
    }

    public String getType() {
        return this.type;
    }
}

