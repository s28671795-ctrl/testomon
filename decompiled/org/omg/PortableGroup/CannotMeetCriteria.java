/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.CannotMeetCriteriaHelper;
import org.omg.PortableGroup.Property;

public final class CannotMeetCriteria
extends UserException {
    private static final long serialVersionUID = 1L;
    public Property[] unmet_criteria;

    public CannotMeetCriteria() {
        super(CannotMeetCriteriaHelper.id());
    }

    public CannotMeetCriteria(String _reason, Property[] unmet_criteria) {
        super(_reason);
        this.unmet_criteria = unmet_criteria;
    }

    public CannotMeetCriteria(Property[] unmet_criteria) {
        super(CannotMeetCriteriaHelper.id());
        this.unmet_criteria = unmet_criteria;
    }
}

