/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class ObjectArrayValueExtractor
implements ValueExtractor<Object[]> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ObjectArrayValueExtractor(), Object[].class, new ArrayElement((Type)((Object)Object[].class)), false, Optional.empty());

    private ObjectArrayValueExtractor() {
    }

    @Override
    public void extractValues(Object[] originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.length; ++i) {
            receiver.indexedValue("<iterable element>", i, originalValue[i]);
        }
    }
}

