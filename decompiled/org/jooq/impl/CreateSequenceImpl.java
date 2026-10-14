/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.CreateSequenceFlagsStep;
import org.jooq.Field;
import org.jooq.Function11;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Sequence;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class CreateSequenceImpl
extends AbstractDDLQuery
implements QOM.CreateSequence,
CreateSequenceFlagsStep,
CreateSequenceFinalStep {
    final Sequence<?> sequence;
    final boolean ifNotExists;
    Field<? extends Number> startWith;
    Field<? extends Number> incrementBy;
    Field<? extends Number> minvalue;
    boolean noMinvalue;
    Field<? extends Number> maxvalue;
    boolean noMaxvalue;
    QOM.CycleOption cycle;
    Field<? extends Number> cache;
    boolean noCache;
    private static final Clause[] CLAUSES = new Clause[]{Clause.CREATE_SEQUENCE};
    private static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> REQUIRES_START_WITH = SQLDialect.supportedBy(SQLDialect.DERBY, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_CACHE = SQLDialect.supportedBy(SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB);
    private static final Set<SQLDialect> NO_SEPARATOR = SQLDialect.supportedBy(SQLDialect.CUBRID, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE);
    private static final Set<SQLDialect> OMIT_NO_CACHE = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> OMIT_NO_CYCLE = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.SNOWFLAKE);
    private static final Set<SQLDialect> OMIT_NO_MINVALUE = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.SNOWFLAKE);
    private static final Set<SQLDialect> OMIT_NO_MAXVALUE = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.SNOWFLAKE);

    CreateSequenceImpl(Configuration configuration, Sequence<?> sequence2, boolean ifNotExists) {
        this(configuration, sequence2, ifNotExists, null, null, null, false, null, false, null, null, false);
    }

    CreateSequenceImpl(Configuration configuration, Sequence<?> sequence2, boolean ifNotExists, Field<? extends Number> startWith, Field<? extends Number> incrementBy, Field<? extends Number> minvalue, boolean noMinvalue, Field<? extends Number> maxvalue, boolean noMaxvalue, QOM.CycleOption cycle, Field<? extends Number> cache, boolean noCache) {
        super(configuration);
        this.sequence = sequence2;
        this.ifNotExists = ifNotExists;
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
    public final CreateSequenceImpl startWith(Number startWith) {
        return this.startWith(Tools.field((Object)startWith, this.sequence.getDataType()));
    }

    @Override
    public final CreateSequenceImpl startWith(Field<? extends Number> startWith) {
        this.startWith = startWith;
        return this;
    }

    @Override
    public final CreateSequenceImpl incrementBy(Number incrementBy) {
        return this.incrementBy(Tools.field((Object)incrementBy, this.sequence.getDataType()));
    }

    @Override
    public final CreateSequenceImpl incrementBy(Field<? extends Number> incrementBy) {
        this.incrementBy = incrementBy;
        return this;
    }

    @Override
    public final CreateSequenceImpl minvalue(Number minvalue) {
        return this.minvalue(Tools.field((Object)minvalue, this.sequence.getDataType()));
    }

    @Override
    public final CreateSequenceImpl minvalue(Field<? extends Number> minvalue) {
        this.minvalue = minvalue;
        return this;
    }

    @Override
    public final CreateSequenceImpl noMinvalue() {
        this.noMinvalue = true;
        return this;
    }

    @Override
    public final CreateSequenceImpl maxvalue(Number maxvalue) {
        return this.maxvalue(Tools.field((Object)maxvalue, this.sequence.getDataType()));
    }

    @Override
    public final CreateSequenceImpl maxvalue(Field<? extends Number> maxvalue) {
        this.maxvalue = maxvalue;
        return this;
    }

    @Override
    public final CreateSequenceImpl noMaxvalue() {
        this.noMaxvalue = true;
        return this;
    }

    @Override
    public final CreateSequenceImpl cycle() {
        this.cycle = QOM.CycleOption.CYCLE;
        return this;
    }

    @Override
    public final CreateSequenceImpl noCycle() {
        this.cycle = QOM.CycleOption.NO_CYCLE;
        return this;
    }

    @Override
    public final CreateSequenceImpl cache(Number cache) {
        return this.cache(Tools.field((Object)cache, this.sequence.getDataType()));
    }

    @Override
    public final CreateSequenceImpl cache(Field<? extends Number> cache) {
        this.cache = cache;
        return this;
    }

    @Override
    public final CreateSequenceImpl noCache() {
        this.noCache = true;
        return this;
    }

    private final boolean supportsIfNotExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.CREATE_SEQUENCE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        String noSeparator;
        ctx.start(Clause.CREATE_SEQUENCE_SEQUENCE).visit(Keywords.K_CREATE).sql(' ').visit(ctx.family() == SQLDialect.CUBRID ? Keywords.K_SERIAL : Keywords.K_SEQUENCE).sql(' ');
        if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
            ctx.visit(Keywords.K_IF_NOT_EXISTS).sql(' ');
        }
        ctx.visit(this.sequence);
        String string = noSeparator = NO_SEPARATOR.contains((Object)ctx.dialect()) ? "" : " ";
        if (this.startWith == null && REQUIRES_START_WITH.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_START_WITH).sql(" 1");
        } else if (this.startWith != null) {
            ctx.sql(' ').visit(Keywords.K_START_WITH).sql(' ').visit(this.startWith);
        }
        if (this.incrementBy != null) {
            ctx.sql(' ').visit(Keywords.K_INCREMENT_BY).sql(' ').visit(this.incrementBy);
        }
        if (this.minvalue != null) {
            ctx.sql(' ').visit(Keywords.K_MINVALUE).sql(' ').visit(this.minvalue);
        } else if (this.noMinvalue && !OMIT_NO_MINVALUE.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_MINVALUE);
        }
        if (this.maxvalue != null) {
            ctx.sql(' ').visit(Keywords.K_MAXVALUE).sql(' ').visit(this.maxvalue);
        } else if (this.noMaxvalue && !OMIT_NO_MAXVALUE.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_MAXVALUE);
        }
        if (this.cycle == QOM.CycleOption.CYCLE) {
            ctx.sql(' ').visit(Keywords.K_CYCLE);
        } else if (this.cycle == QOM.CycleOption.NO_CYCLE && !OMIT_NO_CYCLE.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_CYCLE);
        }
        if (!NO_SUPPORT_CACHE.contains((Object)ctx.dialect())) {
            if (this.cache != null) {
                ctx.sql(' ').visit(Keywords.K_CACHE).sql(' ').visit(this.cache);
            } else if (this.noCache && !OMIT_NO_CACHE.contains((Object)ctx.dialect())) {
                ctx.sql(' ').visit(Keywords.K_NO).sql(noSeparator).visit(Keywords.K_CACHE);
            }
        }
        ctx.end(Clause.CREATE_SEQUENCE_SEQUENCE);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Sequence<?> $sequence() {
        return this.sequence;
    }

    @Override
    public final boolean $ifNotExists() {
        return this.ifNotExists;
    }

    @Override
    public final Field<? extends Number> $startWith() {
        return this.startWith;
    }

    @Override
    public final Field<? extends Number> $incrementBy() {
        return this.incrementBy;
    }

    @Override
    public final Field<? extends Number> $minvalue() {
        return this.minvalue;
    }

    @Override
    public final boolean $noMinvalue() {
        return this.noMinvalue;
    }

    @Override
    public final Field<? extends Number> $maxvalue() {
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
    public final Field<? extends Number> $cache() {
        return this.cache;
    }

    @Override
    public final boolean $noCache() {
        return this.noCache;
    }

    @Override
    public final QOM.CreateSequence $sequence(Sequence<?> newValue) {
        return this.$constructor().apply(newValue, this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $ifNotExists(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), newValue, this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $startWith(Field<? extends Number> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), newValue, this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $incrementBy(Field<? extends Number> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), newValue, this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $minvalue(Field<? extends Number> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), newValue, this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $noMinvalue(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), newValue, this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $maxvalue(Field<? extends Number> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), newValue, this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $noMaxvalue(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), newValue, this.$cycle(), this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $cycle(QOM.CycleOption newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), newValue, this.$cache(), this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $cache(Field<? extends Number> newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), newValue, this.$noCache());
    }

    @Override
    public final QOM.CreateSequence $noCache(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), newValue);
    }

    public final Function11<? super Sequence<?>, ? super Boolean, ? super Field<? extends Number>, ? super Field<? extends Number>, ? super Field<? extends Number>, ? super Boolean, ? super Field<? extends Number>, ? super Boolean, ? super QOM.CycleOption, ? super Field<? extends Number>, ? super Boolean, ? extends QOM.CreateSequence> $constructor() {
        return (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> new CreateSequenceImpl(this.configuration(), (Sequence<?>)a1, (boolean)a2, (Field<? extends Number>)a3, (Field<? extends Number>)a4, (Field<? extends Number>)a5, (boolean)a6, (Field<? extends Number>)a7, (boolean)a8, (QOM.CycleOption)((Object)a9), (Field<? extends Number>)a10, (boolean)a11);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$sequence(), this.$ifNotExists(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$noMinvalue(), this.$maxvalue(), this.$noMaxvalue(), this.$cycle(), this.$cache(), this.$noCache(), (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> this.$constructor().apply((Sequence<?>)a1, (Boolean)a2, (Field<Number>)a3, (Field<Number>)a4, (Field<Number>)a5, (Boolean)a6, (Field<Number>)a7, (Boolean)a8, (QOM.CycleOption)((Object)a9), (Field<Number>)a10, (Boolean)a11), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$sequence(), this.$startWith(), this.$incrementBy(), this.$minvalue(), this.$maxvalue(), this.$cache());
    }
}

