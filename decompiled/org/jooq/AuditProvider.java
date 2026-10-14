/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.GeneratorContext;
import org.jooq.Pro;

@Pro
public interface AuditProvider {
    @Nullable
    public <T> Field<T> provideTimestamp(GeneratorContext<?, ?, T> var1);

    @Nullable
    public <T> Field<T> provideUser(GeneratorContext<?, ?, T> var1);
}

