/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.location;

import org.hibernate.validator.internal.metadata.location.AbstractPropertyConstraintLocation;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Getter;

public class GetterConstraintLocation
extends AbstractPropertyConstraintLocation<Getter> {
    GetterConstraintLocation(Getter getter2) {
        super(getter2);
    }

    @Override
    public ConstraintLocation.ConstraintLocationKind getKind() {
        return ConstraintLocation.ConstraintLocationKind.GETTER;
    }
}

