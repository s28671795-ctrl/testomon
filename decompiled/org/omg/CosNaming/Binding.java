/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NameComponent;

public final class Binding
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public NameComponent[] binding_name;
    public BindingType binding_type;

    public Binding() {
    }

    public Binding(NameComponent[] binding_name, BindingType binding_type) {
        this.binding_name = binding_name;
        this.binding_type = binding_type;
    }
}

