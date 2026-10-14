/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import java.text.Normalizer;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.Normalized;

public class NormalizedDef
extends ConstraintDef<NormalizedDef, Normalized> {
    public NormalizedDef() {
        super(Normalized.class);
    }

    public NormalizedDef normalizationStrategy(Normalizer.Form form) {
        this.addParameter("normalizationForm", (Object)form);
        return this;
    }
}

