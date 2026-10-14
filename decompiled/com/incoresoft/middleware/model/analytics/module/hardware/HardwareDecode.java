/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.hardware;

import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum HardwareDecode {
    cpu(""),
    intel("intel"),
    nvidia("nvidia");

    private static final Logger log;
    private final String id;

    private HardwareDecode(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public String toString() {
        return this.id;
    }

    public static HardwareDecode lookup(String id) {
        return Arrays.stream(HardwareDecode.values()).filter(it -> it.id.equals(id)).findFirst().orElse(null);
    }

    public String getId() {
        return this.id;
    }

    static {
        log = LoggerFactory.getLogger(HardwareDecode.class);
    }
}

