/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.exceptions;

import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsException;

public class AnalyticsIsNotStoppedException
extends AnalyticsException {
    private final int id;

    public AnalyticsIsNotStoppedException(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

