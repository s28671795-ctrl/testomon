/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Support;

public interface FieldLike {
    @NotNull
    public <T> Field<T> asField();

    @NotNull
    public <T> Field<T> asField(String var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public <T> Field<T> asField(Function<? super Field<T>, ? extends String> var1);
}

