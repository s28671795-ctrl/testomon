/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.cfg.ConstraintDef;

public class DecimalMinDef
extends ConstraintDef<DecimalMinDef, DecimalMin> {
    public DecimalMinDef() {
        super(DecimalMin.class);
    }

    public DecimalMinDef value(String min2) {
        this.addParameter("value", min2);
        return this;
    }

    public DecimalMinDef inclusive(boolean inclusive) {
        this.addParameter("inclusive", inclusive);
        return this;
    }
}

