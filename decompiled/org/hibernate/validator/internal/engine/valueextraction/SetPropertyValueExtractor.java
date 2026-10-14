/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.SetProperty
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javafx.beans.property.SetProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@IgnoreForbiddenApisErrors(reason="Usage of JavaFX classes")
class SetPropertyValueExtractor
implements ValueExtractor<SetProperty<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new SetPropertyValueExtractor(), SetProperty.class, SetProperty.class.getTypeParameters()[0], false, Optional.empty());

    private SetPropertyValueExtractor() {
    }

    @Override
    public void extractValues(SetProperty<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (Object object : originalValue) {
            receiver.iterableValue("<iterable element>", object);
        }
    }
}

