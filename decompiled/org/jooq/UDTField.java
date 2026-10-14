/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Field;
import org.jooq.UDT;
import org.jooq.UDTRecord;

public interface UDTField<R extends UDTRecord<R>, T>
extends Field<T> {
    public UDT<R> getUDT();
}

