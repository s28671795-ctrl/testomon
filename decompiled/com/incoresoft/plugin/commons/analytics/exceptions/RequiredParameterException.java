/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.exceptions;

import com.incoresoft.plugin.commons.analytics.exceptions.AnalyticsException;

public class RequiredParameterException
extends AnalyticsException {
    private final String parameterName;

    public RequiredParameterException(String parameterName) {
        this.parameterName = parameterName;
    }

    public RequiredParameterException(String parameterName, String message) {
        super(message);
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return this.parameterName;
    }
}

