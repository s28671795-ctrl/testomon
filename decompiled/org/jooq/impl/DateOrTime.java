/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Keyword;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DateOrTime<T>
extends AbstractField<T>
implements QOM.UNotYetImplemented {
    private final Field<?> field;

    DateOrTime(Field<?> field2, DataType<T> dataType) {
        super(DSL.name(DateOrTime.name(dataType)), dataType);
        this.field = field2;
    }

    private static String name(DataType<?> dataType) {
        return dataType.isDate() ? "date" : (dataType.isTime() ? "time" : "timestamp");
    }

    private static Keyword keyword(DataType<?> dataType) {
        return dataType.isDate() ? Keywords.K_DATE : (dataType.isTime() ? Keywords.K_TIME : Keywords.K_TIMESTAMP);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MEMSQL: 
            case DERBY: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DateOrTime.keyword(this.getDataType())).sql('(').visit(this.field).sql(')');
                break;
            }
            case SQLITE: {
                if (this.getDataType().isDate()) {
                    ctx.visit(Keywords.K_DATE).sql('(').visit(this.field).sql(')');
                    break;
                }
                if (this.getDataType().isTime()) {
                    ctx.visit(Keywords.K_TIME).sql('(').visit(this.field).sql(')');
                    break;
                }
                ctx.visit(Names.N_STRFTIME).sql("('%Y-%m-%d %H:%M:%f', ").visit(this.field).sql(')');
                break;
            }
            default: {
                ctx.visit(Tools.castIfNeeded(this.field, this.getDataType()));
            }
        }
    }
}

