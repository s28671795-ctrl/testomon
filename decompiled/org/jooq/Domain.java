/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jooq.Check;
import org.jooq.Qualified;
import org.jooq.Typed;

public interface Domain<T>
extends Qualified,
Typed<T> {
    @NotNull
    public List<Check<?>> getChecks();
}

