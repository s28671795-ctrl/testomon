/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Size;
import org.hibernate.validator.cfg.ConstraintDef;

public class SizeDef
extends ConstraintDef<SizeDef, Size> {
    public SizeDef() {
        super(Size.class);
    }

    public SizeDef min(int min2) {
        this.addParameter("min", min2);
        return this;
    }

    public SizeDef max(int max) {
        this.addParameter("max", max);
        return this;
    }
}

