/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.TableField;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

@Pro
final class Prior<T>
extends AbstractField<T>
implements QOM.Prior<T> {
    final Field<T> field;

    Prior(Field<T> field2) {
        super(Names.N_PRIOR, Tools.allNotNull(Tools.dataType(field2), field2));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case INFORMIX: {
                ctx.quote(false, c -> {
                    Field<Object> field2;
                    Object c2 = ctx.visit(Keywords.K_PRIOR).sql(' ');
                    Field<T> patt3536$temp = this.field;
                    if (patt3536$temp instanceof TableField) {
                        TableField f = (TableField)patt3536$temp;
                        field2 = DSL.field(DSL.name(f.getTable().getName(), this.field.getName()));
                    } else {
                        field2 = this.field;
                    }
                    c2.visit(field2);
                });
                break;
            }
            default: {
                ctx.visit(Keywords.K_PRIOR).sql(' ').visit(this.field);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("prior(");
        ctx.visit(this.field);
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.Prior<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Prior<T>> $constructor() {
        return a1 -> new Prior(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Prior) {
            QOM.Prior o = (QOM.Prior)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

