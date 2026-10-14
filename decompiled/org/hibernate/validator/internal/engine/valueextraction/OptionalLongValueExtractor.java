/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import java.util.OptionalLong;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.AnnotatedObject;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@UnwrapByDefault
class OptionalLongValueExtractor
implements ValueExtractor<OptionalLong> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalLongValueExtractor(), OptionalLong.class, AnnotatedObject.INSTANCE, true, Optional.of(Long.class));

    OptionalLongValueExtractor() {
    }

    @Override
    public void extractValues(OptionalLong originalValue, ValueExtractor.ValueReceiver receiver) {
        receiver.value(null, originalValue.isPresent() ? Long.valueOf(originalValue.getAsLong()) : null);
    }
}

