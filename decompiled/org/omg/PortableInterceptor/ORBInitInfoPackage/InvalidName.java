/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor.ORBInitInfoPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableInterceptor.ORBInitInfoPackage.InvalidNameHelper;

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

