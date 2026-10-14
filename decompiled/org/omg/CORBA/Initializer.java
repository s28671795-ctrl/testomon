/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.StructMember;
import org.omg.CORBA.portable.IDLEntity;

public final class Initializer
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public StructMember[] members;
    public String name = "";

    public Initializer() {
    }

    public Initializer(StructMember[] members, String name) {
        this.members = members;
        this.name = name;
    }
}

