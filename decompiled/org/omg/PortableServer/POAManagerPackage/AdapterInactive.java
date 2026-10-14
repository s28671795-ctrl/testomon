/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAManagerPackage.AdapterInactiveHelper;

public final class AdapterInactive
extends UserException {
    private static final long serialVersionUID = 1L;

    public AdapterInactive() {
        super(AdapterInactiveHelper.id());
    }

    public AdapterInactive(String value) {
        super(value);
    }
}

