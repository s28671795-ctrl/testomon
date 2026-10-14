/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRetryMetadata;
import org.ldaptive.ConnectionStrategy;

public class LdapURLRetryMetadata
extends AbstractRetryMetadata {
    private final ConnectionStrategy connectionStrategy;

    public LdapURLRetryMetadata(ConnectionStrategy strategy) {
        this.connectionStrategy = strategy;
    }

    public ConnectionStrategy getConnectionStrategy() {
        return this.connectionStrategy;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "connectionStrategy=" + this.connectionStrategy;
    }
}

