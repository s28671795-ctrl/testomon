/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.MapProperty
 */
package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Map;
import java.util.Optional;
import javafx.beans.property.MapProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

@IgnoreForbiddenApisErrors(reason="Usage of JavaFX classes")
class MapPropertyKeyExtractor
implements ValueExtractor<MapProperty<?, ?>> {
    static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new MapPropertyKeyExtractor(), MapProperty.class, MapProperty.class.getTypeParameters()[0], false, Optional.empty());

    private MapPropertyKeyExtractor() {
    }

    @Override
    public void extractValues(MapProperty<?, ?> originalValue, ValueExtractor.ValueReceiver receiver) {
        for (Map.Entry entry : originalValue.entrySet()) {
            receiver.keyedValue("<map key>", entry.getKey(), entry.getKey());
        }
    }
}

