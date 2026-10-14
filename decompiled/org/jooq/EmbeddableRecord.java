/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Converter;
import org.jooq.Field;
import org.jooq.Record;

public interface EmbeddableRecord<R extends EmbeddableRecord<R>>
extends Record {
    public R original();

    public <T> R with(Field<T> var1, T var2);

    public <T, U> R with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);
}

