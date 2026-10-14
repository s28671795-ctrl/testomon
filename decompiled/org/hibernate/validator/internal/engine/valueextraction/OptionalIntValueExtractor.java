/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import java.util.OptionalInt;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.AnnotatedObject;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@UnwrapByDefault
class OptionalIntValueExtractor
implements ValueExtractor<OptionalInt> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalIntValueExtractor(), OptionalInt.class, AnnotatedObject.INSTANCE, true, Optional.of(Integer.class));

    OptionalIntValueExtractor() {
    }

    @Override
    public void extractValues(OptionalInt originalValue, ValueExtractor.ValueReceiver receiver) {
        receiver.value(null, originalValue.isPresent() ? Integer.valueOf(originalValue.getAsInt()) : null);
    }
}

