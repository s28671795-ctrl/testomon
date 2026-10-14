/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.AbstractFutureOrPresentEpochBasedValidator;
import org.joda.time.ReadableInstant;

public class FutureOrPresentValidatorForReadableInstant
extends AbstractFutureOrPresentEpochBasedValidator<ReadableInstant> {
    @Override
    protected long getEpochMillis(ReadableInstant value, Clock reference) {
        return value.getMillis();
    }
}

