/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.annotations;

import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Module {
    public String analyticPluginName();

    public ServiceRequirement[] serviceRequirements() default {};
}

