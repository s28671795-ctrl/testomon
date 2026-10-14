/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.PolicyCurrent;

public abstract class _PolicyCurrentLocalBase
extends LocalObject
implements PolicyCurrent {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/CORBA/PolicyCurrent:1.0", "IDL:omg.org/CORBA/PolicyManager:1.0", "IDL:omg.org/CORBA/Current:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

