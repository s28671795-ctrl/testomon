/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties;

import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.Property;

public interface Field
extends Property {
    @Override
    default public ConstrainedElement.ConstrainedElementKind getConstrainedElementKind() {
        return ConstrainedElement.ConstrainedElementKind.FIELD;
    }
}

