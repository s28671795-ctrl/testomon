/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.AbstractPastOrPresentEpochBasedValidator;
import org.joda.time.ReadableInstant;

public class PastOrPresentValidatorForReadableInstant
extends AbstractPastOrPresentEpochBasedValidator<ReadableInstant> {
    @Override
    protected long getEpochMillis(ReadableInstant value, Clock reference) {
        return value.getMillis();
    }
}

