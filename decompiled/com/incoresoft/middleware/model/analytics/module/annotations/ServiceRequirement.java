/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.annotations;

import com.incoresoft.middleware.analytics.AnalyticsTypes;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ServiceRequirement {
    public AnalyticsTypes value();
}

