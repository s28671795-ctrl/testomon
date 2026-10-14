/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.InterfaceNotFoundHelper;

public final class InterfaceNotFound
extends UserException {
    private static final long serialVersionUID = 1L;

    public InterfaceNotFound() {
        super(InterfaceNotFoundHelper.id());
    }

    public InterfaceNotFound(String value) {
        super(value);
    }
}

