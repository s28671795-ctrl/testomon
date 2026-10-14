/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.MigrationListener;
import org.jooq.MigrationListenerProvider;
import org.jooq.impl.Tools;

public class DefaultMigrationListenerProvider
implements MigrationListenerProvider,
Serializable {
    private final MigrationListener listener;

    public static MigrationListenerProvider[] providers(MigrationListener ... listeners) {
        return Tools.map(listeners, DefaultMigrationListenerProvider::new, MigrationListenerProvider[]::new);
    }

    public DefaultMigrationListenerProvider(MigrationListener listener) {
        this.listener = listener;
    }

    @Override
    public final MigrationListener provide() {
        return this.listener;
    }

    public String toString() {
        return this.listener.toString();
    }
}

