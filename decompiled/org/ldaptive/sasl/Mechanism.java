/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

public enum Mechanism {
    EXTERNAL("EXTERNAL"),
    DIGEST_MD5("DIGEST-MD5"),
    CRAM_MD5("CRAM-MD5"),
    GSSAPI("GSSAPI"),
    SCRAM_SHA_1("SCRAM-SHA-1", "SHA-1", "HmacSHA1"),
    SCRAM_SHA_256("SCRAM-SHA-256", "SHA-256", "HmacSHA256"),
    SCRAM_SHA_512("SCRAM-SHA-512", "SHA-512", "HmacSHA512");

    private final String mechanismName;
    private final String[] properties;

    private Mechanism(String mechanism) {
        this(mechanism, null);
    }

    private Mechanism(String mechanism, String ... props) {
        this.mechanismName = mechanism;
        this.properties = props;
    }

    public String mechanism() {
        return this.mechanismName;
    }

    public String[] properties() {
        return this.properties;
    }
}

