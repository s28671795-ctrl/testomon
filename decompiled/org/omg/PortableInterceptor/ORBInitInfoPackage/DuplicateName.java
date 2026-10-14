/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor.ORBInitInfoPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateNameHelper;

public final class DuplicateName
extends UserException {
    private static final long serialVersionUID = 1L;
    public String name = "";

    public DuplicateName() {
        super(DuplicateNameHelper.id());
    }

    public DuplicateName(String _reason, String name) {
        super(_reason);
        this.name = name;
    }

    public DuplicateName(String name) {
        super(DuplicateNameHelper.id());
        this.name = name;
    }
}

