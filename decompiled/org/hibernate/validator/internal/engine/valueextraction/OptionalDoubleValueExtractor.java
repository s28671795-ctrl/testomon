/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import java.util.OptionalDouble;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.AnnotatedObject;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@UnwrapByDefault
class OptionalDoubleValueExtractor
implements ValueExtractor<OptionalDouble> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalDoubleValueExtractor(), OptionalDouble.class, AnnotatedObject.INSTANCE, true, Optional.of(Double.class));

    OptionalDoubleValueExtractor() {
    }

    @Override
    public void extractValues(OptionalDouble originalValue, ValueExtractor.ValueReceiver receiver) {
        receiver.value(null, originalValue.isPresent() ? Double.valueOf(originalValue.getAsDouble()) : null);
    }
}

