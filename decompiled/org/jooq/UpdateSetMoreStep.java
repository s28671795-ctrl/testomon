/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Record;
import org.jooq.UpdateFromStep;
import org.jooq.UpdateSetStep;

public interface UpdateSetMoreStep<R extends Record>
extends UpdateSetStep<R>,
UpdateFromStep<R> {
}

