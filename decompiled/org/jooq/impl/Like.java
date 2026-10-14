/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.LikeEscapeStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Concat;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Like
extends AbstractCondition
implements QOM.Like,
LikeEscapeStep {
    final Field<?> value;
    final Field<String> pattern;
    Character escape;
    private static final Set<SQLDialect> REQUIRES_CAST_ON_LIKE = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.INFORMIX, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_ILIKE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);

    Like(Field<?> value, Field<String> pattern) {
        this(value, pattern, null);
    }

    Like(Field<?> value, Field<String> pattern, Character escape) {
        this.value = Tools.nullableIf(false, Tools.nullSafe(value, pattern.getDataType()));
        this.pattern = Tools.nullableIf(false, Tools.nullSafe(pattern, value.getDataType()));
    }

    @Override
    public final Like escape(char escape) {
        this.escape = Character.valueOf(escape);
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        Like.accept0(ctx, this.value, Comparator.LIKE, this.pattern, this.escape);
    }

    static final boolean castRhs(Context<?> ctx, Field<?> arg2) {
        boolean castRhs = false;
        if (ctx.family() == SQLDialect.DB2 && arg2 instanceof Concat) {
            castRhs = true;
        }
        return castRhs;
    }

    static final ParamType forcedParamType(Context<?> ctx, Character escape) {
        ParamType forcedParamType = ctx.paramType();
        if (ctx.family() == SQLDialect.REDSHIFT && escape != null) {
            forcedParamType = ParamType.INLINED;
        }
        return forcedParamType;
    }

    static final void accept0(Context<?> ctx, Field<?> arg1, Comparator op, Field<?> arg2, Character escape) {
        switch (op) {
            case LIKE: 
            case SIMILAR_TO: 
            case NOT_LIKE: 
            case NOT_SIMILAR_TO: {
                if (arg1.getType() != String.class && REQUIRES_CAST_ON_LIKE.contains((Object)ctx.dialect())) {
                    arg1 = Tools.castIfNeeded(arg1, String.class);
                }
                if (arg2.getType() == String.class || !REQUIRES_CAST_ON_LIKE.contains((Object)ctx.dialect())) break;
                arg2 = Tools.castIfNeeded(arg2, String.class);
                break;
            }
            case LIKE_IGNORE_CASE: 
            case NOT_LIKE_IGNORE_CASE: {
                if (arg1.getType() != String.class) {
                    arg1 = Tools.castIfNeeded(arg1, String.class);
                }
                if (arg2.getType() == String.class) break;
                arg2 = Tools.castIfNeeded(arg2, String.class);
            }
        }
        switch (op) {
            case LIKE_IGNORE_CASE: 
            case NOT_LIKE_IGNORE_CASE: {
                if (!NO_SUPPORT_ILIKE.contains((Object)ctx.dialect())) break;
                arg1 = DSL.lower(arg1);
                arg2 = DSL.lower(arg2);
                op = op == Comparator.LIKE_IGNORE_CASE ? Comparator.LIKE : Comparator.NOT_LIKE;
            }
        }
        boolean castRhs = Like.castRhs(ctx, arg2);
        ctx.visit(arg1).sql(' ').visit(op.toKeyword()).sql(' ');
        if (castRhs) {
            ctx.visit(Keywords.K_CAST).sql('(');
        }
        ctx.visit(arg2, Like.forcedParamType(ctx, escape));
        if (castRhs) {
            ctx.sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_VARCHAR).sql("(4000))");
        }
        if (escape != null) {
            ctx.sql(' ').visit(Keywords.K_ESCAPE).sql(' ').visit(DSL.inline(escape));
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.value).sql(".like(").visit(Val.getJavaValue(this.pattern)).sql(')');
    }

    @Override
    public final Field<?> $arg1() {
        return this.value;
    }

    @Override
    public final Field<String> $arg2() {
        return this.pattern;
    }

    @Override
    public final Character $arg3() {
        return this.escape;
    }

    @Override
    public final QOM.Like $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue, (Field<String>)this.$arg2(), (Character)((Character)this.$arg3()));
    }

    @Override
    public final QOM.Like $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), newValue, (Character)((Character)this.$arg3()));
    }

    @Override
    public final QOM.Like $arg3(Character newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), (Field<String>)this.$arg2(), (Character)((Character)newValue));
    }

    @Override
    public final Function3<? super Field<?>, ? super Field<String>, ? super Character, ? extends QOM.Like> $constructor() {
        return (a1, a2, a3) -> new Like((Field<?>)a1, (Field<String>)a2, (Character)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Like) {
            QOM.Like o = (QOM.Like)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$pattern(), o.$pattern()) && StringUtils.equals(this.$escape(), o.$escape());
        }
        return super.equals(that);
    }
}

