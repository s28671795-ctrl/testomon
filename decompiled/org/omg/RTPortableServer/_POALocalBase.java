/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTPortableServer;

import org.omg.CORBA.LocalObject;
import org.omg.RTPortableServer.POA;

public abstract class _POALocalBase
extends LocalObject
implements POA {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/RTPortableServer/POA:1.0", "IDL:omg.org/PortableServer/POA:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

