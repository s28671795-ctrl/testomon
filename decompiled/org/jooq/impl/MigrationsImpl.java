/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collections;
import org.jooq.Commit;
import org.jooq.Commits;
import org.jooq.DSLContext;
import org.jooq.Migration;
import org.jooq.Migrations;
import org.jooq.Version;
import org.jooq.Versions;
import org.jooq.impl.CommitImpl;
import org.jooq.impl.CommitsImpl;
import org.jooq.impl.MigrationImpl;
import org.jooq.impl.VersionImpl;
import org.jooq.impl.VersionsImpl;

final class MigrationsImpl
implements Migrations {
    final DSLContext ctx;

    MigrationsImpl(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public final Version version(String id) {
        return new VersionImpl(this.ctx, id, null, new Version[0]);
    }

    @Override
    public final Versions versions() {
        return new VersionsImpl(this.version("init"));
    }

    @Override
    public final Commit commit(String id) {
        return new CommitImpl(this.ctx.configuration(), id, null, Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public final Commits commits() {
        return new CommitsImpl(this.ctx.configuration(), this.commit("init"));
    }

    @Override
    public final Migration migrateTo(Commit to) {
        return new MigrationImpl(this.ctx.configuration(), to);
    }
}

