/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.marketplace.models;

public enum MarketplaceProductType {
    PORTAL("portal"),
    VEZHA_ANALYTICS_PLUGIN("vezha_analytics_plugin"),
    VEZHA_EVENT_MANAGER_PLUGIN("vezha_event_manager_plugin"),
    VEZHA_CORE("vezha_core"),
    VMS_PLUGIN("vms_plugin"),
    VMS_DRIVER("vms_driver"),
    VMS_CORE("vms_core"),
    RECORDING_SERVER("recording_server");

    private final String value;

    public String toString() {
        return this.name();
    }

    public String getValue() {
        return this.value;
    }

    private MarketplaceProductType(String value) {
        this.value = value;
    }
}

