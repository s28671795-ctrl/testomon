/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.beans.value.ObservableValue
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@UnwrapByDefault
@IgnoreForbiddenApisErrors(reason="Usage of JavaFX classes")
class ObservableValueValueExtractor
implements ValueExtractor<ObservableValue<?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ObservableValueValueExtractor(), ObservableValue.class, ObservableValue.class.getTypeParameters()[0], true, Optional.empty());

    private ObservableValueValueExtractor() {
    }

    @Override
    public void extractValues(ObservableValue<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        receiver.value(null, originalValue.getValue());
    }
}

