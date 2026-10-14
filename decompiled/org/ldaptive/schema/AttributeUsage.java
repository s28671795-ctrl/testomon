/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

public enum AttributeUsage {
    USER_APPLICATIONS("userApplications", false),
    DIRECTORY_OPERATION("directoryOperation", true),
    DISTRIBUTED_OPERATION("distributedOperation", true),
    DSA_OPERATION("dSAOperation", true);

    private final String name;
    private final boolean operational;

    private AttributeUsage(String s2, boolean b) {
        this.name = s2;
        this.operational = b;
    }

    public String getName() {
        return this.name;
    }

    public boolean isOperational() {
        return this.operational;
    }

    public static AttributeUsage parse(String s2) {
        for (AttributeUsage usage : AttributeUsage.values()) {
            if (!usage.getName().equalsIgnoreCase(s2)) continue;
            return usage;
        }
        return null;
    }
}

