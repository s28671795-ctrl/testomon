/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Pro;
import org.jooq.SQLDialect;
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

final class Position
extends AbstractField<Integer>
implements QOM.Position {
    final Field<String> in;
    final Field<String> search;
    final Field<? extends Number> startIndex;

    Position(Field<String> in, Field<String> search) {
        super(Names.N_POSITION, Tools.allNotNull(SQLDataType.INTEGER, in, search));
        this.in = Tools.nullSafeNotNull(in, SQLDataType.VARCHAR);
        this.search = Tools.nullSafeNotNull(search, SQLDataType.VARCHAR);
        this.startIndex = null;
    }

    Position(Field<String> in, Field<String> search, Field<? extends Number> startIndex) {
        super(Names.N_POSITION, Tools.allNotNull(SQLDataType.INTEGER, in, search, startIndex));
        this.in = Tools.nullSafeNotNull(in, SQLDataType.VARCHAR);
        this.search = Tools.nullSafeNotNull(search, SQLDataType.VARCHAR);
        this.startIndex = Tools.nullSafeNotNull(startIndex, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if (this.startIndex != null) {
            switch (ctx.family()) {
                case DB2: 
                case DERBY: 
                case H2: {
                    ctx.visit(Names.N_LOCATE).sql('(').visit(this.search).sql(", ").visit(this.in).sql(", ").visit(this.startIndex).sql(')');
                    break;
                }
                case HANA: 
                case INGRES: 
                case SYBASE: {
                    ctx.visit(Names.N_LOCATE).sql('(').visit(this.in).sql(", ").visit(this.search).sql(", ").visit(this.startIndex).sql(')');
                    break;
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    ctx.visit(Names.N_CHARINDEX).sql('(').visit(this.search).sql(", ").visit(this.in).sql(", ").visit(this.startIndex).sql(')');
                    break;
                }
                case BIGQUERY: 
                case ORACLE: 
                case TERADATA: 
                case VERTICA: {
                    ctx.visit(Names.N_INSTR).sql('(').visit(this.in).sql(", ").visit(this.search).sql(", ").visit(this.startIndex).sql(')');
                    break;
                }
                default: {
                    ctx.visit(DSL.case_(DSL.position(DSL.substring(this.in, this.startIndex), this.search)).when((Field<Integer>)DSL.inline(0), DSL.inline(0)).else_(Internal.iadd(DSL.position(DSL.substring(this.in, this.startIndex), this.search), Internal.isub(this.startIndex, DSL.one()))));
                    break;
                }
            }
        } else {
            switch (ctx.family()) {
                case DB2: 
                case DERBY: {
                    ctx.visit(Names.N_LOCATE).sql('(').visit(this.search).sql(", ").visit(this.in).sql(')');
                    break;
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case ASE: {
                    ctx.visit(Names.N_CHARINDEX).sql('(').visit(this.search).sql(", ").visit(this.in).sql(')');
                    break;
                }
                case HANA: 
                case INGRES: 
                case SYBASE: {
                    ctx.visit(Names.N_LOCATE).sql('(').visit(this.in).sql(", ").visit(this.search).sql(')');
                    break;
                }
                case BIGQUERY: 
                case ORACLE: 
                case ACCESS: 
                case SQLITE: {
                    ctx.visit(Names.N_INSTR).sql('(').visit(this.in).sql(", ").visit(this.search).sql(')');
                    break;
                }
                default: {
                    ctx.visit(Names.N_POSITION).sql('(').visit(this.search).sql(' ').visit(Keywords.K_IN).sql(' ').visit(this.in).sql(')');
                }
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("position(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.in));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.search));
        s2 = ", ";
        if (this.startIndex != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.startIndex));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.in;
    }

    @Override
    public final Field<String> $arg2() {
        return this.search;
    }

    @Override
    public final Field<? extends Number> $arg3() {
        return this.startIndex;
    }

    @Override
    public final QOM.Position $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2(), (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.Position $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue, (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.Position $arg3(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)this.$arg2(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<String>, ? super Field<? extends Number>, ? extends QOM.Position> $constructor() {
        return (a1, a2, a3) -> new Position((Field<String>)a1, (Field<String>)a2, (Field<? extends Number>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Position) {
            QOM.Position o = (QOM.Position)that;
            return StringUtils.equals(this.$in(), o.$in()) && StringUtils.equals(this.$search(), o.$search()) && StringUtils.equals(this.$startIndex(), o.$startIndex());
        }
        return super.equals(that);
    }
}

