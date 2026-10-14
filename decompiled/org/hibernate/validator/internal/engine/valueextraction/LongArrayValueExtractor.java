/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class LongArrayValueExtractor
implements ValueExtractor<long[]> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new LongArrayValueExtractor(), long[].class, new ArrayElement((Type)((Object)long[].class)), false, Optional.empty());

    private LongArrayValueExtractor() {
    }

    @Override
    public void extractValues(long[] originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.length; ++i) {
            receiver.indexedValue("<iterable element>", i, originalValue[i]);
        }
    }
}

