/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class FloatArrayValueExtractor
implements ValueExtractor<float[]> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new FloatArrayValueExtractor(), float[].class, new ArrayElement((Type)((Object)float[].class)), false, Optional.empty());

    private FloatArrayValueExtractor() {
    }

    @Override
    public void extractValues(float[] originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.length; ++i) {
            receiver.indexedValue("<iterable element>", i, Float.valueOf(originalValue[i]));
        }
    }
}

