/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class Nvl2<T>
extends AbstractField<T>
implements QOM.Nvl2<T> {
    private final Field<?> arg1;
    private final Field<T> arg2;
    private final Field<T> arg3;

    Nvl2(Field<?> arg1, Field<T> arg2, Field<T> arg3) {
        super(Names.N_NVL2, !arg1.getDataType().nullable() ? arg2.getDataType() : Tools.allNotNull(arg2.getDataType(), arg2, arg3));
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case SQLSERVER: {
                ctx.visit(DSL.iif(this.arg1.isNotNull(), this.arg2, this.arg3));
                break;
            }
            case MARIADB: {
                if (ctx.dialect().precedes(SQLDialect.MARIADB_10_2)) {
                    this.acceptCase(ctx);
                    break;
                }
                this.acceptDefault(ctx);
                break;
            }
            case ASE: 
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case HANA: 
            case MEMSQL: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case CUBRID: 
            case DERBY: 
            case FIREBIRD: 
            case MYSQL: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                this.acceptCase(ctx);
                break;
            }
            default: {
                this.acceptDefault(ctx);
            }
        }
    }

    private void acceptCase(Context<?> ctx) {
        ctx.visit(DSL.when(this.arg1.isNotNull(), this.arg2).otherwise(this.arg3));
    }

    private final void acceptDefault(Context<?> ctx) {
        ctx.visit(DSL.function(Names.N_NVL2, this.getDataType(), this.arg1, this.arg2, this.arg3));
    }

    @Override
    public final Field<?> $arg1() {
        return this.arg1;
    }

    @Override
    public final Field<T> $arg2() {
        return this.arg2;
    }

    @Override
    public final Field<T> $arg3() {
        return this.arg3;
    }

    @Override
    public final Function3<? super Field<?>, ? super Field<T>, ? super Field<T>, ? extends QOM.Nvl2<T>> $constructor() {
        return (a1, a2, a3) -> new Nvl2((Field<?>)a1, a2, a3);
    }
}

