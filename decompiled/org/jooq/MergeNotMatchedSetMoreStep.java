/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.MergeNotMatchedSetStep;
import org.jooq.MergeNotMatchedWhereStep;
import org.jooq.Record;

public interface MergeNotMatchedSetMoreStep<R extends Record>
extends MergeNotMatchedSetStep<R>,
MergeNotMatchedWhereStep<R> {
}

