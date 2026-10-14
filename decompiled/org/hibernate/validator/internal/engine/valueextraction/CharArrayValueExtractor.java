/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class CharArrayValueExtractor
implements ValueExtractor<char[]> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new CharArrayValueExtractor(), char[].class, new ArrayElement((Type)((Object)char[].class)), false, Optional.empty());

    private CharArrayValueExtractor() {
    }

    @Override
    public void extractValues(char[] originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.length; ++i) {
            receiver.indexedValue("<iterable element>", i, Character.valueOf(originalValue[i]));
        }
    }
}

