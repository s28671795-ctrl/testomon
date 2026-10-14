/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

public enum QualityOfProtection {
    AUTH("auth"),
    AUTH_INT("auth-int"),
    AUTH_CONF("auth-conf");

    private final String qop;

    private QualityOfProtection(String s2) {
        this.qop = s2;
    }

    public String string() {
        return this.qop;
    }

    public static QualityOfProtection fromString(String s2) {
        for (QualityOfProtection p : QualityOfProtection.values()) {
            if (!p.string().equalsIgnoreCase(s2)) continue;
            return p;
        }
        return null;
    }
}

