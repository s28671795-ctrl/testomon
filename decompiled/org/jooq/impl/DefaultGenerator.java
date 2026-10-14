/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.GeneratorContext;
import org.jooq.GeneratorStatementType;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.Tools;

final class DefaultGenerator<R extends Record, X extends Table<R>, T>
implements Generator<R, X, T> {
    final Generator<R, X, T> delegate;

    DefaultGenerator(Generator<R, X, T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public final Field<T> apply(GeneratorContext<R, X, T> t2) {
        Field<T> f = t2.field();
        if (f != null) {
            Map map = (Map)t2.data().computeIfAbsent(Tools.SimpleDataKey.DATA_GENERATOR_EXACTLY_ONCE, k -> new ConcurrentHashMap());
            return map.computeIfAbsent(f, k -> (Field)this.delegate.apply(t2));
        }
        return (Field)this.delegate.apply(t2);
    }

    @Override
    public final boolean supports(GeneratorStatementType statementType) {
        return this.delegate.supports(statementType);
    }
}

