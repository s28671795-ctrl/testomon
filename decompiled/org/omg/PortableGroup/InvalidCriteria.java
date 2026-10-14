/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.InvalidCriteriaHelper;
import org.omg.PortableGroup.Property;

public final class InvalidCriteria
extends UserException {
    private static final long serialVersionUID = 1L;
    public Property[] invalid_criteria;

    public InvalidCriteria() {
        super(InvalidCriteriaHelper.id());
    }

    public InvalidCriteria(String _reason, Property[] invalid_criteria) {
        super(_reason);
        this.invalid_criteria = invalid_criteria;
    }

    public InvalidCriteria(Property[] invalid_criteria) {
        super(InvalidCriteriaHelper.id());
        this.invalid_criteria = invalid_criteria;
    }
}

