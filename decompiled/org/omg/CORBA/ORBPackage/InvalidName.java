/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ORBPackage;

import org.omg.CORBA.ORBPackage.InvalidNameHelper;
import org.omg.CORBA.UserException;

public final class InvalidName
extends UserException {
    private static final long serialVersionUID = 1L;

    public InvalidName() {
        super(InvalidNameHelper.id());
    }

    public InvalidName(String value) {
        super(value);
    }
}

