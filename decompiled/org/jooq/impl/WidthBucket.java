/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function4;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class WidthBucket<T extends Number>
extends AbstractField<T>
implements QOM.WidthBucket<T> {
    final Field<T> field;
    final Field<T> low;
    final Field<T> high;
    final Field<Integer> buckets;

    WidthBucket(Field<T> field2, Field<T> low, Field<T> high, Field<Integer> buckets) {
        super(Names.N_WIDTH_BUCKET, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, field2, false), field2, low, high, buckets));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.INTEGER);
        this.low = Tools.nullSafeNotNull(low, SQLDataType.INTEGER);
        this.high = Tools.nullSafeNotNull(high, SQLDataType.INTEGER);
        this.buckets = Tools.nullSafeNotNull(buckets, SQLDataType.INTEGER);
    }

    @Override
    public void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case AURORA_MYSQL: 
            case BIGQUERY: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case FIREBIRD: 
            case H2: 
            case HSQLDB: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: 
            case SQLITE: {
                ctx.visit(DSL.when(this.field.lt(this.low), DSL.zero()).when(this.field.ge(this.high), Internal.iadd(this.buckets, DSL.one())).otherwise(Internal.iadd(DSL.floor(Internal.idiv(Internal.imul(Internal.isub(this.field, this.low), this.buckets), Internal.isub(this.high, this.low))), DSL.one())));
                break;
            }
            default: {
                ctx.visit(Names.N_WIDTH_BUCKET).sql('(').visit(this.field).sql(", ").visit(this.low).sql(", ").visit(this.high).sql(", ").visit(this.buckets).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("widthBucket(");
        String s2 = "";
        ctx.sql(s2).visit(this.field);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.low));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.high));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.buckets));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final Field<T> $arg2() {
        return this.low;
    }

    @Override
    public final Field<T> $arg3() {
        return this.high;
    }

    @Override
    public final Field<Integer> $arg4() {
        return this.buckets;
    }

    @Override
    public final QOM.WidthBucket<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2(), (Field<T>)this.$arg3(), (Field<Integer>)this.$arg4());
    }

    @Override
    public final QOM.WidthBucket<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue, (Field<T>)this.$arg3(), (Field<Integer>)this.$arg4());
    }

    @Override
    public final QOM.WidthBucket<T> $arg3(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<T>)this.$arg2(), newValue, (Field<Integer>)this.$arg4());
    }

    @Override
    public final QOM.WidthBucket<T> $arg4(Field<Integer> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<T>)this.$arg2(), (Field<T>)this.$arg3(), newValue);
    }

    @Override
    public final Function4<? super Field<T>, ? super Field<T>, ? super Field<T>, ? super Field<Integer>, ? extends QOM.WidthBucket<T>> $constructor() {
        return (a1, a2, a3, a4) -> new WidthBucket(a1, a2, a3, (Field<Integer>)a4);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.WidthBucket) {
            QOM.WidthBucket o = (QOM.WidthBucket)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$low(), o.$low()) && StringUtils.equals(this.$high(), o.$high()) && StringUtils.equals(this.$buckets(), o.$buckets());
        }
        return super.equals(that);
    }
}

