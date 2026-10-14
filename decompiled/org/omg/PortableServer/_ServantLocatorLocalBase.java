/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.ServantLocator;

public abstract class _ServantLocatorLocalBase
extends LocalObject
implements ServantLocator {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableServer/ServantLocator:1.0", "IDL:omg.org/PortableServer/ServantManager:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

