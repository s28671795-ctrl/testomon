/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.time.Instant;
import java.util.Map;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.GeneratorContext;
import org.jooq.GeneratorStatementType;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractScope;

final class DefaultGeneratorContext<R extends Record, X extends Table<R>, T>
extends AbstractScope
implements GeneratorContext<R, X, T> {
    final Instant renderTime;
    final X table;
    final Field<T> field;
    final GeneratorStatementType statementType;

    DefaultGeneratorContext(Configuration configuration) {
        this(configuration, null, null, null, null, null);
    }

    DefaultGeneratorContext(Configuration configuration, Map<Object, Object> data, Instant renderTime, X table, Field<T> field2, GeneratorStatementType statementType) {
        super(configuration, data);
        this.renderTime = renderTime != null ? renderTime : this.creationTime();
        this.table = table;
        this.field = field2;
        this.statementType = statementType;
    }

    @Override
    public final Instant renderTime() {
        return this.renderTime;
    }

    @Override
    public final X table() {
        return this.table;
    }

    @Override
    public final Field<T> field() {
        return this.field;
    }

    @Override
    public final GeneratorStatementType statementType() {
        return this.statementType;
    }
}

