/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.MemberNotFoundHelper;

public final class MemberNotFound
extends UserException {
    private static final long serialVersionUID = 1L;

    public MemberNotFound() {
        super(MemberNotFoundHelper.id());
    }

    public MemberNotFound(String value) {
        super(value);
    }
}

