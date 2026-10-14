/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.AbstractFutureEpochBasedValidator;
import org.joda.time.ReadableInstant;

public class FutureValidatorForReadableInstant
extends AbstractFutureEpochBasedValidator<ReadableInstant> {
    @Override
    protected long getEpochMillis(ReadableInstant value, Clock reference) {
        return value.getMillis();
    }
}

