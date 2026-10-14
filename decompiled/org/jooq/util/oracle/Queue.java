/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.oracle;

import org.jooq.Pro;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTRecord;

@Pro
public interface Queue<R extends UDTRecord<R>> {
    public String name();

    public Schema schema();

    public UDT<R> type();
}

