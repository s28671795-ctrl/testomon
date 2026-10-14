/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Null;
import org.hibernate.validator.cfg.ConstraintDef;

public class NullDef
extends ConstraintDef<NullDef, Null> {
    public NullDef() {
        super(Null.class);
    }
}

