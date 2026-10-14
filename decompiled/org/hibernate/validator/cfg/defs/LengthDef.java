/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.Length;

public class LengthDef
extends ConstraintDef<LengthDef, Length> {
    public LengthDef() {
        super(Length.class);
    }

    public LengthDef min(int min2) {
        this.addParameter("min", min2);
        return this;
    }

    public LengthDef max(int max) {
        this.addParameter("max", max);
        return this;
    }
}

