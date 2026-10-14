/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jooq.ForeignKey;
import org.jooq.Key;
import org.jooq.Record;

public interface UniqueKey<R extends Record>
extends Key<R> {
    @NotNull
    public List<ForeignKey<?, R>> getReferences();

    public boolean isPrimary();
}

