/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Consumer;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;

public abstract class CustomField<T>
extends AbstractField<T>
implements QOM.UEmptyField<T>,
QOM.UOpaque {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CUSTOM};

    protected CustomField(String name, DataType<T> type) {
        this(DSL.name(name), type);
    }

    protected CustomField(Name name, DataType<T> type) {
        super(name, type);
    }

    public static final <T> CustomField<T> of(String name, DataType<T> type, Consumer<? super Context<?>> consumer) {
        return CustomField.of(DSL.name(name), type, consumer);
    }

    public static final <T> CustomField<T> of(Name name, DataType<T> type, final Consumer<? super Context<?>> consumer) {
        return new CustomField<T>(name, type){

            @Override
            public void accept(Context<?> ctx) {
                consumer.accept(ctx);
            }
        };
    }

    @Override
    public abstract void accept(Context<?> var1);

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Field<T> as(Name alias) {
        return super.as(alias);
    }

    @Override
    public final boolean declaresFields() {
        return super.declaresFields();
    }

    @Override
    public final boolean declaresTables() {
        return super.declaresTables();
    }
}

