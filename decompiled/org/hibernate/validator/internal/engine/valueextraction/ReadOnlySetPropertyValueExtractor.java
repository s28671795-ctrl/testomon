/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ReadOnlySetProperty
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javafx.beans.property.ReadOnlySetProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@IgnoreForbiddenApisErrors(reason="Usage of JavaFX classes")
class ReadOnlySetPropertyValueExtractor
implements ValueExtractor<ReadOnlySetProperty<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ReadOnlySetPropertyValueExtractor(), ReadOnlySetProperty.class, ReadOnlySetProperty.class.getTypeParameters()[0], false, Optional.empty());

    private ReadOnlySetPropertyValueExtractor() {
    }

    @Override
    public void extractValues(ReadOnlySetProperty<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (Object object : originalValue) {
            receiver.iterableValue("<iterable element>", object);
        }
    }
}

