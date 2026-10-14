/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Condition;
import org.jooq.Constraint;
import org.jooq.Named;
import org.jooq.Record;
import org.jooq.Table;

public interface Check<R extends Record>
extends Named {
    public Table<R> getTable();

    public Condition condition();

    public Constraint constraint();

    public boolean enforced();
}

