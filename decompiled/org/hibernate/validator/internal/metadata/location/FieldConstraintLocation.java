/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.location;

import org.hibernate.validator.internal.metadata.location.AbstractPropertyConstraintLocation;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Field;

public class FieldConstraintLocation
extends AbstractPropertyConstraintLocation<Field> {
    FieldConstraintLocation(Field field2) {
        super(field2);
    }

    @Override
    public ConstraintLocation.ConstraintLocationKind getKind() {
        return ConstraintLocation.ConstraintLocationKind.FIELD;
    }
}

