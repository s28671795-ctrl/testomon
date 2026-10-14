/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

public interface PropertySource<T> {
    public void initialize();

    public static enum PropertyDomain {
        LDAP("org.ldaptive."),
        AUTH("org.ldaptive.auth."),
        POOL("org.ldaptive.pool.");

        private final String domain;

        private PropertyDomain(String s2) {
            this.domain = s2;
        }

        public String value() {
            return this.domain;
        }
    }
}

