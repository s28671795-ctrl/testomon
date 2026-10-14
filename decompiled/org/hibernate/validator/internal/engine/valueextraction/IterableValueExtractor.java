/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class IterableValueExtractor
implements ValueExtractor<Iterable<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new IterableValueExtractor(), Iterable.class, Iterable.class.getTypeParameters()[0], false, Optional.empty());

    private IterableValueExtractor() {
    }

    @Override
    public void extractValues(Iterable<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (Object object : originalValue) {
            receiver.iterableValue("<iterable element>", object);
        }
    }
}

