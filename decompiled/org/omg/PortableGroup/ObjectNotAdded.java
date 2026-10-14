/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.ObjectNotAddedHelper;

public final class ObjectNotAdded
extends UserException {
    private static final long serialVersionUID = 1L;

    public ObjectNotAdded() {
        super(ObjectNotAddedHelper.id());
    }

    public ObjectNotAdded(String value) {
        super(value);
    }
}

