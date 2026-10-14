/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Min;
import org.hibernate.validator.cfg.ConstraintDef;

public class MinDef
extends ConstraintDef<MinDef, Min> {
    public MinDef() {
        super(Min.class);
    }

    public MinDef value(long min2) {
        this.addParameter("value", min2);
        return this;
    }
}

