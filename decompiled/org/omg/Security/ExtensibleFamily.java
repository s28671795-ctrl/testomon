/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;

public final class ExtensibleFamily
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short family_definer;
    public short family;

    public ExtensibleFamily() {
    }

    public ExtensibleFamily(short family_definer, short family) {
        this.family_definer = family_definer;
        this.family = family;
    }
}

