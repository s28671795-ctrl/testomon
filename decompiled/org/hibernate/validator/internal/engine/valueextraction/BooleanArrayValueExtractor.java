/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class BooleanArrayValueExtractor
implements ValueExtractor<boolean[]> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new BooleanArrayValueExtractor(), boolean[].class, new ArrayElement((Type)((Object)boolean[].class)), false, Optional.empty());

    private BooleanArrayValueExtractor() {
    }

    @Override
    public void extractValues(boolean[] originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.length; ++i) {
            receiver.indexedValue("<iterable element>", i, originalValue[i]);
        }
    }
}

