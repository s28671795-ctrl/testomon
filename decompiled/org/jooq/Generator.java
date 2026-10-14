/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.function.Function;
import org.jooq.Field;
import org.jooq.GeneratorContext;
import org.jooq.GeneratorStatementType;
import org.jooq.Record;
import org.jooq.Table;

@FunctionalInterface
public interface Generator<R extends Record, X extends Table<R>, T>
extends Function<GeneratorContext<R, X, T>, Field<T>>,
Serializable {
    default public boolean supports(GeneratorStatementType statementType) {
        return true;
    }
}

