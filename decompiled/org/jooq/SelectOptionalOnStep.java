/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Record;
import org.jooq.SelectJoinPartitionByStep;
import org.jooq.SelectJoinStep;

public interface SelectOptionalOnStep<R extends Record>
extends SelectJoinStep<R>,
SelectJoinPartitionByStep<R> {
}

