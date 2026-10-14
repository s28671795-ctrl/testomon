/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.QualifiedRecord;
import org.jooq.UDT;

public interface UDTRecord<R extends UDTRecord<R>>
extends QualifiedRecord<R> {
    @NotNull
    public UDT<R> getUDT();
}

