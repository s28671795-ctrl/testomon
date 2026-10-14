/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class OptionalValueExtractor
implements ValueExtractor<Optional<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalValueExtractor(), Optional.class, Optional.class.getTypeParameters()[0], false, Optional.empty());

    private OptionalValueExtractor() {
    }

    @Override
    public void extractValues(Optional<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        receiver.value(null, originalValue.isPresent() ? originalValue.get() : null);
    }
}

