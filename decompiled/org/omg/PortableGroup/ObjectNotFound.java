/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.ObjectNotFoundHelper;

public final class ObjectNotFound
extends UserException {
    private static final long serialVersionUID = 1L;

    public ObjectNotFound() {
        super(ObjectNotFoundHelper.id());
    }

    public ObjectNotFound(String value) {
        super(value);
    }
}

