/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ContainerPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.Contained;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.portable.IDLEntity;

public final class Description
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public Contained contained_object;
    public DefinitionKind kind;
    public Any value;

    public Description() {
    }

    public Description(Contained contained_object, DefinitionKind kind, Any value) {
        this.contained_object = contained_object;
        this.kind = kind;
        this.value = value;
    }
}

