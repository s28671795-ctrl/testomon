/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.hardware;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum HardwareAcceleration {
    none(""),
    openvino("openvino"),
    trt("trt"),
    cudnn("cudnn"),
    cudnn_half("cudnn-half");

    private static final Logger log;
    private final String id;

    private HardwareAcceleration(String id) {
        this.id = Objects.requireNonNull(id);
    }

    @JsonValue
    public String toString() {
        return this.id;
    }

    public static HardwareAcceleration lookup(String id) {
        return Arrays.stream(HardwareAcceleration.values()).filter(it -> it.id.equals(id)).findFirst().orElse(null);
    }

    public String getId() {
        return this.id;
    }

    static {
        log = LoggerFactory.getLogger(HardwareAcceleration.class);
    }
}

