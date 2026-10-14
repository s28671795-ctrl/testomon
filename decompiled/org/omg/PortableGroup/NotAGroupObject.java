/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.NotAGroupObjectHelper;

public final class NotAGroupObject
extends UserException {
    private static final long serialVersionUID = 1L;

    public NotAGroupObject() {
        super(NotAGroupObjectHelper.id());
    }

    public NotAGroupObject(String value) {
        super(value);
    }
}

