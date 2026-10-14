/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.ObjectGroupNotFoundHelper;

public final class ObjectGroupNotFound
extends UserException {
    private static final long serialVersionUID = 1L;

    public ObjectGroupNotFound() {
        super(ObjectGroupNotFoundHelper.id());
    }

    public ObjectGroupNotFound(String value) {
        super(value);
    }
}

