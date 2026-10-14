/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ContainedPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.portable.IDLEntity;

public final class Description
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public DefinitionKind kind;
    public Any value;

    public Description() {
    }

    public Description(DefinitionKind kind, Any value) {
        this.kind = kind;
        this.value = value;
    }
}

