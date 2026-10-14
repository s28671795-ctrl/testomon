/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.POAManager;

public abstract class _POAManagerLocalBase
extends LocalObject
implements POAManager {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableServer/POAManager:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

