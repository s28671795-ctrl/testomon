/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Pro;
import org.jooq.Record2;
import org.jooq.Select;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class SplitPart
extends AbstractField<String>
implements QOM.SplitPart {
    final Field<String> string;
    final Field<String> delimiter;
    final Field<? extends Number> n;

    SplitPart(Field<String> string, Field<String> delimiter, Field<? extends Number> n) {
        super(Names.N_SPLIT_PART, Tools.allNotNull(SQLDataType.VARCHAR, string, delimiter, n));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.delimiter = Tools.nullSafeNotNull(delimiter, SQLDataType.VARCHAR);
        this.n = Tools.nullSafeNotNull(n, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case BIGQUERY: {
                return false;
            }
            case SQLSERVER: {
                return false;
            }
            case DB2: 
            case ORACLE: {
                return false;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                return false;
            }
            case HSQLDB: {
                return false;
            }
            case TERADATA: {
                return false;
            }
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case BIGQUERY: {
                ctx.visit(Names.N_SPLIT).sql('(').visit(this.string).sql(", ").visit(this.delimiter).sql(")[").visit(Keywords.K_ORDINAL).sql('(').visit(this.n).sql(")]");
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.coalesce(DSL.field(DSL.select(DSL.field(Names.N_VALUE)).from("{0}({1}, {2}, 1)", Names.N_STRING_SPLIT, this.string, this.delimiter).where(DSL.field(Names.N_ORDINAL).eq(this.n))), DSL.inline("")));
                break;
            }
            case DB2: 
            case ORACLE: {
                Field<Integer> ld = DSL.length(this.delimiter);
                Field<Integer> ls = DSL.length(this.string);
                Field<Integer> n1 = DSL.nullif(DSL.function(Names.N_INSTR, SQLDataType.INTEGER, this.string, this.delimiter, DSL.one(), DSL.nullif(Internal.isub(this.n, DSL.one()), DSL.zero())), DSL.zero());
                Field<Integer> nx = DSL.decode(this.n, DSL.one(), DSL.one(), Internal.iadd(n1, ld));
                Field<Integer> n2 = DSL.nullif(DSL.function(Names.N_INSTR, SQLDataType.INTEGER, this.string, this.delimiter, DSL.one(), this.n), DSL.zero());
                ctx.visit(DSL.coalesce(DSL.substring(this.string, DSL.nullif(nx, Internal.iadd(ls, DSL.one())), DSL.coalesce(Internal.isub(n2, nx), Internal.isub(Internal.isub(ls, n1), Internal.isub(ld, DSL.one())))), new Field[]{DSL.inline("")}));
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.substring(DSL.substringIndex(this.string, this.delimiter, this.n), DSL.case_(this.n).when((Field<? extends Number>)DSL.one(), DSL.one()).else_(DSL.length(DSL.substringIndex(this.string, this.delimiter, this.n.minus(DSL.one()))).plus(DSL.length(this.delimiter)).plus(DSL.one()))));
                break;
            }
            case HSQLDB: {
                Field<String> rS = DSL.field(DSL.name("s"), String.class);
                Field<Integer> rN = DSL.field(DSL.name("n"), Integer.TYPE);
                Field<String> rD = DSL.field(DSL.name("d"), String.class);
                Field<Integer> rPos = DSL.position(rS, rD);
                Field<Integer> rLen = DSL.length(rD);
                Field<Integer> rPosN = DSL.nullif(rPos, DSL.zero());
                Field<String> rStr = DSL.substring(rS, rPosN.plus(rLen));
                Field<String> rRes = DSL.coalesce(DSL.substring(rS, DSL.one(), rPosN.minus(DSL.one())), rS);
                CommonTableExpression<Record2<String, String>> s1 = DSL.name("s1").fields("s", "d").as(DSL.select(this.string, this.delimiter));
                CommonTableExpression s2 = DSL.name("s2").fields("s", "d", "x", "n").as(DSL.select(rStr, rD, rRes, DSL.one()).from((TableLike<?>)s1).unionAll((Select)DSL.select(rStr, rD, rRes, rN.plus(DSL.one())).from(DSL.name("s2")).where(rS.isNotNull())));
                Tools.visitSubquery(ctx, DSL.withRecursive(s1, s2).select(DSL.coalesce(DSL.max(DSL.field(DSL.name("x"))), DSL.inline(""))).from((TableLike<?>)s2).where(s2.field("n").eq(this.n)), 1);
                break;
            }
            case TERADATA: {
                ctx.visit(DSL.function(Names.N_STRTOK, this.getDataType(), this.string, this.delimiter, this.n));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_SPLIT_PART, this.getDataType(), this.string, this.delimiter, this.n));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("splitPart(");
        String s2 = "";
        ctx.sql(s2).visit(this.string);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.delimiter));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.n));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<String> $arg2() {
        return this.delimiter;
    }

    @Override
    public final Field<? extends Number> $arg3() {
        return this.n;
    }

    @Override
    public final QOM.SplitPart $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2(), (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.SplitPart $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue, (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.SplitPart $arg3(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)this.$arg2(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<String>, ? super Field<? extends Number>, ? extends QOM.SplitPart> $constructor() {
        return (a1, a2, a3) -> new SplitPart((Field<String>)a1, (Field<String>)a2, (Field<? extends Number>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.SplitPart) {
            QOM.SplitPart o = (QOM.SplitPart)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$delimiter(), o.$delimiter()) && StringUtils.equals(this.$n(), o.$n());
        }
        return super.equals(that);
    }
}

