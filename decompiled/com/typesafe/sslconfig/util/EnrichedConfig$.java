/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.config.Config;
import com.typesafe.sslconfig.util.EnrichedConfig;

public final class EnrichedConfig$ {
    public static final EnrichedConfig$ MODULE$ = new EnrichedConfig$();

    public EnrichedConfig apply(Config underlying) {
        return new EnrichedConfig(underlying);
    }

    private EnrichedConfig$() {
    }
}

