/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.portable.IDLEntity;

public final class NameComponent
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String id = "";
    public String kind = "";

    public NameComponent() {
    }

    public NameComponent(String id, String kind) {
        this.id = id;
        this.kind = kind;
    }
}

