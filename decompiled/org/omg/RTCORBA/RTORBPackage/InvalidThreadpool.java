/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA.RTORBPackage;

import org.omg.CORBA.UserException;
import org.omg.RTCORBA.RTORBPackage.InvalidThreadpoolHelper;

public final class InvalidThreadpool
extends UserException {
    private static final long serialVersionUID = 1L;

    public InvalidThreadpool() {
        super(InvalidThreadpoolHelper.id());
    }

    public InvalidThreadpool(String value) {
        super(value);
    }
}

