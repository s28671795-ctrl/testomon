/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.exceptions;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class ServerNotFoundException
extends RuntimeException {
    private final Analytics analytics;

    public ServerNotFoundException(@NotNull Analytics analytics) {
        this.analytics = Objects.requireNonNull(analytics);
    }

    @Override
    public String getMessage() {
        return String.format("Server supporting analytics %d of type %s not found", this.analytics.getId(), this.analytics.getModule().getAnalyticsTypes().stream().map(AnalyticsTypes::getName).collect(Collectors.joining(", ")));
    }

    public Analytics getAnalytics() {
        return this.analytics;
    }
}

