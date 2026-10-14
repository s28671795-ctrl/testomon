/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.portable.IDLEntity;

public final class ExtInitializer
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public StructMember[] members;
    public ExceptionDescription[] exceptions;
    public String name = "";

    public ExtInitializer() {
    }

    public ExtInitializer(StructMember[] members, ExceptionDescription[] exceptions, String name) {
        this.members = members;
        this.exceptions = exceptions;
        this.name = name;
    }
}

