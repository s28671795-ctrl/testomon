/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.portable.IDLEntity;

public final class RoutingTypeRange
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short min;
    public short max;

    public RoutingTypeRange() {
    }

    public RoutingTypeRange(short min2, short max) {
        this.min = min2;
        this.max = max;
    }
}

