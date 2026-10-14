/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.impl.TableRecordImpl;

public abstract class CustomRecord<R extends TableRecord<R>>
extends TableRecordImpl<R> {
    protected CustomRecord(Table<R> table) {
        super(table);
    }
}

