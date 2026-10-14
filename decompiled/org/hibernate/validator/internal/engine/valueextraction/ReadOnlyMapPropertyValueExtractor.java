/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ReadOnlyMapProperty
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Map;
import java.util.Optional;
import javafx.beans.property.ReadOnlyMapProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@IgnoreForbiddenApisErrors(reason="Usage of JavaFX classes")
class ReadOnlyMapPropertyValueExtractor
implements ValueExtractor<ReadOnlyMapProperty<?, ?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ReadOnlyMapPropertyValueExtractor(), ReadOnlyMapProperty.class, ReadOnlyMapProperty.class.getTypeParameters()[1], false, Optional.empty());

    private ReadOnlyMapPropertyValueExtractor() {
    }

    @Override
    public void extractValues(ReadOnlyMapProperty<?, ?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (Map.Entry entry : originalValue.entrySet()) {
            receiver.keyedValue("<map value>", entry.getKey(), entry.getValue());
        }
    }
}

