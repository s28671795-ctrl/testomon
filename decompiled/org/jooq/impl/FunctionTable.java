/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.TableAlias;

final class FunctionTable<R extends Record>
extends AbstractTable<R>
implements QOM.UNotYetImplemented {
    private final Field<?> function;

    FunctionTable(Field<?> function) {
        super(TableOptions.function(), Names.N_FUNCTION);
        this.function = function;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    final FieldsImpl<R> fields0() {
        return new FieldsImpl(new SelectField[0]);
    }

    @Override
    public final Table<R> as(Name as) {
        return new TableAlias<R>(new FunctionTable<R>(this.function), as);
    }

    @Override
    public final Table<R> as(Name as, Name ... fieldAliases) {
        return new TableAlias<R>(new FunctionTable<R>(this.function), as, fieldAliases);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case HSQLDB: {
                ctx.visit(Keywords.K_TABLE).sql('(').visit(this.function).sql(')');
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(this.function);
                break;
            }
            default: {
                throw new SQLDialectNotSupportedException("FUNCTION TABLE is not supported for " + String.valueOf((Object)ctx.dialect()));
            }
        }
    }
}

