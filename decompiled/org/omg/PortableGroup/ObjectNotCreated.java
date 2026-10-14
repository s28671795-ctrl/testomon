/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.ObjectNotCreatedHelper;

public final class ObjectNotCreated
extends UserException {
    private static final long serialVersionUID = 1L;

    public ObjectNotCreated() {
        super(ObjectNotCreatedHelper.id());
    }

    public ObjectNotCreated(String value) {
        super(value);
    }
}

