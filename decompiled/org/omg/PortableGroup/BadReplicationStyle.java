/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.PortableGroup.BadReplicationStyleHelper;

public final class BadReplicationStyle
extends UserException {
    private static final long serialVersionUID = 1L;

    public BadReplicationStyle() {
        super(BadReplicationStyleHelper.id());
    }

    public BadReplicationStyle(String value) {
        super(value);
    }
}

