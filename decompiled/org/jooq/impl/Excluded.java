/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.InsertQueryImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Excluded<T>
extends AbstractField<T>
implements QOM.Excluded<T> {
    final Field<T> field;

    Excluded(Field<T> field2) {
        super(Names.N_EXCLUDED, Tools.allNotNull(Tools.dataType(field2), field2));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: {
                ctx.visit(Keywords.K_VALUES).sql('(').qualify(false, c -> c.visit(this.field)).sql(')');
                break;
            }
            case MYSQL: {
                if (!InsertQueryImpl.REQUIRE_NEW_MYSQL_EXCLUDED_EMULATION.contains((Object)ctx.dialect())) {
                    ctx.visit(Keywords.K_VALUES).sql('(').qualify(false, c -> c.visit(this.field)).sql(')');
                    break;
                }
                ctx.visit(DSL.name("t")).sql('.').qualify(false, c -> c.visit(this.field));
                break;
            }
            default: {
                ctx.visit(ctx.data(Tools.ExtendedDataKey.DATA_INSERT_ON_DUPLICATE_KEY_UPDATE) != null ? DSL.name("t") : Names.N_EXCLUDED).sql('.').qualify(false, c -> c.visit(this.field));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("excluded(");
        ctx.visit(this.field);
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.Excluded<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Excluded<T>> $constructor() {
        return a1 -> new Excluded(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Excluded) {
            QOM.Excluded o = (QOM.Excluded)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

