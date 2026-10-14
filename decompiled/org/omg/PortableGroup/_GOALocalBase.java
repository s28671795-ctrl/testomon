/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.LocalObject;
import org.omg.PortableGroup.GOA;

public abstract class _GOALocalBase
extends LocalObject
implements GOA {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableGroup/GOA:1.0", "IDL:omg.org/PortableServer/POA:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

