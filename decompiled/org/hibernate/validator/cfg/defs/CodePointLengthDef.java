/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.CodePointLength;

public class CodePointLengthDef
extends ConstraintDef<CodePointLengthDef, CodePointLength> {
    public CodePointLengthDef() {
        super(CodePointLength.class);
    }

    public CodePointLengthDef min(int min2) {
        this.addParameter("min", min2);
        return this;
    }

    public CodePointLengthDef max(int max) {
        this.addParameter("max", max);
        return this;
    }

    public CodePointLengthDef normalizationStrategy(CodePointLength.NormalizationStrategy strategy) {
        this.addParameter("normalizationStrategy", (Object)strategy);
        return this;
    }
}

