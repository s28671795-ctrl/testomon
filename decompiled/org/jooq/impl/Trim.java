/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Trim
extends AbstractField<String>
implements QOM.Trim {
    final Field<String> string;
    final Field<String> characters;
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_TRIM_WITHOUT_CHARACTERS = SQLDialect.supportedUntil(SQLDialect.ASE, SQLDialect.INGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016);

    Trim(Field<String> string) {
        super(Names.N_TRIM, Tools.allNotNull(SQLDataType.VARCHAR, string));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.characters = null;
    }

    Trim(Field<String> string, Field<String> characters) {
        super(Names.N_TRIM, Tools.allNotNull(SQLDataType.VARCHAR, string, characters));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.characters = Tools.nullSafeNotNull(characters, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if (this.characters == null) {
            if (NO_SUPPORT_TRIM_WITHOUT_CHARACTERS.contains((Object)ctx.family())) {
                ctx.visit(DSL.ltrim(DSL.rtrim(this.string)));
            } else {
                ctx.visit(Names.N_TRIM).sql('(').visit(this.string).sql(')');
            }
        } else {
            switch (ctx.family()) {
                case SNOWFLAKE: 
                case SQLITE: {
                    ctx.visit(Names.N_TRIM).sql('(').visit(this.string).sql(", ").visit(this.characters).sql(')');
                    break;
                }
                case SQLSERVER: {
                    ctx.visit(Names.N_TRIM).sql('(').visit(this.characters).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.string).sql(')');
                    break;
                }
                default: {
                    ctx.visit(Names.N_TRIM).sql('(').visit(Keywords.K_BOTH).sql(' ').visit(this.characters).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.string).sql(')');
                }
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("trim(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.string));
        s2 = ", ";
        if (this.characters != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.characters));
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
        return this.characters;
    }

    @Override
    public final QOM.Trim $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.Trim $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue);
    }

    @Override
    public final Function2<? super Field<String>, ? super Field<String>, ? extends QOM.Trim> $constructor() {
        return (a1, a2) -> new Trim((Field<String>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Trim) {
            QOM.Trim o = (QOM.Trim)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$characters(), o.$characters());
        }
        return super.equals(that);
    }
}

