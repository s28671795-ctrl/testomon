/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.omg.CORBA.StructMember;
import org.omg.CORBA.portable.IDLEntity;

public final class Initializer
implements IDLEntity {
    public StructMember[] members = null;
    public String name = null;

    public Initializer() {
    }

    public Initializer(StructMember[] _members, String _name) {
        this.members = _members;
        this.name = _name;
    }
}

