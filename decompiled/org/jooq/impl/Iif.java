/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class Iif<T>
extends AbstractField<T>
implements QOM.Iif<T> {
    private final Condition condition;
    private final Field<T> ifTrue;
    private final Field<T> ifFalse;

    Iif(Name name, Condition condition, Field<T> ifTrue, Field<T> ifFalse) {
        super(name, ifTrue.getDataType());
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Names.N_IF).sql('(').visit(this.condition).sql(", ").visit(this.ifTrue).sql(", ").visit(this.ifFalse).sql(')');
                break;
            }
            case ACCESS: 
            case SQLSERVER: {
                ctx.visit(Names.N_IIF).sql('(').visit(this.condition).sql(", ").visit(this.ifTrue).sql(", ").visit(this.ifFalse).sql(')');
                break;
            }
            default: {
                ctx.visit(DSL.when(this.condition, this.ifTrue).otherwise(this.ifFalse));
            }
        }
    }

    @Override
    public final Condition $arg1() {
        return this.condition;
    }

    @Override
    public final Field<T> $arg2() {
        return this.ifTrue;
    }

    @Override
    public final Field<T> $arg3() {
        return this.ifFalse;
    }

    @Override
    public final Function3<? super Condition, ? super Field<T>, ? super Field<T>, ? extends QOM.Iif<T>> $constructor() {
        return (c, f1, f2) -> new Iif<T>(this.getQualifiedName(), (Condition)c, f1, f2);
    }
}

