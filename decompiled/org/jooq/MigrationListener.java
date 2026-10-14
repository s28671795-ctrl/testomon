/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.EventListener;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.MigrationContext;

@ApiStatus.Experimental
public interface MigrationListener
extends EventListener {
    default public void migrationStart(MigrationContext ctx) {
    }

    default public void migrationEnd(MigrationContext ctx) {
    }

    default public void queriesStart(MigrationContext ctx) {
    }

    default public void queriesEnd(MigrationContext ctx) {
    }

    default public void queryStart(MigrationContext ctx) {
    }

    default public void queryEnd(MigrationContext ctx) {
    }
}

