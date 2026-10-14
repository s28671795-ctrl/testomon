/*
 * Decompiled with CFR 0.152.
 */
package com.license4j;

import java.util.Arrays;

public enum ActivationStatus {
    ACTIVATION_SERVER_CONNECTION_ERROR(-1),
    LICENSE_NOT_FOUND_ON_ACTIVATION_SERVER(0),
    ALREADY_ACTIVATED_ON_ANOTHER_COMPUTER(1),
    MULTIPLE_ACTIVATION_LIMIT_REACHED(2),
    ACTIVATION_HARDWAREID_ERROR(3),
    ACTIVATION_REJECTED_IP_BLOCK_RESTRICTION(4),
    ACTIVATION_REJECTED_FEATURE_DISABLED(5),
    ACTIVATION_NOT_FOUND_ON_SERVER(6),
    DEACTIVATION_COMPLETED(7),
    MULTIPLE_DEACTIVATION_LIMIT_REACHED(8),
    DEACTIVATION_REJECTED_FEATURE_DISABLED(9),
    ACTIVATION_COMPLETED(10),
    ACTIVATION_REQUIRED(11),
    ACTIVATION_NOT_REQUIRED(12);

    private final int value;

    private ActivationStatus(int value) {
        this.value = value;
    }

    public String getStringValue() {
        return String.valueOf(this.value);
    }

    public static ActivationStatus getStatus(String value) {
        return Arrays.stream(ActivationStatus.values()).filter(status -> status.getValue() == Integer.parseInt(value)).findFirst().orElse(ACTIVATION_SERVER_CONNECTION_ERROR);
    }

    public int getValue() {
        return this.value;
    }
}

