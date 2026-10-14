/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CosNaming.NameComponent;

public final class Property
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public NameComponent[] nam;
    public Any val;

    public Property() {
    }

    public Property(NameComponent[] nam, Any val) {
        this.nam = nam;
        this.val = val;
    }
}

