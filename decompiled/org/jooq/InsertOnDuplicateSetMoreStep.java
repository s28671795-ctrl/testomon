/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.InsertOnConflictWhereStep;
import org.jooq.InsertOnDuplicateSetStep;
import org.jooq.Record;

public interface InsertOnDuplicateSetMoreStep<R extends Record>
extends InsertOnDuplicateSetStep<R>,
InsertOnConflictWhereStep<R> {
}

