/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Map;
import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

class MapValueExtractor
implements ValueExtractor<Map<?, ?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new MapValueExtractor(), Map.class, Map.class.getTypeParameters()[1], false, Optional.empty());

    private MapValueExtractor() {
    }

    @Override
    public void extractValues(Map<?, ?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (Map.Entry<?, ?> entry : originalValue.entrySet()) {
            receiver.keyedValue("<map value>", entry.getKey(), entry.getValue());
        }
    }
}

