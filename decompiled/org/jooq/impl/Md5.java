/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;
import org.jooq.util.sqlserver.SQLServerDataType;

final class Md5
extends AbstractField<String>
implements QOM.Md5 {
    final Field<String> string;
    @Pro
    private static final Name ORACLE_DBMS_CRYPTO_HASH = DSL.name("SYS", "DBMS_CRYPTO", "HASH");
    @Pro
    private static final Name ORACLE_UTL_RAW_CAST_TO_RAW = DSL.name("SYS", "UTL_RAW", "CAST_TO_RAW");

    Md5(Field<String> string) {
        super(Names.N_MD5, Tools.allNotNull(SQLDataType.VARCHAR, string));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        block0 : switch (ctx.family()) {
            case EXASOL: {
                ctx.visit(Names.N_HASH_MD5).sql('(').visit(this.string).sql(')');
                break;
            }
            case ORACLE: {
                switch (ctx.dialect()) {
                    case ORACLE10G: 
                    case ORACLE11G: {
                        ctx.visit(Names.N_LOWER).sql('(').visit(Names.N_RAWTOHEX).sql('(').visit(ORACLE_DBMS_CRYPTO_HASH).sql('(').visit(ORACLE_UTL_RAW_CAST_TO_RAW).sql('(').visit(this.string).sql("), 2)))");
                        break block0;
                    }
                }
                ctx.visit(Names.N_LOWER).sql('(').visit(Names.N_STANDARD_HASH).sql('(').visit(this.string).sql(", 'MD5'))");
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_LOWER).sql('(').visit(Names.N_CONVERT).sql('(').visit(Keywords.K_VARCHAR).sql("(32), ").visit(Names.N_HASHBYTES).sql("('MD5', ").visit(DSL.cast(this.string, SQLServerDataType.VARCHAR)).sql("), 2))");
                break;
            }
            default: {
                ctx.visit(Names.N_MD5).sql('(').visit(this.string).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("md5(");
        ctx.visit(Val.getJavaValue(this.string));
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final QOM.Md5 $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue);
    }

    @Override
    public final Function1<? super Field<String>, ? extends QOM.Md5> $constructor() {
        return a1 -> new Md5((Field<String>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Md5) {
            QOM.Md5 o = (QOM.Md5)that;
            return StringUtils.equals(this.$string(), o.$string());
        }
        return super.equals(that);
    }
}

