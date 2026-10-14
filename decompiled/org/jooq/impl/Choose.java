/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class Choose<T>
extends AbstractField<T>
implements QOM.Choose<T> {
    private Field<Integer> index;
    private Field<T>[] values;

    Choose(Field<Integer> index, Field<T>[] values2) {
        this(index, values2, Tools.nullSafeDataType(values2));
    }

    Choose(Field<Integer> index, Field<T>[] values2, DataType<T> type) {
        super(Names.N_CHOOSE, type);
        this.index = index;
        this.values = values2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.values.length == 0) {
            ctx.visit(DSL.inline(null, this.getDataType()));
            return;
        }
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case FIREBIRD: 
            case H2: 
            case HSQLDB: 
            case IGNITE: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                CaseValueStep<Integer> s2 = DSL.choose(this.index);
                CaseWhenStep<Integer, T> when = null;
                for (int i = 0; i < this.values.length; ++i) {
                    when = when == null ? s2.when((Field<Integer>)DSL.inline(i + 1), this.values[i]) : when.when((Field<Integer>)DSL.inline(i + 1), this.values[i]);
                }
                ctx.visit(when);
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_ELT, this.getDataType(), Tools.combine(this.index, this.values)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_CHOOSE, this.getDataType(), Tools.combine(this.index, this.values)));
            }
        }
    }

    @Override
    public final Field<Integer> $arg1() {
        return this.index;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<T>> $arg2() {
        return QOM.unmodifiable((QueryPart[])this.values);
    }

    @Override
    public final Function2<? super Field<Integer>, ? super QOM.UnmodifiableList<? extends Field<T>>, ? extends QOM.Choose<T>> $constructor() {
        return (i, v) -> v.isEmpty() ? new Choose((Field<Integer>)i, Tools.EMPTY_FIELD, (DataType<?>)this.getDataType()) : new Choose((Field<Integer>)i, v.toArray(Tools.EMPTY_FIELD));
    }
}

