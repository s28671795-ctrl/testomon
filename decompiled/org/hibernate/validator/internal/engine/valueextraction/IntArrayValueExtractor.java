/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class IntArrayValueExtractor
implements ValueExtractor<int[]> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new IntArrayValueExtractor(), int[].class, new ArrayElement((Type)((Object)int[].class)), false, Optional.empty());

    private IntArrayValueExtractor() {
    }

    @Override
    public void extractValues(int[] originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.length; ++i) {
            receiver.indexedValue("<iterable element>", i, originalValue[i]);
        }
    }
}

