/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Fields;
import org.jooq.Record;

public interface RecordType<R extends Record>
extends Fields {
    public int size();
}

