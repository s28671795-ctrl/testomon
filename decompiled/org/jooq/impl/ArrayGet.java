/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class ArrayGet<T>
extends AbstractField<T>
implements QOM.ArrayGet<T> {
    final Field<T[]> array;
    final Field<Integer> index;

    ArrayGet(Field<T[]> array, Field<Integer> index) {
        super(Names.N_ARRAY_GET, Tools.allNotNull(StringUtils.defaultIfNull(array.getDataType().getArrayComponentDataType(), SQLDataType.OTHER), array, index));
        this.array = Tools.nullSafeNotNull(array, SQLDataType.OTHER.array());
        this.index = Tools.nullSafeNotNull(index, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case H2: {
                if (ctx.dialect().precedes(SQLDialect.H2_1_4_200)) {
                    ctx.visit(Names.N_ARRAY_GET).sql('(').visit(this.array).sql(", ").visit(this.index).sql(')');
                    break;
                }
                ctx.visit(new Standard());
                break;
            }
            case HSQLDB: {
                if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_STORE_ASSIGNMENT))) {
                    ctx.visit(new Standard());
                    break;
                }
                ctx.visit(DSL.when(DSL.cardinality(this.array).ge(this.index), new Standard()));
                break;
            }
            default: {
                ctx.visit(new Standard());
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("arrayGet(");
        String s2 = "";
        ctx.sql(s2).visit(this.array);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.index));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T[]> $arg1() {
        return this.array;
    }

    @Override
    public final Field<Integer> $arg2() {
        return this.index;
    }

    @Override
    public final QOM.ArrayGet<T> $arg1(Field<T[]> newValue) {
        return this.$constructor().apply(newValue, (Field<Integer>)this.$arg2());
    }

    @Override
    public final QOM.ArrayGet<T> $arg2(Field<Integer> newValue) {
        return this.$constructor().apply((Field<T[]>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T[]>, ? super Field<Integer>, ? extends QOM.ArrayGet<T>> $constructor() {
        return (a1, a2) -> new ArrayGet((Field<T[]>)a1, (Field<Integer>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ArrayGet) {
            QOM.ArrayGet o = (QOM.ArrayGet)that;
            return StringUtils.equals(this.$array(), o.$array()) && StringUtils.equals(this.$index(), o.$index());
        }
        return super.equals(that);
    }

    private class Standard
    extends AbstractField<T>
    implements QOM.UTransient {
        Standard() {
            super(ArrayGet.this.getQualifiedName(), ArrayGet.this.getDataType());
        }

        @Override
        public void accept(Context<?> ctx) {
            if (ArrayGet.this.array instanceof TableField || Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_STORE_ASSIGNMENT))) {
                ctx.visit(ArrayGet.this.array).sql('[').visit(ArrayGet.this.index).sql(']');
            } else {
                ctx.sql('(').visit(ArrayGet.this.array).sql(')').sql('[').visit(ArrayGet.this.index).sql(']');
            }
        }
    }
}

