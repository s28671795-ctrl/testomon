/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.AlterSequenceFinalStep;
import org.jooq.AlterSequenceFlagsStep;
import org.jooq.AlterSequenceStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function14;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Sequence;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class AlterSequenceImpl<T extends Number>
extends AbstractDDLQuery
implements QOM.AlterSequence<T>,
AlterSequenceStep<T>,
AlterSequenceFlagsStep<T>,
AlterSequenceFinalStep {
    final Sequence<T> sequence;
    final boolean ifExists;
    Sequence<?> renameTo;
    boolean restart;
    Field<T> restartWith;
    Field<T> startWith;
    Field<T> incrementBy;
    Field<T> minvalue;
    boolean noMinvalue;
    Field<T> maxvalue;
    boolean noMaxvalue;
    QOM.CycleOption cycle;
    Field<T> cache;
    boolean noCache;
    private static final Clause[] CLAUSES = new Clause[]{Clause.ALTER_SEQUENCE};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SEPARATOR = SQLDialect.supportedBy(SQLDialect.CUBRID, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE);
    private static final Set<SQLDialect> NO_SUPPORT_CACHE = SQLDialect.supportedBy(SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB);
    private static final Set<SQLDialect> EMULATE_NO_CACHE = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_RESTART = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.VERTICA);

    AlterSequenceImpl(Configuration configuration, Sequence<T> sequence2, boolean ifExists) {
        this(configuration, sequence2, ifExists, null, false, null, null, null, null, false, null, false, null, null, false);
    }

    AlterSequenceImpl(Configuration configuration, Sequence<T> sequence2, boolean ifExists, Sequence<?> renameTo, boolean restart, Field<T> restartWith, Field<T> startWith, Field<T> incrementBy, Field<T> minvalue, boolean noMinvalue, Field<T> maxvalue, boolean noMaxvalue, QOM.CycleOption cycle, Field<T> cache, boolean noCache) {
        super(configuration);
        this.sequence = sequence2;
        this.ifExists = ifExists;
        this.renameTo = renameTo;
        this.restart = restart;
        this.restartWith = restartWith;
        this.startWith = startWith;
        this.incrementBy = incrementBy;
        this.minvalue = minvalue;
        this.noMinvalue = noMinvalue;
        this.maxvalue = maxvalue;
        this.noMaxvalue = noMaxvalue;
        this.cycle = cycle;
        this.cache = cache;
        this.noCache = noCache;
    }

    @Override
    public final AlterSequenceImpl<T> renameTo(String renameTo) {
        return this.renameTo((Sequence)DSL.sequence(DSL.name(renameTo)));
    }

    @Override
    public final AlterSequenceImpl<T> renameTo(Name renameTo) {
        return this.renameTo((Sequence)DSL.sequence(renameTo));
    }

    @Override
    public final AlterSequenceImpl<T> renameTo(Sequence<?> renameTo) {
        this.renameTo = renameTo;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> restart() {
        this.restart = true;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> restartWith(T restartWith) {
        return this.restartWith((Field)Tools.field(restartWith));
    }

    @Override
    public final AlterSequenceImpl<T> restartWith(Field<T> restartWith) {
        this.restartWith = restartWith;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> startWith(T startWith) {
        return this.startWith((Field)Tools.field(startWith));
    }

    @Override
    public final AlterSequenceImpl<T> startWith(Field<T> startWith) {
        this.startWith = startWith;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> incrementBy(T incrementBy) {
        return this.incrementBy((Field)Tools.field(incrementBy));
    }

    @Override
    public final AlterSequenceImpl<T> incrementBy(Field<T> incrementBy) {
        this.incrementBy = incrementBy;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> minvalue(T minvalue) {
        return this.minvalue((Field)Tools.field(minvalue));
    }

    @Override
    public final AlterSequenceImpl<T> minvalue(Field<T> minvalue) {
        this.minvalue = minvalue;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> noMinvalue() {
        this.noMinvalue = true;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> maxvalue(T maxvalue) {
        return this.maxvalue((Field)Tools.field(maxvalue));
    }

    @Override
    public final AlterSequenceImpl<T> maxvalue(Field<T> maxvalue) {
        this.maxvalue = maxvalue;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> noMaxvalue() {
        this.noMaxvalue = true;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> cycle() {
        this.cycle = QOM.CycleOption.CYCLE;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> noCycle() {
        this.cycle = QOM.CycleOption.NO_CYCLE;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> cache(T cache) {
        return this.cache((Field)Tools.field(cache));
    }

    @Override
    public final AlterSequenceImpl<T> cache(Field<T> cache) {
        this.cache = cache;
        return this;
    }

    @Override
    public final AlterSequenceImpl<T> noCache() {
        this.noCache = true;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.ALTER_SEQUENCE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        switch (ctx.family()) {
            case INFORMIX: 
            case ORACLE: {
                if (this.renameTo != null) {
                    this.acceptRenameSequenceOracle(ctx);
                    break;
                }
                this.accept1(ctx);
                break;
            }
            case MARIADB: {
                if (this.renameTo != null) {
                    this.acceptRenameTable(ctx);
                    break;
                }
                this.accept1(ctx);
                break;
            }
            default: {
                this.accept1(ctx);
            }
        }
    }

    private final void acceptRenameTable(Context<?> ctx) {
        boolean qualify = ctx.qualify();
        ctx.start(Clause.ALTER_SEQUENCE_SEQUENCE).start(Clause.ALTER_SEQUENCE_RENAME).visit(Keywords.K_ALTER_TABLE).sql(' ').visit(this.sequence).sql(' ').visit(Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_SEQUENCE_RENAME).end(Clause.ALTER_SEQUENCE_SEQUENCE);
    }

    @Pro
    private final void acceptRenameSequenceOracle(Context<?> ctx) {
        ctx.start(Clause.ALTER_SEQUENCE_SEQUENCE).start(Clause.ALTER_SEQUENCE_RENAME).visit(ctx.family() == SQLDialect.INFORMIX ? Keywords.K_RENAME_SEQUENCE : Keywords.K_RENAME).sql(' ').visit(this.sequence).sql(' ').visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_SEQUENCE_RENAME).end(Clause.ALTER_SEQUENCE_SEQUENCE);
    }

    private final void accept1(Context<?> ctx) {
        ctx.start(Clause.ALTER_SEQUENCE_SEQUENCE).visit(Keywords.K_ALTER).sql(' ').visit(ctx.family() == SQLDialect.CUBRID ? Keywords.K_SERIAL : Keywords.K_SEQUENCE);
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        switch (ctx.family()) {
            case SQLSERVER: {
                ctx.sql(' ').qualifyCatalog(false, c -> c.visit(this.sequence));
                break;
            }
            default: {
                ctx.sql(' ').visit(this.sequence);
            }
        }
        ctx.end(Clause.ALTER_SEQUENCE_SEQUENCE);
        if (this.renameTo != null) {
            ctx.start(Clause.ALTER_SEQUENCE_RENAME).sql(' ').visit(Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_SEQUENCE_RENAME);
        } else {
            String noSeparator;
            ctx.start(Clause.ALTER_SEQUENCE_RESTART);
            String string = noSeparator = NO_SEPARATOR.contains((Object)ctx.dialect()) ? "" : " ";
            if (this.incrementBy != null) {
                ctx.sql(' ').visit(Keywords.K_INCREMENT_BY).sql(' ').visit(this.incrementBy);
            }
            if (this.minvalue != null) {
                ctx.sql(' ').visit(Keywords.K_MINVALUE).sql(' ').visit(this.minvalue);
            } else if (this.noMinvalue) {
                ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_MINVALUE);
            }
            if (this.maxvalue != null) {
                ctx.sql(' ').visit(Keywords.K_MAXVALUE).sql(' ').visit(this.maxvalue);
            } else if (this.noMaxvalue) {
                ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_MAXVALUE);
            }
            if (this.startWith != null) {
                ctx.sql(' ').visit(Keywords.K_START_WITH).sql(' ').visit(this.startWith);
            }
            if (this.restart) {
                if (NO_SUPPORT_RESTART.contains((Object)ctx.dialect())) {
                    ctx.sql(' ').visit(Keywords.K_RESTART_WITH);
                    if (this.sequence.getStartWith() != null) {
                        ctx.visit(this.sequence.getStartWith());
                    } else {
                        ctx.sql(" 1");
                    }
                } else {
                    ctx.sql(' ').visit(Keywords.K_RESTART);
                }
            } else if (this.restartWith != null) {
                if (ctx.family() == SQLDialect.CUBRID) {
                    ctx.sql(' ').visit(Keywords.K_START_WITH).sql(' ').visit(this.restartWith);
                } else {
                    ctx.sql(' ').visit(Keywords.K_RESTART_WITH).sql(' ').visit(this.restartWith);
                }
            }
            if (!NO_SUPPORT_CACHE.contains((Object)ctx.dialect())) {
                if (this.cache != null) {
                    ctx.sql(' ').visit(Keywords.K_CACHE).sql(' ').visit(this.cache);
                } else if (this.noCache) {
                    if (EMULATE_NO_CACHE.contains((Object)ctx.dialect())) {
                        ctx.sql(' ').visit(Keywords.K_CACHE).sql(' ').sql(1);
                    } else {
                        ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_CACHE);
                    }
                }
            }
            if (this.cycle == QOM.CycleOption.CYCLE) {
                ctx.sql(' ').visit(Keywords.K_CYCLE);
            } else if (this.cycle == QOM.CycleOption.NO_CYCLE) {
                ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_CYCLE);
            }
            ctx.end(Clause.ALTER_SEQUENCE_RESTART);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Sequence<T> $sequence() {
        return this.sequence;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final Sequence<?> $renameTo() {
        return this.renameTo;
    }

    @Override
    public final boolean $restart() {
        return this.restart;
    }

    @Override
    public final Field<T> $restartWith() {
        return this.restartWith;
    }

    @Override
    public final Field<T> $startWith() {
        return this.startWith;
    }

    @Override
    public final Field<T> $incrementBy() {
        return this.incrementBy;
    }

    @Override
    public final Field<T> $minvalue() {
        return this.minvalue;
    }

    @Override
    public final boolean $noMinvalue() {
        return this.noMinvalue;
    }

    @Override
    public final Field<T> $maxvalue() {
        return this.maxvalue;
    }

    @Override
    public final boolean $noMaxvalue() {
        return this.noMaxvalue;
    }

    @Override
    public final QOM.CycleOption $cycle() {
        return this.cycle;
    }

    @Override
    public final Field<T> $cache() {
        return this.cache;
    }

    @Override
    public final boolean $noCache() {
        return this.noCache;
    }

    @Override
    public final QOM.AlterSequence<T> $sequence(Sequence<T> newValue) {
        return this.$constructor().apply(newValue, this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), newValue, this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $renameTo(Sequence<?> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), newValue, this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $restart(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), newValue, this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $restartWith(Field<T> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), newValue, this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $startWith(Field<T> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), newValue, this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $incrementBy(Field<T> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), newValue, this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $minvalue(Field<T> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), newValue, this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $noMinvalue(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), newValue, this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $maxvalue(Field<T> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), newValue, this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $noMaxvalue(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), newValue, this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $cycle(QOM.CycleOption newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), newValue, this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $cache(Field<T> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), newValue, this.$noCache());
    }

    @Override
    public final QOM.AlterSequence<T> $noCache(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), newValue);
    }

    public final Function14<? super Sequence<T>, ? super Boolean, ? super Sequence<?>, ? super Boolean, ? super Field<T>, ? super Field<T>, ? super Field<T>, ? super Field<T>, ? super Boolean, ? super Field<T>, ? super Boolean, ? super QOM.CycleOption, ? super Field<T>, ? super Boolean, ? extends QOM.AlterSequence<T>> $constructor() {
        return (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) -> new AlterSequenceImpl<T>(this.configuration(), a1, (boolean)a2, (Sequence<?>)a3, (boolean)a4, a5, a6, a7, a8, (boolean)a9, a10, (boolean)a11, (QOM.CycleOption)((Object)a12), a13, (boolean)a14);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$sequence(), this.$ifExists(), this.$renameTo(), this.$restart(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache(), (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) -> this.$constructor().apply((Sequence<T>)a1, (Boolean)a2, (Sequence<?>)a3, (Boolean)a4, (Field<T>)a5, (Field<T>)a6, (Field<T>)a7, (Field<T>)a8, (Boolean)a9, (Field<T>)a10, (Boolean)a11, (QOM.CycleOption)((Object)a12), (Field<T>)a13, (Boolean)a14), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$sequence(), this.$renameTo(), this.$restartWith(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$maxvalue(), this.$cache());
    }
}

