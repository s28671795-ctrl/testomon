/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ListProperty
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javafx.beans.property.ListProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@IgnoreForbiddenApisErrors(reason="Usage of JavaFX classes")
class ListPropertyValueExtractor
implements ValueExtractor<ListProperty<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ListPropertyValueExtractor(), ListProperty.class, ListProperty.class.getTypeParameters()[0], false, Optional.empty());

    private ListPropertyValueExtractor() {
    }

    @Override
    public void extractValues(ListProperty<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (int i = 0; i < originalValue.size(); ++i) {
            receiver.indexedValue("<list element>", i, originalValue.get(i));
        }
    }
}

