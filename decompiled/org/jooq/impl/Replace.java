/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Replace
extends AbstractField<String>
implements QOM.Replace {
    final Field<String> string;
    final Field<String> search;
    final Field<String> replace;

    Replace(Field<String> string, Field<String> search) {
        super(Names.N_REPLACE, Tools.allNotNull(SQLDataType.VARCHAR, string, search));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.search = Tools.nullSafeNotNull(search, SQLDataType.VARCHAR);
        this.replace = null;
    }

    Replace(Field<String> string, Field<String> search, Field<String> replace) {
        super(Names.N_REPLACE, Tools.allNotNull(SQLDataType.VARCHAR, string, search, replace));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.search = Tools.nullSafeNotNull(search, SQLDataType.VARCHAR);
        this.replace = Tools.nullSafeNotNull(replace, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ASE: {
                if (this.replace == null) {
                    ctx.visit(DSL.function(Names.N_STR_REPLACE, SQLDataType.VARCHAR, this.string, this.search, DSL.inline((String)null)));
                } else {
                    ctx.visit(DSL.function(Names.N_STR_REPLACE, SQLDataType.VARCHAR, this.string, this.search, this.replace));
                }
                return;
            }
            case TERADATA: {
                if (this.replace == null) {
                    ctx.visit(DSL.function(Names.N_OREPLACE, SQLDataType.VARCHAR, this.string, this.search, DSL.inline("")));
                } else {
                    ctx.visit(DSL.function(Names.N_OREPLACE, SQLDataType.VARCHAR, this.string, this.search, this.replace));
                }
                return;
            }
            case ACCESS: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case HANA: 
            case INGRES: 
            case MEMSQL: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: 
            case FIREBIRD: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                if (this.replace == null) {
                    ctx.visit(DSL.function(Names.N_REPLACE, SQLDataType.VARCHAR, this.string, this.search, DSL.inline("")));
                } else {
                    ctx.visit(DSL.function(Names.N_REPLACE, SQLDataType.VARCHAR, this.string, this.search, this.replace));
                }
                return;
            }
        }
        if (this.replace == null) {
            ctx.visit(DSL.function(Names.N_REPLACE, SQLDataType.VARCHAR, this.string, this.search));
        } else {
            ctx.visit(DSL.function(Names.N_REPLACE, SQLDataType.VARCHAR, this.string, this.search, this.replace));
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("replace(");
        String s2 = "";
        ctx.sql(s2).visit(this.string);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.search));
        s2 = ", ";
        if (this.replace != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.replace));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<String> $arg2() {
        return this.search;
    }

    @Override
    public final Field<String> $arg3() {
        return this.replace;
    }

    @Override
    public final QOM.Replace $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2(), (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Replace $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue, (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Replace $arg3(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)this.$arg2(), (Field<String>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<String>, ? super Field<String>, ? extends QOM.Replace> $constructor() {
        return (a1, a2, a3) -> new Replace((Field<String>)a1, (Field<String>)a2, (Field<String>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Replace) {
            QOM.Replace o = (QOM.Replace)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$search(), o.$search()) && StringUtils.equals(this.$replace(), o.$replace());
        }
        return super.equals(that);
    }
}

