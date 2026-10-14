/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.MemberAlreadyPresentHelper;

public final class MemberAlreadyPresent
extends UserException {
    private static final long serialVersionUID = 1L;

    public MemberAlreadyPresent() {
        super(MemberAlreadyPresentHelper.id());
    }

    public MemberAlreadyPresent(String value) {
        super(value);
    }
}

