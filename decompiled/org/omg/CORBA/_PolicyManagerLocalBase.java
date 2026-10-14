/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.PolicyManager;

public abstract class _PolicyManagerLocalBase
extends LocalObject
implements PolicyManager {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/CORBA/PolicyManager:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

