/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ReadOnlyListProperty
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javafx.beans.property.ReadOnlyListProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@IgnoreForbiddenApisErrors(reason="Usage of JavaFX classes")
class ReadOnlyListPropertyValueExtractor
implements ValueExtractor<ReadOnlyListProperty<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ReadOnlyListPropertyValueExtractor(), ReadOnlyListProperty.class, ReadOnlyListProperty.class.getTypeParameters()[0], false, Optional.empty());

    private ReadOnlyListPropertyValueExtractor() {
    }

    @Override
    public void extractValues(ReadOnlyListProperty<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.size(); ++i) {
            receiver.indexedValue("<list element>", i, originalValue.get(i));
        }
    }
}

