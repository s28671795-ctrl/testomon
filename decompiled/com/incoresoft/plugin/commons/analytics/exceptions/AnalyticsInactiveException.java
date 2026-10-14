/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.exceptions;

import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsException;
import java.util.Set;

public class AnalyticsInactiveException
extends AnalyticsException {
    Set<Integer> cameraIds;

    public AnalyticsInactiveException(Set<Integer> cameraIds) {
        this.cameraIds = cameraIds;
    }

    public Set<Integer> getCameraIds() {
        return this.cameraIds;
    }
}

