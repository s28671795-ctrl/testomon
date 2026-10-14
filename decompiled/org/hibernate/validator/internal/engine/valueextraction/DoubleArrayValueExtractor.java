/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class DoubleArrayValueExtractor
implements ValueExtractor<double[]> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new DoubleArrayValueExtractor(), double[].class, new ArrayElement((Type)((Object)double[].class)), false, Optional.empty());

    private DoubleArrayValueExtractor() {
    }

    @Override
    public void extractValues(double[] originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.length; ++i) {
            receiver.indexedValue("<iterable element>", i, originalValue[i]);
        }
    }
}

