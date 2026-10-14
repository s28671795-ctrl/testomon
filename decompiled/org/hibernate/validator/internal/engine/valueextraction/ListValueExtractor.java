/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.List;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class ListValueExtractor
implements ValueExtractor<List<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ListValueExtractor(), List.class, List.class.getTypeParameters()[0], false, Optional.empty());

    private ListValueExtractor() {
    }

    @Override
    public void extractValues(List<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.size(); ++i) {
            receiver.indexedValue("<list element>", i, originalValue.get(i));
        }
    }
}

