/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

public enum PluginState {
    CREATED("CREATED"),
    DISABLED("DISABLED"),
    RESOLVED("RESOLVED"),
    STARTED("STARTED"),
    STOPPED("STOPPED"),
    FAILED("FAILED");

    private String status;

    private PluginState(String status) {
        this.status = status;
    }

    public boolean equals(String status) {
        return status == null ? false : this.status.equalsIgnoreCase(status);
    }

    public String toString() {
        return this.status;
    }

    public static PluginState parse(String string) {
        for (PluginState status : PluginState.values()) {
            if (!status.equals(string)) continue;
            return status;
        }
        return null;
    }
}

