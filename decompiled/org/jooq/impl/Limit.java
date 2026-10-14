/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lazy;
import org.jooq.impl.NoField;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;

final class Limit
extends AbstractQueryPart
implements QOM.UTransient {
    private static final Lazy<Param<Integer>> ZERO = Lazy.of(() -> DSL.zero());
    private static final Lazy<Param<Integer>> ONE = Lazy.of(() -> DSL.one());
    private static final Lazy<Param<Integer>> MAX = Lazy.of(() -> DSL.inline(Integer.MAX_VALUE));
    Field<? extends Number> limit;
    private Field<? extends Number> limitOrMax = MAX.get();
    Field<? extends Number> offset;
    private Field<? extends Number> offsetOrZero = ZERO.get();
    private Field<? extends Number> offsetPlusOne = ONE.get();
    boolean withTies;
    boolean percent;

    Limit() {
    }

    @Override
    public final void accept(Context<?> ctx) {
        ParamType paramType = ctx.paramType();
        RenderContext.CastMode castMode = ctx.castMode();
        switch (ctx.family()) {
            case VERTICA: {
                if (ctx.subquery()) {
                    ctx.paramType(ParamType.INLINED);
                }
                this.acceptDefault(ctx, castMode);
                if (!ctx.subquery()) break;
                ctx.paramType(paramType);
                break;
            }
            case SQLSERVER: {
                if (ctx.dialect().precedes(SQLDialect.SQLSERVER2008) || Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_PREFER_TOP_OVER_FETCH))) {
                    this.top(ctx, paramType);
                    break;
                }
                this.acceptStandard(ctx, castMode);
                break;
            }
            case CUBRID: {
                ctx.castMode(RenderContext.CastMode.NEVER).formatSeparator().visit(Keywords.K_LIMIT).sql(' ').visit(this.offsetOrZero).sql(", ").visit(this.limitOrMax).castMode(castMode);
                break;
            }
            case FIREBIRD: {
                if (ctx.dialect().precedes(SQLDialect.FIREBIRD_2_5)) {
                    ctx.castMode(RenderContext.CastMode.NEVER).formatSeparator().visit(Keywords.K_ROWS).sql(' ').visit(Internal.iadd(this.getLowerRownum(), (Field)ONE.get())).sql(' ').visit(Keywords.K_TO).sql(' ').visit(this.getUpperRownum()).castMode(castMode);
                    break;
                }
                this.acceptStandard(ctx, castMode);
                break;
            }
            case H2: {
                if (ctx.dialect().precedesStrictly(SQLDialect.H2_2_1_214) && !this.withTies() && !this.percent()) {
                    this.acceptDefault(ctx, castMode);
                    break;
                }
                this.acceptStandard(ctx, castMode);
                break;
            }
            case POSTGRES: {
                if (ctx.dialect().precedes(SQLDialect.POSTGRES_12)) {
                    this.acceptDefault(ctx, castMode);
                    break;
                }
                this.acceptStandard(ctx, castMode);
                break;
            }
            case ORACLE: 
            case DERBY: {
                this.acceptStandard(ctx, castMode);
                break;
            }
            case INGRES: {
                ctx.paramType(ParamType.INLINED).formatSeparator().visit(Keywords.K_OFFSET).sql(' ').visit(this.offsetOrZero).sql(' ').visit(Keywords.K_FETCH_FIRST).sql(' ').visit(this.limitOrMax).sql(' ').visit(Keywords.K_ROWS_ONLY).paramType(paramType);
                break;
            }
            case INFORMIX: {
                ctx.paramType(ParamType.INLINED);
                if (!this.offsetZero()) {
                    ctx.visit(Keywords.K_SKIP).sql(' ').visit(this.offsetOrZero).sql(' ');
                }
                ctx.visit(Keywords.K_FIRST).sql(' ').visit(this.limitOrMax).paramType(paramType);
                break;
            }
            case SYBASE: {
                ctx.paramType(ParamType.INLINED).visit(Keywords.K_TOP).sql(' ').visit(this.limitOrMax);
                if (!this.offsetZero()) {
                    ctx.sql(' ').visit(Keywords.K_START_AT).sql(' ').visit(this.offsetPlusOne);
                }
                ctx.paramType(paramType);
                break;
            }
            case DB2: {
                if (ctx.dialect().precedes(SQLDialect.DB2_10)) {
                    ctx.paramType(ParamType.INLINED, c -> this.acceptStandard((Context<?>)c, castMode));
                    break;
                }
                this.acceptStandard(ctx, castMode);
                break;
            }
            case ACCESS: 
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case TERADATA: {
                this.top(ctx, paramType);
                break;
            }
            case MARIADB: {
                if (ctx.dialect().precedes(SQLDialect.MARIADB_10_5)) {
                    this.acceptDefaultLimitMandatory(ctx, castMode);
                    break;
                }
                this.acceptStandard(ctx, castMode);
                break;
            }
            case AURORA_MYSQL: 
            case HANA: 
            case MEMSQL: 
            case SNOWFLAKE: 
            case MYSQL: 
            case SQLITE: {
                this.acceptDefaultLimitMandatory(ctx, castMode);
                break;
            }
            case BIGQUERY: 
            case EXASOL: {
                ctx.paramType(ParamType.INLINED, c -> this.acceptDefaultLimitMandatory((Context<?>)c, castMode));
                break;
            }
            case REDSHIFT: {
                ctx.paramType(ParamType.INLINED, c -> this.acceptDefault((Context<?>)c, castMode));
                break;
            }
            default: {
                this.acceptDefault(ctx, castMode);
            }
        }
    }

    private final void acceptStandard(Context<?> ctx, RenderContext.CastMode castMode) {
        ctx.castMode(RenderContext.CastMode.NEVER);
        if (ctx.family() == SQLDialect.SQLSERVER || !this.offsetZero()) {
            ctx.formatSeparator().visit(Keywords.K_OFFSET).sql(' ').visit(this.offsetOrZero).sql(' ').visit(Keywords.K_ROWS);
        }
        if (!this.limitZero()) {
            ctx.formatSeparator().visit(Keywords.K_FETCH_NEXT).sql(' ').visit(this.limit);
            if (this.percent) {
                ctx.sql(' ').visit(Keywords.K_PERCENT);
            }
            ctx.sql(' ').visit(this.withTies ? Keywords.K_ROWS_WITH_TIES : Keywords.K_ROWS_ONLY);
        }
        ctx.castMode(castMode);
    }

    private final void acceptDefault(Context<?> ctx, RenderContext.CastMode castMode) {
        ctx.castMode(RenderContext.CastMode.NEVER);
        if (!this.limitZero()) {
            ctx.formatSeparator().visit(Keywords.K_LIMIT).sql(' ').visit(this.limit);
        }
        if (!this.offsetZero()) {
            ctx.formatSeparator().visit(Keywords.K_OFFSET).sql(' ').visit(this.offsetOrZero);
        }
        ctx.castMode(castMode);
    }

    private void acceptDefaultLimitMandatory(Context<?> ctx, RenderContext.CastMode castMode) {
        ctx.castMode(RenderContext.CastMode.NEVER).formatSeparator().visit(Keywords.K_LIMIT).sql(' ').visit(this.limitOrMax);
        if (!this.offsetZero()) {
            ctx.formatSeparator().visit(Keywords.K_OFFSET).sql(' ').visit(this.offsetOrZero);
        }
        ctx.castMode(castMode);
    }

    @Pro
    private final void top(Context<?> ctx, ParamType paramType) {
        ctx.data(Tools.BooleanDataKey.DATA_PREFER_TOP_OVER_FETCH, null, c -> {
            c.paramType(ParamType.INLINED).visit(Keywords.K_TOP).sql(' ').visit(this.limit).paramType(paramType);
            if (this.percent) {
                c.sql(' ').visit(Keywords.K_PERCENT);
            }
            if (this.withTies) {
                c.sql(' ').visit(Keywords.K_WITH_TIES);
            }
        });
    }

    final boolean limitZero() {
        return this.limit == null;
    }

    final boolean limitOne() {
        return !this.limitZero() && !this.withTies() && !this.percent() && Long.valueOf(1L).equals(this.getLimit());
    }

    final boolean offsetZero() {
        return this.offset == null;
    }

    final Field<?> getLowerRownum() {
        return this.offsetOrZero;
    }

    final Field<?> getUpperRownum() {
        return Internal.iadd(this.offsetOrZero, this.limitOrMax);
    }

    final boolean isApplicable() {
        return this.offset != null || this.limit != null;
    }

    final boolean isExpression() {
        return this.isApplicable() && (this.limit != null && !(this.limit instanceof Param) || this.offset != null && !(this.offset instanceof Param));
    }

    final boolean isSubquery() {
        return this.isApplicable() && (Tools.isScalarSubquery(this.limit) || Tools.isScalarSubquery(this.offset));
    }

    final void setOffset(Number offset) {
        this.offset = DSL.val((Object)offset.longValue(), SQLDataType.BIGINT);
        this.offsetOrZero = this.offset;
        this.offsetPlusOne = DSL.val((Object)(offset.longValue() + 1L), SQLDataType.BIGINT);
    }

    final void setOffset(Field<? extends Number> offset) {
        if (offset instanceof NoField) {
            return;
        }
        this.offset = offset;
        this.offsetOrZero = offset == null ? (Field)ZERO.get() : offset;
    }

    final void setLimit(Number l) {
        this.limit = DSL.val((Object)l.longValue(), SQLDataType.BIGINT);
        this.limitOrMax = this.limit;
    }

    final void setLimit(Field<? extends Number> l) {
        if (l instanceof NoField) {
            return;
        }
        this.limit = l;
        this.limitOrMax = l == null ? (Field)MAX.get() : l;
    }

    final Long getLimit() {
        Field<? extends Number> l;
        Field<? extends Number> field2 = l = this.limit != null ? this.limit : this.limitOrMax;
        if (l instanceof Param) {
            Param p = (Param)l;
            return Convert.convert(p.getValue(), Long.TYPE);
        }
        return Convert.convert((Object)MAX.get().getValue(), Long.TYPE);
    }

    final void setPercent(boolean percent) {
        this.percent = percent;
    }

    final boolean percent() {
        return this.percent;
    }

    final void setWithTies(boolean withTies) {
        this.withTies = withTies;
    }

    final boolean withTies() {
        return this.withTies;
    }

    final Limit from(Limit other) {
        if (other.limit != null) {
            if (this.limit == null) {
                this.setLimit(other.limit);
            } else {
                this.setLimit(((Val)other.limit).copy(Math.min(this.getLimit(), other.getLimit())));
            }
        }
        if (other.offset != null) {
            this.setOffset(other.offset);
        }
        this.setPercent(other.percent);
        this.setWithTies(other.withTies);
        return this;
    }

    final void clear() {
        this.offset = null;
        this.limit = null;
        this.withTies = false;
        this.percent = false;
    }
}

