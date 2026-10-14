/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function4;
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

final class Overlay
extends AbstractField<String>
implements QOM.Overlay {
    final Field<String> in;
    final Field<String> placing;
    final Field<? extends Number> startIndex;
    final Field<? extends Number> length;
    private static final Set<SQLDialect> NO_SUPPORT = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.DERBY, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private static final Set<SQLDialect> SUPPORT_INSERT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE);

    Overlay(Field<String> in, Field<String> placing, Field<? extends Number> startIndex) {
        super(Names.N_OVERLAY, Tools.allNotNull(SQLDataType.VARCHAR, in, placing, startIndex));
        this.in = Tools.nullSafeNotNull(in, SQLDataType.VARCHAR);
        this.placing = Tools.nullSafeNotNull(placing, SQLDataType.VARCHAR);
        this.startIndex = Tools.nullSafeNotNull(startIndex, SQLDataType.INTEGER);
        this.length = null;
    }

    Overlay(Field<String> in, Field<String> placing, Field<? extends Number> startIndex, Field<? extends Number> length) {
        super(Names.N_OVERLAY, Tools.allNotNull(SQLDataType.VARCHAR, in, placing, startIndex, length));
        this.in = Tools.nullSafeNotNull(in, SQLDataType.VARCHAR);
        this.placing = Tools.nullSafeNotNull(placing, SQLDataType.VARCHAR);
        this.startIndex = Tools.nullSafeNotNull(startIndex, SQLDataType.INTEGER);
        this.length = Tools.nullSafeNotNull(length, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        Field<? extends Number> l = this.length;
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if (ctx.family() == SQLDialect.DB2 && l == null) {
            l = DSL.length(this.placing);
        }
        if (l != null) {
            if (SUPPORT_INSERT.contains((Object)ctx.dialect())) {
                ctx.visit(DSL.function(Names.N_INSERT, this.getDataType(), this.in, this.startIndex, l, this.placing));
            } else if (NO_SUPPORT.contains((Object)ctx.dialect())) {
                ctx.visit(DSL.substring(this.in, DSL.inline(1), Internal.isub(this.startIndex, DSL.inline(1))).concat(this.placing).concat(DSL.substring(this.in, Internal.iadd(this.startIndex, l))));
            } else {
                ctx.visit(Names.N_OVERLAY).sql('(').visit(this.in).sql(' ').visit(Keywords.K_PLACING).sql(' ').visit(this.placing).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.startIndex).sql(' ').visit(Keywords.K_FOR).sql(' ').visit(l).sql(')');
            }
        } else if (SUPPORT_INSERT.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.function(Names.N_INSERT, this.getDataType(), this.in, this.startIndex, DSL.length(this.placing), this.placing));
        } else if (NO_SUPPORT.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.substring(this.in, DSL.inline(1), Internal.isub(this.startIndex, DSL.inline(1))).concat(this.placing).concat(DSL.substring(this.in, Internal.iadd(this.startIndex, DSL.length(this.placing)))));
        } else {
            ctx.visit(Names.N_OVERLAY).sql('(').visit(this.in).sql(' ').visit(Keywords.K_PLACING).sql(' ').visit(this.placing).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.startIndex).sql(')');
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("overlay(");
        String s2 = "";
        ctx.sql(s2).visit(this.in);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.placing));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.startIndex));
        s2 = ", ";
        if (this.length != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.length));
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
        return this.placing;
    }

    @Override
    public final Field<? extends Number> $arg3() {
        return this.startIndex;
    }

    @Override
    public final Field<? extends Number> $arg4() {
        return this.length;
    }

    @Override
    public final QOM.Overlay $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2(), (Field<? extends Number>)this.$arg3(), (Field<? extends Number>)this.$arg4());
    }

    @Override
    public final QOM.Overlay $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue, (Field<? extends Number>)this.$arg3(), (Field<? extends Number>)this.$arg4());
    }

    @Override
    public final QOM.Overlay $arg3(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)this.$arg2(), (Field<? extends Number>)newValue, (Field<? extends Number>)this.$arg4());
    }

    @Override
    public final QOM.Overlay $arg4(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)this.$arg2(), (Field<? extends Number>)this.$arg3(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function4<? super Field<String>, ? super Field<String>, ? super Field<? extends Number>, ? super Field<? extends Number>, ? extends QOM.Overlay> $constructor() {
        return (a1, a2, a3, a4) -> new Overlay((Field<String>)a1, (Field<String>)a2, (Field<? extends Number>)a3, (Field<? extends Number>)a4);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Overlay) {
            QOM.Overlay o = (QOM.Overlay)that;
            return StringUtils.equals(this.$in(), o.$in()) && StringUtils.equals(this.$placing(), o.$placing()) && StringUtils.equals(this.$startIndex(), o.$startIndex()) && StringUtils.equals(this.$length(), o.$length());
        }
        return super.equals(that);
    }
}

