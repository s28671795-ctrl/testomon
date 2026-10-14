/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.WrongAdapterHelper;

public final class WrongAdapter
extends UserException {
    private static final long serialVersionUID = 1L;

    public WrongAdapter() {
        super(WrongAdapterHelper.id());
    }

    public WrongAdapter(String value) {
        super(value);
    }
}

