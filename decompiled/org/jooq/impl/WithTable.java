/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Context;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQL;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDelegatingTable;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.ForLock;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.ScopeMarker;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.TableList;
import org.jooq.impl.Tools;

@Pro
final class WithTable<R extends Record>
extends AbstractDelegatingTable<R>
implements QOM.WithTable<R> {
    private final SQL hint;

    WithTable(AbstractTable<R> delegate, SQL hint) {
        super(delegate);
        this.hint = hint;
    }

    @Override
    final <O extends Record> WithTable<O> construct(AbstractTable<O> newDelegate) {
        return new WithTable<O>(newDelegate, this.hint);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (SelectQueryImpl.NO_SUPPORT_WITH_TABLE.contains((Object)ctx.dialect())) {
            ScopeWithTableForUpdate s2 = new ScopeWithTableForUpdate();
            if (s2.active) {
                ctx.visit(this.delegate);
                ctx.data().compute(Tools.ExtendedDataKey.DATA_SCOPE_WITH_TABLE_HINTS, (k, v) -> s2.merge((ScopeWithTableForUpdate)v));
                return;
            }
        }
        ctx.visit(this.delegate).sql(' ').visit(Keywords.K_WITH).sql(" (").visit(this.hint).sql(')');
    }

    @Override
    public final Table<R> $table() {
        return this.delegate;
    }

    @Override
    public final <O extends Record> WithTable<O> $table(Table<O> newTable) {
        return this.construct((AbstractTable)newTable);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.delegate);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.delegate, abstractTable -> this.construct((AbstractTable)abstractTable), replacer);
    }

    @Pro
    final class ScopeWithTableForUpdate
    extends AbstractQueryPart
    implements ScopeMarker.ScopeContent,
    QOM.UTransient {
        final List<Table<?>> tables;
        boolean active;
        boolean skipLocked;
        boolean nowait;

        ScopeWithTableForUpdate() {
            String lc = WithTable.this.hint.toString().toLowerCase();
            this.tables = new ArrayList<Table>(Arrays.asList(WithTable.this.$table()));
            this.active = lc.contains("rowlock") && lc.contains("updlock");
            this.skipLocked = lc.contains("readpast");
            this.nowait = lc.contains("nowait");
        }

        @Override
        public final void accept(Context<?> ctx) {
            ForLock l = new ForLock();
            l.forLockMode = ForLock.ForLockMode.UPDATE;
            if (this.nowait) {
                l.forLockWaitMode = ForLock.ForLockWaitMode.NOWAIT;
            } else if (this.skipLocked) {
                l.forLockWaitMode = ForLock.ForLockWaitMode.SKIP_LOCKED;
            }
            if (!ForLock.EMULATE_FOR_UPDATE_OF_TABLE.contains((Object)ctx.dialect())) {
                l.forLockOfTables = new TableList((List<? extends Table<?>>)this.tables);
            }
            ctx.visit(l);
        }

        ScopeWithTableForUpdate merge(ScopeWithTableForUpdate v) {
            if (v != null && v.active) {
                if (this.active) {
                    this.tables.addAll(0, v.tables);
                    this.skipLocked |= v.skipLocked;
                    this.nowait |= v.nowait;
                } else {
                    return v;
                }
            }
            return this;
        }

        @Override
        public boolean isEmpty() {
            return !this.active;
        }
    }
}

