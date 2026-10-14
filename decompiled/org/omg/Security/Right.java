/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.Security.ExtensibleFamily;

public final class Right
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public ExtensibleFamily rights_family;
    public String right = "";

    public Right() {
    }

    public Right(ExtensibleFamily rights_family, String right) {
        this.rights_family = rights_family;
        this.right = right;
    }
}

