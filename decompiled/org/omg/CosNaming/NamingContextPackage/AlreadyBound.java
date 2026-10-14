/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper;

public final class AlreadyBound
extends UserException {
    private static final long serialVersionUID = 1L;

    public AlreadyBound() {
        super(AlreadyBoundHelper.id());
    }

    public AlreadyBound(String value) {
        super(value);
    }
}

