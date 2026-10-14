/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.defs.ru;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.ru.INN;

public class INNDef
extends ConstraintDef<INNDef, INN> {
    public INNDef() {
        super(INN.class);
    }

    public INNDef type(INN.Type type) {
        this.addParameter("type", (Object)type);
        return this;
    }
}

