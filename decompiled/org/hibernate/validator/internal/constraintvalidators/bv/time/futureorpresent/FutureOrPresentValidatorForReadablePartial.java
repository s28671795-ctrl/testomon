/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.AbstractFutureOrPresentEpochBasedValidator;
import org.joda.time.Instant;
import org.joda.time.ReadablePartial;

public class FutureOrPresentValidatorForReadablePartial
extends AbstractFutureOrPresentEpochBasedValidator<ReadablePartial> {
    @Override
    protected long getEpochMillis(ReadablePartial value, Clock reference) {
        return value.toDateTime(new Instant(reference.millis())).getMillis();
    }
}

