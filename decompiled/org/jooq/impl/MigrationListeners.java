/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Configuration;
import org.jooq.MigrationContext;
import org.jooq.MigrationListener;
import org.jooq.conf.InvocationOrder;
import org.jooq.impl.Tools;

class MigrationListeners
implements MigrationListener {
    private final MigrationListener[] listeners;

    MigrationListeners(Configuration configuration) {
        this.listeners = Tools.map(configuration.migrationListenerProviders(), p -> p.provide(), MigrationListener[]::new);
    }

    @Override
    public final void migrationStart(MigrationContext ctx) {
        for (MigrationListener listener : ctx.settings().getMigrationListenerStartInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.migrationStart(ctx);
        }
    }

    @Override
    public final void migrationEnd(MigrationContext ctx) {
        for (MigrationListener listener : ctx.settings().getMigrationListenerEndInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.migrationEnd(ctx);
        }
    }

    @Override
    public final void queriesStart(MigrationContext ctx) {
        for (MigrationListener listener : ctx.settings().getMigrationListenerStartInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.queriesStart(ctx);
        }
    }

    @Override
    public final void queriesEnd(MigrationContext ctx) {
        for (MigrationListener listener : ctx.settings().getMigrationListenerEndInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.queriesEnd(ctx);
        }
    }

    @Override
    public final void queryStart(MigrationContext ctx) {
        for (MigrationListener listener : ctx.settings().getMigrationListenerStartInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.queryStart(ctx);
        }
    }

    @Override
    public final void queryEnd(MigrationContext ctx) {
        for (MigrationListener listener : ctx.settings().getMigrationListenerEndInvocationOrder() != InvocationOrder.REVERSE ? Arrays.asList(this.listeners) : Tools.reverseIterable(this.listeners)) {
            listener.queryEnd(ctx);
        }
    }
}

