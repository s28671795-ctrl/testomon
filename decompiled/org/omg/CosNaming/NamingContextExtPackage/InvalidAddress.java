/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextExtPackage;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper;

public final class InvalidAddress
extends UserException {
    private static final long serialVersionUID = 1L;

    public InvalidAddress() {
        super(InvalidAddressHelper.id());
    }

    public InvalidAddress(String value) {
        super(value);
    }
}

