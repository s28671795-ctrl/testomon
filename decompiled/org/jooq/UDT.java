/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.RecordQualifier;
import org.jooq.UDTRecord;

public interface UDT<R extends UDTRecord<R>>
extends RecordQualifier<R> {
    public boolean isSQLUsable();

    public boolean isSynthetic();
}

